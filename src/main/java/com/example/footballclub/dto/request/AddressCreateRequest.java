package com.example.footballclub.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressCreateRequest {
    @Size(min = 0, max = 255, message = "INVALID_STRING_LENGTH")
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String name;
    @Size(min = 0, max = 255, message = "INVALID_STRING_LENGTH")
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String address;
    @NotNull(message = "ATTRIBUTE_NOT_NULL")
    String orgId;
    @Size(min = 0, max = 255, message = "INVALID_STRING_LENGTH")
    String mapUrl;
}
