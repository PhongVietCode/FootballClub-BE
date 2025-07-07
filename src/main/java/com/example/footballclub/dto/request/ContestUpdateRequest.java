package com.example.footballclub.dto.request;

import com.example.footballclub.dto.TeamSplitted;
import com.example.footballclub.dto.response.TeamSplitResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContestUpdateRequest {
    String addressId;
    LocalDateTime dateTime;
    List<TeamSplitted> teamSplitted;
}


