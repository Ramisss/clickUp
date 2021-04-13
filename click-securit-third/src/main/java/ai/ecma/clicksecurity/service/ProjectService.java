package ai.ecma.clicksecurity.service;


import ai.ecma.clicksecurity.entity.Project;
import ai.ecma.clicksecurity.entity.Role;
import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.entity.UserProject;
import ai.ecma.clicksecurity.entity.enums.ProjectPermissionEnum;
import ai.ecma.clicksecurity.entity.enums.RoleName;
import ai.ecma.clicksecurity.payload.*;
import ai.ecma.clicksecurity.repository.*;
import ai.ecma.clicksecurity.utills.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    UserProjectRepository userProjectRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    //USER ADD PROJECT
    public ApiResponse addProject(ProjectDto projectDto, User user) {
        boolean isExist = projectRepository.existsByName(projectDto.getName());
        if (isExist) return new ApiResponse("this project exist", false);
        //CREATE NEW PROJECT
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setUsers(user);
        projectRepository.save(project);
        //SAVE USER AND PROJECT TO USERPROJECT
        UserProject userProject = new UserProject();
        userProject.setProject(project);
        userProject.setUser(user);
        ProjectPermissionEnum[] projectPermissionEnums = ProjectPermissionEnum.values();
        userProject.setProjectPermissionEnums(new HashSet<>(Arrays.asList(projectPermissionEnums)));
        userProjectRepository.save(userProject);
        return new ApiResponse("project saved 3 table userProject,project and projectPermission ", true, project);

    }


    //GET PROJECT CURRENTUSER BY ID
    public ApiResponse getById(UUID id, User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("User not found"));
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalStateException("projet not exicst"));
        return new ApiResponse("take the project", true, project);
    }


    //EDIT PROJECT
    public ApiResponse editProject(UUID id, ProjectDto projectDto, User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) return new ApiResponse("User not found", false);
        User user1 = optionalUser.get();

        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalStateException("project not exist"));
        project.setName(projectDto.getName());
        project.setUsers(user1);
        projectRepository.save(project);

        UserProject userProject = new UserProject();
        userProject.setUser(user1);
        userProject.setProject(project);
        ProjectPermissionEnum[] projectPermissionEnums = ProjectPermissionEnum.values();
        userProject.setProjectPermissionEnums(new HashSet<>(Arrays.asList(projectPermissionEnums)));
        userProjectRepository.save(userProject);
        return new ApiResponse("project edited", true, project);
    }

    //GET PROJECT BY PAGEBLR
//    public ApiResponse getAllByPageable(Integer page, Integer size, User user) {
//        Page<Project> projectPage;
////        Page<Project> projectList = userProjectRepository.findByUserId(user.getId());
////        return new ApiResponse("Page",true,projectList.getContent().stream().map(this::getProjectDtoFromDB).collect(Collectors.toList()),projectList.getTotalElements(),page);
//return null;
//
////        List<Project>projectList=userProjectRepository.findByUserId(user.getId());
////        if (projectList==null) return new ApiResponse("No projects",false);
////        projectPage=
////        return new ApiResponse("list project",true,projectList);
//
////        projectPage = projectRepository.findByUsersId(user.getId(), CommonUtils.getPageableByCreatedAtAsc(page, size));
////        projectPage = projectRepository.findByUsersId();
////        return new ApiResponse("OK", true, projectPage.getContent()
////                .stream().map(this::getProjectDtoFromDB)
////                .collect(Collectors.toList()), projectPage.getTotalElements(), page);
//
//    }

    //DELETE PROJECT BY ID
    public ApiResponse deleteById(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalStateException("project not found"));
        projectRepository.deleteById(id);
        return new ApiResponse("project deleted", true);
    }


//    public ApiResponse addOneUserToProject(User user, UUID userIds, UUID projectId, List<String> stringList) {
//        userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("uer not found"));
//
//        Optional<User> optionalUser = userRepository.findById(userIds);
//        if (!optionalUser.isPresent()) return new ApiResponse("selected user not found", false);
//        User user1 = optionalUser.get();
//
//        Optional<Project> optionalProject = projectRepository.findById(projectId);
//        if (!optionalProject.isPresent()) return new ApiResponse("project not found", false);
//        Project project = optionalProject.get();
//
//        UserProject userProject = new UserProject();
//        userProject.setUser(user1);
//        userProject.setProject(project);
//        ProjectPermissionEnum[] projectPermissionEnums = ProjectPermissionEnum.values();
////        userProject.setProjectPermissionEnums();
//
//        userProject.setProjectPermissionEnums(new HashSet<>(Arrays.asList(projectPermissionEnums)));
//
//
//        userProjectRepository.save(userProject);
//        return new ApiResponse(" User add to new project by admin", true);
//
//
//    }


    public ProjectDto get2ProjectDtoFromDB(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setUserId(project.getUsers().getId());
//        projectDto.setUserId(project.getUsers().getId());
        return projectDto;

    }


    public UserProjectDto getProjectfromUserProjectTable(UserProject userProject) {
        UserProjectDto userProjectDto = new UserProjectDto();
        userProjectDto.setId(userProject.getId());
        userProjectDto.setProjectId(userProject.getProject().getId());
        userProjectDto.setUserId(userProject.getUser().getId());
        return userProjectDto;

    }


    public ApiResponse registerUser(User user, UserDto userDto, UUID projectId) {


        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) return new ApiResponse("project not found ", false);
        Project project = optionalProject.get();

        User newUser = new User();
        newUser.setFirsName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setUserName(userDto.getUserName());
        if (userDto.getRolesId() == null) {
            List<Role> roles = Collections.singletonList(roleRepository.findByRoleName(RoleName.ROLE_USER));
            newUser.setRoles(roles);
        }

        userRepository.save(newUser);

        UserProject userProject = new UserProject();
        userProject.setUser(newUser);
        userProject.setProject(project);

        userProjectRepository.save(userProject);
        return new ApiResponse("user add to project ", false);

    }


    public ApiResponse getProjects(Integer page, Integer size, User user) {

        List<Project> projectList = projectRepository.progectList(user.getId(), page, size);


        return new ApiResponse("Projects ", true,projectList.stream().map(this::get2ProjectDtoFromDB).collect(Collectors.toList()));
    }


    public ApiResponse addOneUserToProject(User user, AddUserToProject addUserToProject) {

//        Optional<User> optional = userRepository.findById(addUserToProject.getUserId());
//        if (!optional.isPresent()) return new ApiResponse("user this not exist", false);
//        User user2 = optional.get();


        Optional<User> optionalUser = userRepository.findById(addUserToProject.getUserId());
        if (!optionalUser.isPresent()) return new ApiResponse("user this not exist", false);
        User user1 = optionalUser.get();

        Optional<Project> optionalProject = projectRepository.findById(addUserToProject.getProjectId());
        if (!optionalProject.isPresent()) new ApiResponse("Project not found", false);
        Project project = optionalProject.get();

        UserProject userProject = new UserProject();
        userProject.setUser(user1);
        userProject.setProject(project);
        userProject.setProjectPermissionEnums(getPermissionEnums(addUserToProject.getListPermission()));
        userProjectRepository.save(userProject);
        return new ApiResponse("User add to project with PERMISSIONs", true, userProject);
    }


    // METHOD CHECK PERMISSION (frontdan kelgan String Set<ProjectPermissionEnum> berish )
    public Set<ProjectPermissionEnum> getPermissionEnums(List<String> stringList) {

        Set<ProjectPermissionEnum> projectPermissionEnums = new HashSet<>();

        for (String string : stringList) {
            switch (string) {

                case "TASK_ADD":
                    projectPermissionEnums.add(ProjectPermissionEnum.TASK_ADD);
                    break;
                case "TASK_CHANGE":
                    projectPermissionEnums.add(ProjectPermissionEnum.TASK_CHANGE);
                    break;
                case "TASK_READ":
                    projectPermissionEnums.add(ProjectPermissionEnum.TASK_READ);
                    break;
                case "TASK_DELETE":
                    projectPermissionEnums.add(ProjectPermissionEnum.TASK_DELETE);
                    break;
                case "STATUS_ADD":
                    projectPermissionEnums.add(ProjectPermissionEnum.STATUS_ADD);
                    break;
                case "STATUS_CHANGE":
                    projectPermissionEnums.add(ProjectPermissionEnum.STATUS_CHANGE);
                    break;
                case "STATUS_READ":
                    projectPermissionEnums.add(ProjectPermissionEnum.STATUS_READ);
                    break;
                case "STATUS_DELETE":
                    projectPermissionEnums.add(ProjectPermissionEnum.STATUS_DELETE);
                    break;


            }
        }

        return projectPermissionEnums;
    }

    //METHOD GET PROJECT FROM DB
    public ProjectDto getProjectDtoFromDB(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setUserId(project.getUsers().getId());
        return projectDto;

    }


    public boolean check(Set<ProjectPermissionEnum> projectPermissionEnums, ProjectPermissionEnum projectPermissionEnum) {

        return true;
    }
}
