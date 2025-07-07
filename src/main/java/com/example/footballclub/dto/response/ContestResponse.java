package com.example.footballclub.dto.response;


import com.example.footballclub.entity.Address;
import com.example.footballclub.entity.Contest;
import com.example.footballclub.entity.Team;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContestResponse {
    String id;
    String address;
    String addressName;
    Integer teamCount;
    LocalDateTime localDateTime;
    List<TeamSplitResponse> teams;
}
