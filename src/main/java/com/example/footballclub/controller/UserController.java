package com.example.footballclub.controller;

import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.request.OrganizationCreateRequest;
import com.example.footballclub.dto.request.OrganizationUpdateRequest;
import com.example.footballclub.dto.request.UserCreateRequest;
import com.example.footballclub.dto.request.UserUpdateRequest;
import com.example.footballclub.dto.response.OrganizationResponse;
import com.example.footballclub.dto.response.UserProfileResponse;
import com.example.footballclub.dto.response.UserResponse;
import com.example.footballclub.service.OrganizationService;
import com.example.footballclub.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> getProfile() {
        UserProfileResponse result = userService.getProfile();

        return ApiResponse.<UserProfileResponse>builder().result(result).build();
    }
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        UserResponse result = userService.create(request);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request) {
        UserResponse result = userService.updateUser(id, request);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable String id) {
        UserResponse result = userService.getUser(id);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

}
