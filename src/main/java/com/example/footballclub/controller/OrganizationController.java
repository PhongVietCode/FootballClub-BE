package com.example.footballclub.controller;

import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.request.OrganizationCreateRequest;
import com.example.footballclub.dto.request.OrganizationUpdateRequest;
import com.example.footballclub.dto.response.OrganizationDetailResponse;
import com.example.footballclub.dto.response.OrganizationResponse;
import com.example.footballclub.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationController {
    OrganizationService organizationService;

    @PostMapping
    public ApiResponse<OrganizationResponse> createOrg(@RequestBody @Valid OrganizationCreateRequest request) {
        OrganizationResponse result = organizationService.create(request);
        return ApiResponse.<OrganizationResponse>builder().result(result).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<OrganizationResponse> updateOrg(@PathVariable String id, @RequestBody @Valid OrganizationUpdateRequest request) {
        OrganizationResponse result = organizationService.updateOrganization(id, request);
        return ApiResponse.<OrganizationResponse>builder().result(result).build();
    }
    @GetMapping("/{id}")
    public ApiResponse<OrganizationDetailResponse> getDetail(@PathVariable String id) {
        OrganizationDetailResponse result = organizationService.getDetail(id);
        return ApiResponse.<OrganizationDetailResponse>builder().result(result).build();
    }

}
