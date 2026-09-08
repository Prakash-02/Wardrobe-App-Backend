package com.wardrobe.controller;

import com.wardrobe.dto.WardrobeItemResponse;
import com.wardrobe.service.WardrobeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/wardrobe/items")
public class WardrobeController {

    private final WardrobeService wardrobeService;

    public WardrobeController(WardrobeService wardrobeService) {
        this.wardrobeService = wardrobeService;
    }

    @GetMapping
    public List<WardrobeItemResponse> list(Authentication auth, HttpServletRequest request) {
        return wardrobeService.listItems(auth.getName(), baseUrl(request));
    }

    // multipart/form-data: fields "name", "category", "color", and file field "image"
    @PostMapping(consumes = "multipart/form-data")
    public WardrobeItemResponse add(
            Authentication auth,
            HttpServletRequest request,
            @RequestParam String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String color,
            @RequestParam MultipartFile image
    ) {
        return wardrobeService.addItem(auth.getName(), name, category, color, image, baseUrl(request));
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication auth, @PathVariable Long id) {
        wardrobeService.deleteItem(auth.getName(), id);
    }

    private String baseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
