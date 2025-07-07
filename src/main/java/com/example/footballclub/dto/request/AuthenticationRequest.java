package com.example.footballclub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @Size(max = 255, message = "INVALID_STRING_LENGTH")
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String userName;
    @Size(max = 255, message = "INVALID_STRING_LENGTH")
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String password;
}
