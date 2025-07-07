package com.example.footballclub.dto.request;


import com.example.footballclub.dto.response.PlayerResponse;
import com.example.footballclub.entity.Player;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamSplitRequest {
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    Integer teamCount;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    List<String> players;
}
