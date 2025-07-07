package com.example.footballclub.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreateRequest {
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String fullName;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String password;
    String email;
}
