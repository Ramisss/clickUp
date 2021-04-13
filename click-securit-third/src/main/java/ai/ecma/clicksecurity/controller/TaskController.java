package ai.ecma.clicksecurity.controller;


import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.entity.enums.ProjectPermissionEnum;
import ai.ecma.clicksecurity.payload.ApiResponse;
import ai.ecma.clicksecurity.payload.TaskDto;
import ai.ecma.clicksecurity.security.CurrentUser;
import ai.ecma.clicksecurity.service.TaskService;
import ai.ecma.clicksecurity.utills.ApplicationConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {


    @Autowired
    TaskService taskService;


    //ADD TASK DONE
    @PostMapping
    public HttpEntity<?> addTask(@RequestBody TaskDto taskDto, @CurrentUser User user) {
        ApiResponse apiResponse = taskService.addTask(taskDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //GET TASK BY ID
    @GetMapping("/{id}")
    public HttpEntity<?> getTask(@PathVariable UUID id) {
        ApiResponse apiResponse = taskService.getTask(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    // GET ALL TASKS this PROJECT DONE
    @GetMapping(("/alltaskproject"))
    public HttpEntity<?> getAllByPaygable(
            @RequestBody TaskDto taskDto,
            @RequestParam(value = "page", defaultValue = ApplicationConstance.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", defaultValue = ApplicationConstance.DEFAULT_PAGE_SIZE) Integer size,
            @CurrentUser User user
    ) {
        ApiResponse apiResponse = taskService.getAll(page, size, taskDto,user);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    //EDIT TASK
    @PutMapping("/{id}")
    public HttpEntity<?> editTask(@PathVariable UUID id, @RequestBody TaskDto taskDto, @CurrentUser User user) {
        ApiResponse apiResponse = taskService.editTask(id, taskDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    //EDIT TASK
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id,
                                @RequestParam(name = "projectId") UUID projectId,
                                @RequestParam(name = "statusId") UUID statusId,
                                @CurrentUser User user) {
        ApiResponse apiResponse = taskService.delete(id, projectId, statusId, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/changeStatus/{id}")
    public HttpEntity<?> changeStatus(@PathVariable UUID id, @RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.changeStatus(id, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/spendTime/{id}")
    public HttpEntity<?> spendTime(@PathVariable UUID id) {
        ApiResponse apiResponse = taskService.spendTime(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PostMapping("/{projectId}")
    public HttpEntity<?> editTask(@CurrentUser User user, @PathVariable UUID projectId, @RequestBody TaskDto taskDto) {

        ApiResponse apiResponse = taskService.editTaskByNewUser(user, projectId, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getByStatus/{projectId}")
    public HttpEntity<?> getByStatus(@PathVariable UUID projectId, @CurrentUser User user) {
        ApiResponse apiResponse = taskService.getTasksByStatus(projectId, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
