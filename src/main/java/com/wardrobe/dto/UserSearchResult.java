package com.wardrobe.dto;

import com.wardrobe.model.User;

public record UserSearchResult(
        String username,
        String name,
        boolean publicProfile
) {
    public static UserSearchResult from(User user) {
        return new UserSearchResult(user.getUsername(), user.getName(), user.isPublicProfile());
    }
}
