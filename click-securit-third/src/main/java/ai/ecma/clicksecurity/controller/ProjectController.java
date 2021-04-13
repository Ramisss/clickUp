package ai.ecma.clicksecurity.controller;


import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.payload.AddUserToProject;
import ai.ecma.clicksecurity.payload.ApiResponse;
import ai.ecma.clicksecurity.payload.ProjectDto;
import ai.ecma.clicksecurity.payload.UserDto;
import ai.ecma.clicksecurity.security.CurrentUser;
import ai.ecma.clicksecurity.service.ProjectService;
import ai.ecma.clicksecurity.utills.ApplicationConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@CrossOrigin
@RestController
@RequestMapping("/api/project/")
public class ProjectController {

    @Autowired
    ProjectService projectService;


    //ADD PROJECT FOR USER
    @PostMapping("addProject")
    public HttpEntity<?> addProject(@RequestBody ProjectDto projectDto, @CurrentUser User user) {
        ApiResponse response = projectService.addProject(projectDto, user);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(response);
//        return ResponseEntity.status(response.isSuccess()?201:409).body(response);
    }

    //GET ALL PROJECT FOR USER
//    @GetMapping("all")
//    public HttpEntity<?> getAllByPageable(@RequestParam(value = "page", defaultValue = ApplicationConstance.DEFAULT_PAGE_NUMBER) Integer page,
//                                          @RequestParam(value = "size", defaultValue = ApplicationConstance.DEFAULT_PAGE_SIZE) Integer size,
//                                          @CurrentUser User user) {
//        ApiResponse apiResponse = projectService.getAllByPageable(page, size, user);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
//    }


    // GET PROJECT BY ID FOR USER
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id, @CurrentUser User user) {
        ApiResponse apiResponse = projectService.getById(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    //PUT PROJECT BY ID FOR USER
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody ProjectDto projectDto,@CurrentUser User user) {
        ApiResponse apiResponse = projectService.editProject(id, projectDto,user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    //DELETE PROJECT
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = projectService.deleteById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    //Register USER TO PROJECT
    @PostMapping("registerUserToProject/{projectId}")
    public HttpEntity<?> registerUser(@CurrentUser User user, @RequestBody UserDto userDto, @PathVariable UUID projectId) {
        ApiResponse apiResponse = projectService.registerUser(user, userDto, projectId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    // Add TO PROJECT ADD USER TO PROJECT
    @PostMapping("addUserToProject")
    public HttpEntity<?> addUser(@CurrentUser User user, @RequestBody AddUserToProject addUserToProject) {
        ApiResponse apiResponse = projectService.addOneUserToProject(user, addUserToProject);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    //RETURN PAGEABLE USER PROJECT
    @GetMapping("findProjects")
    public HttpEntity<?> getProjects(@RequestParam(value = "page", defaultValue = ApplicationConstance.DEFAULT_PAGE_NUMBER) Integer page,
                                     @RequestParam(value = "size", defaultValue = ApplicationConstance.DEFAULT_PAGE_SIZE) Integer size,
                                     @CurrentUser User user) {
        ApiResponse apiResponse = projectService.getProjects(page, size, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }


}




