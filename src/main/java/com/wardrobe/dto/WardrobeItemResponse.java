package com.wardrobe.dto;

import com.wardrobe.model.WardrobeItem;

import java.time.Instant;

public record WardrobeItemResponse(
        Long id,
        String name,
        String category,
        String color,
        String imageUrl,
        Instant createdAt
) {
    public static WardrobeItemResponse from(WardrobeItem item, String baseUrl) {
        return new WardrobeItemResponse(
                item.getId(),
                item.getName(),
                item.getCategory(),
                item.getColor(),
                baseUrl + "/uploads/" + item.getImageFilename(),
                item.getCreatedAt()
        );
    }
}
