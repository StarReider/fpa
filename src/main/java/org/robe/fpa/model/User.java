package org.robe.fpa.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    private Long userId;
    @NotNull
    private String username;
    @NotNull
    private String passwordHash;
    @NotNull
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
