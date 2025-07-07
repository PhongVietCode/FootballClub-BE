package com.example.footballclub.controller;

import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.request.AddressCreateRequest;
import com.example.footballclub.dto.request.AddressUpdateRequest;
import com.example.footballclub.dto.response.AddressResponse;
import com.example.footballclub.service.AddressService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressController {
    AddressService addressService;

    @PostMapping
    public ApiResponse<AddressResponse> create(@RequestBody @Valid AddressCreateRequest request) {
        AddressResponse result = addressService.createAddress(request);
        return ApiResponse.<AddressResponse>builder().result(result).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<AddressResponse> updateAddress(@PathVariable String id, @RequestBody @Valid AddressUpdateRequest request) {
        AddressResponse result = addressService.updateAddress(id, request);
        return ApiResponse.<AddressResponse>builder().result(result).build();
    }
}
