package org.robe.fpa.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {
    private Long userId;
    private String username;
    private String passwordHash;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
