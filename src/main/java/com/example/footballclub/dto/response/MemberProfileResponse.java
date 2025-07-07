package com.example.footballclub.dto.response;

import com.example.footballclub.enums.MemberRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberProfileResponse {
    String id;
    OrganizationResponse organization;
    Float elo;
    MemberRole role;
}
