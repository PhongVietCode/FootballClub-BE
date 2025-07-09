package com.example.footballclub.controller;

import com.example.footballclub.dto.ApiResponse;
import com.example.footballclub.dto.request.*;
import com.example.footballclub.dto.response.MemberResponse;
import com.example.footballclub.service.MemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {
    MemberService memberService;

//    TODO: refactor to optional
    @GetMapping
    public ApiResponse<List<MemberResponse>> findMember(@RequestParam(required = false, name = "organizationId") String orgId,
                                                        @RequestParam(required = false, name = "name") String name,
                                                        @RequestParam(required = false, name = "contestId") String contestId) {
        List<MemberResponse> result = memberService.getMemberList(MemberQueryRequest.builder().orgId(orgId).name(name).contestId(contestId).build());
        return ApiResponse.<List<MemberResponse>>builder().result(result).build();
    }

    @PostMapping
    public ApiResponse<MemberResponse> createMember(@RequestBody @Valid MemberCreateRequest request) {
        MemberResponse result = memberService.createMember(request);
        return ApiResponse.<MemberResponse>builder().result(result).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<MemberResponse> updateMember(@PathVariable String id, @RequestBody @Valid MemberUpdateRequest request) {
        MemberResponse result = memberService.updateMember(id, request);
        return ApiResponse.<MemberResponse>builder().result(result).build();
    }


}
