package ai.ecma.clicksecurity.controller;


import ai.ecma.clicksecurity.entity.User;
import ai.ecma.clicksecurity.payload.ApiResponse;
import ai.ecma.clicksecurity.payload.ReqSignIn;
import ai.ecma.clicksecurity.payload.UserDto;
import ai.ecma.clicksecurity.repository.StatusRepository;
import ai.ecma.clicksecurity.repository.UserRepository;
import ai.ecma.clicksecurity.security.CurrentUser;
import ai.ecma.clicksecurity.security.JwtTokenProvider;
import ai.ecma.clicksecurity.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @PostMapping("registr")
    public HttpEntity<?> registr(@Valid @RequestBody UserDto userDto) {
        ApiResponse response = userService.registration(userDto);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(response);
//        return ResponseEntity.status(response.isSuccess()?201:409).body(response);
    }

    @PostMapping("login")
    public HttpEntity<?> checkLogin(@RequestBody ReqSignIn userDto) {
        try {
            Authentication paroliVaLoginiTogriOdam = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(),
                            userDto.getPassword()
                    ));
            User user = (User) paroliVaLoginiTogriOdam.getPrincipal();
            String token = jwtTokenProvider.generateToken(user.getId());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(409).body("Login or password incorrect!"+" Create new account");
//        return ResponseEntity.ok("Bad cred.");
    }




















}