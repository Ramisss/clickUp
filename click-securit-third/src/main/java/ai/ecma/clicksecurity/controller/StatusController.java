package ai.ecma.clicksecurity.controller;

import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.payload.ApiResponse;
import ai.ecma.clicksecurity.payload.StatusDto;
import ai.ecma.clicksecurity.security.CurrentUser;
import ai.ecma.clicksecurity.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @PostMapping
    public HttpEntity<?> addstatus(@RequestBody StatusDto statusDto, @CurrentUser User user) {
        ApiResponse apiResponse = statusService.addStatus(statusDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> get() {
        ApiResponse apiResponse = statusService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editStatus(@PathVariable UUID id, @RequestBody StatusDto statusDto, @CurrentUser User user) {
        ApiResponse apiResponse = statusService.editStatus(id, statusDto,user);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = statusService.getStatus(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable UUID uuid,StatusDto statusDto,@CurrentUser User user){
        ApiResponse apiResponse=statusService.delete(uuid,statusDto,user);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);

    }

}
