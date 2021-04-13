package ai.ecma.clicksecurity.components;

import ai.ecma.clicksecurity.entity.Project;
import ai.ecma.clicksecurity.entity.Role;
import ai.ecma.clicksecurity.entity.Status;
import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.entity.enums.RoleName;
import ai.ecma.clicksecurity.entity.enums.StatusType;
import ai.ecma.clicksecurity.repository.ProjectRepository;
import ai.ecma.clicksecurity.repository.RoleRepository;
import ai.ecma.clicksecurity.repository.StatusRepository;
import ai.ecma.clicksecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProjectRepository projectRepository;


    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {

            statusRepository.save(new Status(StatusType.BEGIN, "Open", null));
            statusRepository.save(new Status(StatusType.COMPETED, "Completed", null));
            statusRepository.save(new Status(StatusType.OTHER, "In progress", null));

            Role superAdmin = roleRepository.save(new Role(1, RoleName.ROLE_SUPER_ADMIN));
            Role admin = roleRepository.save(new Role(2, RoleName.ROLE_ADMIN));
            Role user = roleRepository.save(new Role(3, RoleName.ROLE_USER));


            User user1 = userRepository.save(new User("SuperAdmin",
                    "SuperAdminov",
                    passwordEncoder.encode("root123"),
                    "super@inbox.ru",
                    "+9989939330934",
                    "super", Collections.singletonList(superAdmin)
            ));
            userRepository.save(new User("Admin",
                    "Adminov",
                    passwordEncoder.encode("root123"),
                    "admin@inbox.ru",
                    "+99899393304386",
                    "admin", Collections.singletonList(admin)
            ));
            userRepository.save(new User("User",
                    "Userov",
                    passwordEncoder.encode("root123"),
                    "user@inbox.ru",
                    "+9989939331725",
                    "user", Collections.singletonList(user)
            ));


        }
    }
}
