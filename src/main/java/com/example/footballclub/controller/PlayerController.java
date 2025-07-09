package com.example.footballclub.controller;

import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.request.PlayerCreateRequest;
import com.example.footballclub.dto.request.PlayerListRequest;
import com.example.footballclub.dto.response.PlayerResponse;
import com.example.footballclub.service.PlayerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerController {
    PlayerService playerService;

    @PostMapping
    public ApiResponse<PlayerResponse> joinContest(@RequestBody @Valid PlayerCreateRequest request) {
        PlayerResponse result = playerService.joinContest(request);
        return ApiResponse.<PlayerResponse>builder().result(result).build();
    }
    @PostMapping("/list")
    public ApiResponse<List<PlayerResponse>> joinContestByPlayerList(@RequestBody @Valid PlayerListRequest request) {
        List<PlayerResponse> responses = playerService.listPlayerToJoinContest(request);
        return ApiResponse.<List<PlayerResponse>>builder().result(responses).build();
    }
}
