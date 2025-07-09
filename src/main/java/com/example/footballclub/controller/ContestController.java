package com.example.footballclub.controller;

import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.request.*;
import com.example.footballclub.dto.response.ContestResponse;
import com.example.footballclub.dto.response.TeamSplitResponse;
import com.example.footballclub.service.ContestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContestController {
    ContestService contestService;

    @PostMapping
    public ApiResponse<ContestResponse> create(@RequestBody @Valid ContestCreateRequest request) {
        ContestResponse result = contestService.createContest(request);
        return ApiResponse.<ContestResponse>builder().result(result).build();
    }

    @GetMapping("/{id}/teams")
    public ApiResponse<List<TeamSplitResponse>> splitTeam(@PathVariable String id) {
        List<TeamSplitResponse> result = contestService.splitTeam(id);
        return ApiResponse.<List<TeamSplitResponse>>builder().result(result).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<ContestResponse> updateContest(@PathVariable String id, @RequestBody @Valid ContestUpdateRequest request) {
        ContestResponse result = contestService.updateContest(id, request);
        return ApiResponse.<ContestResponse>builder().result(result).build();
    }
    @GetMapping("/list")
    public ApiResponse<List<ContestResponse>> getContestList(@RequestParam(required = false) String orgId) {
        List<ContestResponse> result = contestService.getContestList(orgId);
        return ApiResponse.<List<ContestResponse>>builder().result(result).build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ContestResponse> getContest(@PathVariable String id) {
        ContestResponse result = contestService.getContestDetail(id);
        return ApiResponse.<ContestResponse>builder().result(result).build();
    }

    @DeleteMapping("/{contestId}")
    public ApiResponse<String> deleteContestById(@PathVariable String contestId){
        contestService.deleteContest(contestId);
        return ApiResponse.<String>builder().result("Deleted successfully").build();

    }

}
