package com.example.usermanagement.dto;

import com.example.usermanagement.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateRequest {
    @NotNull(message = "Role cannot be null")
    private Role role;
}
