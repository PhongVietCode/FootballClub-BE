package com.example.footballclub.controller;


import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.response.ContestResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HealthController {
    @GetMapping
    public ApiResponse<String> checkHealth(){
        return ApiResponse.<String>builder().result("Success").build();
    }
}
