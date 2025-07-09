package com.example.footballclub.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
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
public class ContestCreateRequest {
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String memberId;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String addressId;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    Integer teamCount;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    LocalDateTime dateTime;

    List<String> players;
}
