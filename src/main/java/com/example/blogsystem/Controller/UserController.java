package com.example.blogsystem.Controller;

import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.UserRepository;
import com.example.blogsystem.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;



    //all
    @PostMapping("/login/{username}/{password}")//done
    public ResponseEntity<ApiResponse> login(@PathVariable String username, @PathVariable String password ){
        logger.info("inside login  endpoint!");
        userService.login(username, password);
        return ResponseEntity.ok().body(new ApiResponse("user Logged In successfully!"));
    }

    //all
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(){
        logger.info("inside logout endpoint");
        userService.logout();
        return ResponseEntity.ok().body(new ApiResponse("user logged out successfully!"));
    }

    //all
    @PostMapping("/register")//done
    public ResponseEntity<ApiResponse> register(@RequestBody User user){
        logger.info("inside register endpoint!");
        userService.register(user);
        return ResponseEntity.ok().body(new ApiResponse("user registerd successfully!"));
    }

    //user
    @PutMapping("/update/{userId}")//done
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer userId, @RequestBody User user){
        logger.info("inside udpate user endpoint!");
        userService.updateUser(userId, user);
        return ResponseEntity.ok().body(new ApiResponse("user updated successfully!"));
    }

    //admin
    @GetMapping("/get-user-by-id/{userId}")//done
    public ResponseEntity<User> getUserById(@PathVariable Integer userId){
        logger.info("inside get user by id endpoint!");
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    //admin
    @DeleteMapping("/delete/{userId}")//done
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        logger.info("inside delete user endpoint!");
        userService.deleteUser(userId);
        return ResponseEntity.ok().body(new ApiResponse("user deleted successfully!"));
    }






}
