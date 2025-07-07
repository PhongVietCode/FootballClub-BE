package com.example.footballclub.controller;
import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.request.AuthenticationRequest;
import com.example.footballclub.dto.response.AuthenticationResponse;
import com.example.footballclub.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

//    @PostMapping("/token")
//    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
//            throws ParseException, JOSEException {
//        IntrospectResponse result = authenticationService.introspect(request);
//        return ApiResponse.<IntrospectResponse>builder().result(result).build();
//    }
//
//    @PostMapping("/logout")
//    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
//        authenticationService.logout(request);
//        return ApiResponse.<Void>builder().build();
//    }
//
//    @PostMapping("/refresh")
//    ApiResponse<AuthenticationResponse> logout(@RequestBody RefreshRequest request)
//            throws ParseException, JOSEException {
//        AuthenticationResponse response = authenticationService.refreshToken(request);
//        return ApiResponse.<AuthenticationResponse>builder().result(response).build();
//    }
}
