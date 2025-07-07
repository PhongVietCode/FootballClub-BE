package com.example.footballclub.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerCreateRequest {
    @Builder.Default
    Boolean isCustomer = false;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    Float elo;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String orgId;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String contestId;
    String memberId;
}
