package com.example.footballclub.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationUpdateRequest {
    @Size(min = 0, max = 255, message = "INVALID_STRING_LENGTH")
    String name;
    @Size( max = 255, message = "INVALID_STRING_LENGTH")
    String logoUrl;
}
