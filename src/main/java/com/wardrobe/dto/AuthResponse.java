package com.wardrobe.dto;

public record AuthResponse(
        String token,
        Long userId,
        String name,
        String username,
        String email,
        boolean publicProfile
) {}
