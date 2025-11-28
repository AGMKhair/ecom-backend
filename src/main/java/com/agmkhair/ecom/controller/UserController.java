package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.APIResponse;
import com.agmkhair.ecom.dto.APIResponseBuilder;
import com.agmkhair.ecom.dto.CreateUserRequest;
import com.agmkhair.ecom.dto.LoginRequest;
import com.agmkhair.ecom.dto.ResetPasswordRequest;
import com.agmkhair.ecom.dto.UpdateUserRequest;
import com.agmkhair.ecom.entity.User;
import com.agmkhair.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    // CREATE USER
    @PostMapping("/create")
    public APIResponse<User> createUser(@RequestBody CreateUserRequest req) {
        User created = service.createUser(req);   // If fails → exception → handled globally

        return APIResponseBuilder.success(
                "User created successfully",
                created
        );
    }

    // LOGIN
    @PostMapping("/login")
    public APIResponse<User> login(@RequestBody LoginRequest req) {

        User valid = service.verifyUser(req);

        if (valid == null) {
            return APIResponseBuilder.failed("Invalid Username or Password");
        }

        return APIResponseBuilder.success("Login Successful", valid);
    }


    // DELETE USER
    @DeleteMapping("/{id}")
    public APIResponse<Void> deleteUser(@PathVariable Long id) {

        service.deleteUser(id);  // If user doesn't exist → throw exception

        return APIResponseBuilder.success("User Deleted Successfully", null);
    }

    // RESET PASSWORD
    @PostMapping("/reset-password")
    public APIResponse<Void> resetPassword(@RequestBody ResetPasswordRequest req) {

        service.resetPassword(req);

        return APIResponseBuilder.success("Password Updated Successfully", null);
    }

    // UPDATE USER
    @PutMapping("/update")
    public APIResponse<User> updateUser(@RequestBody UpdateUserRequest req) {

        User updated = service.updateUser(req); // If not found → exception

        return APIResponseBuilder.success("User Updated Successfully", updated);
    }
}
