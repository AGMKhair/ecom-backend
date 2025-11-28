package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.CreateUserRequest;
import com.agmkhair.ecom.dto.LoginRequest;
import com.agmkhair.ecom.dto.ResetPasswordRequest;
import com.agmkhair.ecom.dto.UpdateUserRequest;
import com.agmkhair.ecom.entity.User;
import com.agmkhair.ecom.exception.UserAlreadyExistsException;
import com.agmkhair.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // CREATE USER
    public User createUser(CreateUserRequest dto) {

        if (userRepo.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("User already registered");
        }
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());

        // password encrypted
        user.setPassword(encoder.encode(dto.getPassword()));

        user.setPhoneNo(dto.getPhoneNo());
        user.setStatus(dto.getStatus());
        user.setType(dto.getType());

        return userRepo.save(user);
    }

    // VERIFY USER LOGIN
    public User verifyUser(LoginRequest login) {
        Optional<User> optional = userRepo.findByUsername(login.getUsername());

        if (optional.isEmpty()) {
            return null;
        }

        User user = optional.get();

        // match raw password with bcrypt
        if (!encoder.matches(login.getPassword(), user.getPassword())) {
            return null;
        }

        return user;  // LOGIN SUCCESS
    }



    // DELETE USER
    public boolean deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            return false;
        }
        userRepo.deleteById(id);
        return true;
    }

    // RESET PASSWORD
    public boolean resetPassword(ResetPasswordRequest req) {

        Optional<User> optional = userRepo.findById(req.getUserId());
        if (optional.isEmpty()) {
            return false;
        }

        User user = optional.get();

        user.setPassword(encoder.encode(req.getNewPassword()));

        userRepo.save(user);
        return true;
    }


    // Update User info
    public User updateUser(UpdateUserRequest req) {

        Optional<User> optional = userRepo.findById(req.getId());

        if (optional.isEmpty()) {
            return null; // or throw exception
        }

        User user = optional.get();

        // Update only allowed fields
        if (req.getFirstName() != null) user.setFirstName(req.getFirstName());
        if (req.getLastName() != null) user.setLastName(req.getLastName());
        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getPhoneNo() != null) user.setPhoneNo(req.getPhoneNo());
        if (req.getStatus() != null) user.setStatus(req.getStatus());
        if (req.getType() != null) user.setType(req.getType());

        return userRepo.save(user);
    }


}
