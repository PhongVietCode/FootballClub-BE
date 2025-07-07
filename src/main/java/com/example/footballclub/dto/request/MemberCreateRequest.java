package com.example.footballclub.dto.request;

import com.example.footballclub.enums.MemberStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberCreateRequest {
    @Range(min = 0,max = 10, message = "INVALID_ELO")
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    Float elo;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String organizationId;
    String status;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String name;
    String userId;
}
