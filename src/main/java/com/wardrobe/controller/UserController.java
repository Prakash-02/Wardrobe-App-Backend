package com.wardrobe.controller;

import com.wardrobe.dto.UserSearchResult;
import com.wardrobe.dto.VisibilityRequest;
import com.wardrobe.dto.WardrobeItemResponse;
import com.wardrobe.service.UserService;
import com.wardrobe.service.WardrobeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final WardrobeService wardrobeService;

    public UserController(UserService userService, WardrobeService wardrobeService) {
        this.userService = userService;
        this.wardrobeService = wardrobeService;
    }

    // GET /api/users/search?q=jane
    @GetMapping("/search")
    public List<UserSearchResult> search(@RequestParam("q") String query) {
        return userService.search(query);
    }

    // PUT /api/users/me/visibility  { "publicProfile": true }
    @PutMapping("/me/visibility")
    public VisibilityRequest setVisibility(Authentication auth, @RequestBody VisibilityRequest req) {
        boolean updated = userService.setVisibility(auth.getName(), req.publicProfile());
        return new VisibilityRequest(updated);
    }

    // GET /api/users/{username}/wardrobe  -> 403 if private and not the owner
    @GetMapping("/{username}/wardrobe")
    public List<WardrobeItemResponse> viewWardrobe(
            Authentication auth, HttpServletRequest request, @PathVariable String username
    ) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return wardrobeService.listItemsForUsername(auth.getName(), username, baseUrl);
    }
}
