package com.wardrobe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "wardrobe_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WardrobeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String name;

    private String category; // e.g. "Dress", "Top", "Shoes"
    private String color;

    // Relative filename under app.upload.dir, served via /uploads/<imageFilename>
    @Column(nullable = false)
    private String imageFilename;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}
