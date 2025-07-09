package com.example.footballclub.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberUpdateRequest {
    @Range(min = 0,max = 10, message = "INVALID_ELO")
    Float elo;
    String role;
    String status;
    String name;
}
