package com.wardrobe.service;

import com.wardrobe.dto.UserSearchResult;
import com.wardrobe.model.User;
import com.wardrobe.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserSearchResult> search(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        return userRepository.findTop20ByUsernameContainingIgnoreCase(query.trim())
                .stream()
                .map(UserSearchResult::from)
                .toList();
    }

    public boolean setVisibility(String requesterEmail, boolean publicProfile) {
        User user = userRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        user.setPublicProfile(publicProfile);
        userRepository.save(user);
        return user.isPublicProfile();
    }
}
