package ai.ecma.clicksecurity.service;

import ai.ecma.clicksecurity.entity.*;
import ai.ecma.clicksecurity.entity.enums.ProjectPermissionEnum;
import ai.ecma.clicksecurity.payload.ApiResponse;
import ai.ecma.clicksecurity.payload.ProjectDto;
import ai.ecma.clicksecurity.payload.TaskDto;
import ai.ecma.clicksecurity.repository.*;
import ai.ecma.clicksecurity.utills.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TaskStatusRepositopy taskStatusRepositopy;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProjectRepository userProjectRepository;

    //ADD TASK
    public ApiResponse addTask(TaskDto taskDto, User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) return new ApiResponse("User not found ", false);
        User user1 = optionalUser.get();

        Optional<Project> optionalProject = projectRepository.findById(taskDto.getProjectId());
        if (!optionalProject.isPresent()) return new ApiResponse("Project not found", false);
        Project project = optionalProject.get();

        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), project.getId());
        if (!userProject.isPresent()) return new ApiResponse("User project ID not found", false);
        UserProject userProject1 = userProject.get();
        List<String> stringList = userProjectRepository.findByUserProjectId(userProject1.getId());

        if (!check(stringList, ProjectPermissionEnum.TASK_READ) && !check(stringList, ProjectPermissionEnum.TASK_ADD))
            return new ApiResponse("YOUR NOT PERMISSION", false);


        Optional<Status> optionalStatus = statusRepository.findById(taskDto.getStatusId());
        if (!optionalStatus.isPresent()) return new ApiResponse("Status not found", false);
        Status status = optionalStatus.get();

        Task task = new Task();
        task.setName(taskDto.getName());
        task.setStatus(status);
        task.setProject(project);
        taskRepository.save(task);

        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setTask(task);
        taskStatus.setStatus(status);
        taskStatusRepositopy.save(taskStatus);


        return new ApiResponse("task saved ", true, task);

    }

    //EDIT TASK
    public ApiResponse editTask(UUID id, TaskDto taskDto, User user) {

        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) return new ApiResponse("User not found ", false);
        User user1 = optionalUser.get();

        Optional<Project> optionalProject = projectRepository.findById(taskDto.getProjectId());
        if (!optionalProject.isPresent()) return new ApiResponse("Project not found", false);
        Project project = optionalProject.get();

        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), project.getId());
        if (!userProject.isPresent()) return new ApiResponse("User project ID not found", false);
        UserProject userProject1 = userProject.get();
        List<String> stringList = userProjectRepository.findByUserProjectId(userProject1.getId());

        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("not found"));
        Optional<Status> optionalStatus = statusRepository.findById(taskDto.getStatusId());

        if (!check(stringList, ProjectPermissionEnum.TASK_READ) && !check(stringList, ProjectPermissionEnum.TASK_CHANGE)) {
            return new ApiResponse("YOU NOT PERMISSION", false);
        }


        task.setName(taskDto.getName());
        Status status = optionalStatus.get();
        task.setStatus(status);
        taskRepository.save(task);
        return new ApiResponse("edited", true, task);
    }


    //GET TASK BY ID
    public ApiResponse getTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalStateException("task not found"));
        return new ApiResponse("task", true, task);
    }

    //GET TASKs PAGEABLE
    public ApiResponse getAll(int page, int size, TaskDto taskDto, User user) {
        Page<Task> taskPage;
        taskPage=taskRepository.findByProjectId(taskDto.getProjectId(), CommonUtils.getPageableByCreatedAtAsc(page,size));
        return new ApiResponse("all task this project",true,taskPage.getContent().stream().map(this::getTaskFromDB).collect(Collectors.toList()));

    }


    //METHOD GET TASK FROM DB
    public TaskDto getTaskFromDB(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setProjectId(task.getProject().getId());
        taskDto.setStatusId(task.getStatus().getId());
        return taskDto;
    }

    //METHOD GET PROJECT FROM DB
    public ProjectDto getProjectDtoFromDB(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setUserId(project.getUsers().getId());
        return projectDto;

    }

    //DELETE TASK
    public ApiResponse delete(UUID id, UUID projectId, UUID statusId, User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (!optionalUser.isPresent()) return new ApiResponse("User not found ", false);
        User user1 = optionalUser.get();

        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) return new ApiResponse("Project not found", false);
        Project project = optionalProject.get();


        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), project.getId());
        if (!userProject.isPresent()) return new ApiResponse("User project ID not found", false);
        UserProject userProject1 = userProject.get();
        List<String> stringList = userProjectRepository.findByUserProjectId(userProject1.getId());

        if (!check(stringList, ProjectPermissionEnum.TASK_DELETE))
            return new ApiResponse("YOU have not DELETE PERMISSION", false);
        taskStatusRepositopy.deleteByTaskIdAndStatusId(id, statusId);
        taskRepository.deleteById(id);
        return new ApiResponse("deleted", true);

    }


    public ApiResponse changeStatus(UUID id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResponse("task not found", false);
        Task task = optionalTask.get();

        Optional<Status> optionalStatus = statusRepository.findById(taskDto.getStatusId());
        if (!optionalStatus.isPresent()) return new ApiResponse("status not found", false);
        Status status = optionalStatus.get();


        if (status.getId().equals(task.getStatus().getId())) return new ApiResponse("Status not changed", false);
        task.setStatus(status);


        TaskStatus taskStatus = new TaskStatus();
        taskStatus.setStatus(status);
        taskStatus.setTask(task);


        taskStatusRepositopy.save(taskStatus);
        return new ApiResponse("taskstatus changed ", true);

    }

    public ApiResponse spendTime(UUID id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResponse("task not found", false);
        Task task = optionalTask.get();
        Optional<Status> optionalStatus = statusRepository.findById(task.getStatus().getId());
        if (!optionalStatus.isPresent()) return new ApiResponse("status none", false);
        Status status = optionalStatus.get();
        return null;

    }

    public boolean editTaskBo(User user, UUID projectId, ProjectPermissionEnum projectPermissionEnums) {
        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), projectId);
        if (!userProject.isPresent()) new ApiResponse("user or project not found", false);
        UserProject userProject1 = userProject.get();

        List<String> permissionEnumList = userProjectRepository.findByUserProjectId(userProject.get().getId());

        for (String taskChange : permissionEnumList
        ) {
            if (taskChange.toLowerCase().equals(permissionEnumList)) {

            }

        }

        return true;
    }


    public ApiResponse editTaskByNewUser(User user, UUID projectId, TaskDto taskDto) {
        List<String> stringList = stringList(user, projectId);

        Optional<Task> optionalTask = taskRepository.findById(taskDto.getId());
        if (!optionalTask.isPresent()) return new ApiResponse("Task not found", false);
        Task task = optionalTask.get();

        Optional<Status> optionalStatus = statusRepository.findById(taskDto.getStatusId());
        if (!optionalStatus.isPresent()) return new ApiResponse("Status not found", false);
        Status status = optionalStatus.get();

        if (!stringList.isEmpty()) {

            if (check(stringList, ProjectPermissionEnum.TASK_CHANGE) && check(stringList, ProjectPermissionEnum.TASK_READ)) {
                task.setName(taskDto.getName());
                task.setStatus(status);
                taskRepository.save(task);

                TaskStatus taskStatus = new TaskStatus();
                taskStatus.setStatus(status);
                taskStatus.setTask(task);
                taskStatusRepositopy.save(taskStatus);
                return new ApiResponse("task edited by added user", true);
            } else {
                return new ApiResponse("Sizda bunday huquq yuq", false);
            }
        } else {
            //XATOLIK
            return new ApiResponse("Sizda bunday huquq yuq (BO'SH)", false);
        }
//        return new ApiResponse("project", false);
    }

    // USERNI PERMISSIONLAR LISTINI OLIB MEN BERGAN PERMISSION BILAN TEKSHIRADI
    public boolean check(List<String> projectPermissionList, ProjectPermissionEnum projectPermissionEnum) {

        boolean isTrue = false;

        for (String permissionEnum : projectPermissionList) {
            if (permissionEnum.equals(projectPermissionEnum.toString())) {
                isTrue = true;
            }
        }

        return isTrue;
    }

    // USERPROJECT TABELDAN PERMISSIONLARNI OLIB BERADI
    public List<String> stringList(User user, UUID projectId) {
        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), projectId);
        if (!userProject.isPresent()) {
            return userProjectRepository.findByUserProjectId(userProject.get().getId());
        } else {
            return new ArrayList<>();
        }
    }


    public ApiResponse getTasksByStatus(UUID projectId, User user) {

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalStateException("Project not found"));
        List<Task> taskList = taskRepository.findByProjectId(projectId);

        return new ApiResponse("All task ", true, taskList.stream().map(Task::getName).collect(Collectors.toList()));


    }


    //        Optional<UserProject> userProject = userProjectRepository.findByUserIdAndProjectId(user.getId(), projectId);
//        if (!userProject.isPresent()) new ApiResponse("user or project not found", false);
////        UserProject userProject1 = userProject.get();

//        ProjectPermissionEnum[] permissionEnums = ProjectPermissionEnum.values();


//        List<String> permissionEnumList = userProjectRepository.findByUserProjectId(userProject.get().getId());

//        projectPermissionEnum.forEach(permission -> {
//            permissionEnumList.forEach(s -> {
//                s.equals(permission);
//            });
//        });
//        AtomicBoolean isExist = new AtomicBoolean(false);
//        permissionEnumList.forEach(permission -> {
//            if (permission.equals(projectPermissionEnum.name())) {
//
//                isExist.set(true);
//                System.out.println("fghjkl");
//            }
//        });
//
//        return isExist.get();
}
