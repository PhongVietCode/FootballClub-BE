package com.example.footballclub.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String name;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String description;
}
