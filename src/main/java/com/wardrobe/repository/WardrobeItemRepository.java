package com.wardrobe.repository;

import com.wardrobe.model.WardrobeItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardrobeItemRepository extends JpaRepository<WardrobeItem, Long> {
    List<WardrobeItem> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
    boolean existsByIdAndOwnerId(Long id, Long ownerId);
}
