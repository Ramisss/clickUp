package ai.ecma.clicksecurity.service;

import ai.ecma.clicksecurity.entity.*;
import ai.ecma.clicksecurity.entity.enums.ProjectPermissionEnum;
import ai.ecma.clicksecurity.payload.ApiResponse;
import ai.ecma.clicksecurity.payload.StatusDto;
import ai.ecma.clicksecurity.repository.*;
import ai.ecma.clicksecurity.utills.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserProjectRepository userProjectRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskStatusRepositopy taskStatusRepositopy;

    @Autowired
    UserRepository userRepository;

    public ApiResponse addStatus(StatusDto statusDto, User user) {
        Optional<Project> optionalProject = projectRepository.findById(statusDto.getProjectId());
        if (!optionalProject.isPresent()) return new ApiResponse("Project not found", false);
        Project project = optionalProject.get();

        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), project.getId());
        if (!userProject.isPresent()) return new ApiResponse("user project not found", false);
        UserProject userProject1 = userProject.get();

        List<String> stringList = userProjectRepository.findByUserProjectId(userProject1.getId());

        if (!taskService.check(stringList, ProjectPermissionEnum.STATUS_READ) && !taskService.check(stringList, ProjectPermissionEnum.STATUS_ADD))
            return new ApiResponse("YUo do not have permission", false);

        boolean isExist = statusRepository.existsByName(statusDto.getName());
        if (isExist) return new ApiResponse("this status alredy exists", false);
        Status status = new Status();
        status.setName(statusDto.getName());
        if (statusDto.getProjectId() == null) return new ApiResponse("Fill the blanck", false);
        statusRepository.save(status);
        return new ApiResponse("status add", true);
    }

    public ApiResponse getAll() {

        List<Status> status = statusRepository.findAll();
        return new ApiResponse("all status", true, status);
    }


    public ApiResponse editStatus(UUID id, StatusDto statusDto, User user) {
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("user not found"));

        Optional<Project> optionalProject = projectRepository.findById(statusDto.getProjectId());
        if (!optionalProject.isPresent()) return new ApiResponse("project not found", false);
        Project project = optionalProject.get();

        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), project.getId());
        if (!userProject.isPresent()) return new ApiResponse("user project not found", false);
        UserProject userProject1 = userProject.get();

        List<String> stringList = userProjectRepository.findByUserProjectId(userProject1.getId());
        if (!taskService.check(stringList, ProjectPermissionEnum.STATUS_READ) && !taskService.check(stringList, ProjectPermissionEnum.STATUS_CHANGE))
            return new ApiResponse("You not have permission", false);

        Status status = statusRepository.findById(id).orElseThrow(() -> new IllegalStateException("status not found "));
        status.setName(statusDto.getName());
        status.setProject(project);
        statusRepository.save(status);
        return new ApiResponse("status edited", true);
    }

    public ApiResponse getStatus(UUID id) {
        statusRepository.findById(id).orElseThrow(() -> new IllegalStateException("status not found"));
        return new ApiResponse("get status", true);
    }


    public ApiResponse delete(UUID uuid,StatusDto statusDto,User user) {
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalStateException("user not found"));

        Optional<Project> optionalProject = projectRepository.findById(statusDto.getProjectId());
        if (!optionalProject.isPresent()) return new ApiResponse("project not found", false);
        Project project = optionalProject.get();

        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), project.getId());
        if (!userProject.isPresent()) return new ApiResponse("user project not found", false);
        UserProject userProject1 = userProject.get();

        List<String> stringList = userProjectRepository.findByUserProjectId(userProject1.getId());
        if (!taskService.check(stringList, ProjectPermissionEnum.STATUS_DELETE))
            return new ApiResponse("You not have permission", false);

        taskRepository.deleteById(uuid);
        return new ApiResponse("status delete",true);


    }
}
