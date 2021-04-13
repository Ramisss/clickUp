package ai.ecma.clicksecurity.service;


import ai.ecma.clicksecurity.entity.Project;
import ai.ecma.clicksecurity.entity.Role;
import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.entity.UserProject;
import ai.ecma.clicksecurity.entity.enums.RoleName;
import ai.ecma.clicksecurity.payload.ApiResponse;
import ai.ecma.clicksecurity.payload.ProjectDto;
import ai.ecma.clicksecurity.payload.UserDto;
import ai.ecma.clicksecurity.repository.*;
import ai.ecma.clicksecurity.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailValidator emailValidator;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserProjectRepository userProjectRepository;


    public ApiResponse registration(UserDto userDto) {
        boolean isValidEmail = emailValidator.
                test(userDto.getEmail());
        if (!isValidEmail) {
            throw new UsernameNotFoundException("email not valid");
        }

        User user = new User();
        user.setFirsName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRolesId() == null) {
            List<Role> roles = Collections.singletonList(roleRepository.findByRoleName(RoleName.ROLE_USER));
            user.setRoles(roles);
        }
        userRepository.save(user);

        return new ApiResponse("User saved", true, user);
    }




//    public ApiResponse addUserToProject(User user, UserDto userDto, UUID projectId) {
//        userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("main User not found"));
//        Optional<Project> optionalProject = projectRepository.findById(projectId);
//        if (!optionalProject.isPresent()) return new ApiResponse("project not found", false);
//        Project project = optionalProject.get();
//
//        User user1 = new User();
//        user1.setFirsName(userDto.getFirstName());
//        user1.setLastName(userDto.getLastName());
//        user1.setEmail(userDto.getEmail());
//        user1.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        user1.setPhoneNumber(userDto.getPhoneNumber());
//        if (userDto.getRolesId() == null) {
//            List<Role> roles = Collections.singletonList(roleRepository.findByRoleName(RoleName.ROLE_USER));
//            user1.setRoles(roles);
//        }
//        userRepository.save(user1);
//
//
//        project.setUsers(user1);
//        projectRepository.save(project);
//
//
//
//
//        UserProject newUser = new UserProject();
//        newUser.setUser(Collections.singletonList(user1));
//        newUser.setProject(Collections.singletonList(project));
//
//        userProjectRepository.save(newUser);
//        return new ApiResponse("user saved to table user_project", true);
//    }


}
//    public ApiResponse addProject(ProjectDto projectDto, User user) {
//        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("User not found"));
//        boolean existsName = statusRepository.existsByName(projectDto.getName());
//
//        if (existsName) return new ApiResponse("not saved", false);
//
//        Status status1 = new Status();
//        status1.setName(projectDto.getName());
//        statusRepository.save(status1);
//        return new ApiResponse("status saved", true);
//
//    }




