package com.wardrobe.service;

import com.wardrobe.dto.WardrobeItemResponse;
import com.wardrobe.model.User;
import com.wardrobe.model.WardrobeItem;
import com.wardrobe.repository.UserRepository;
import com.wardrobe.repository.WardrobeItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class WardrobeService {

    private final WardrobeItemRepository itemRepository;
    private final UserRepository userRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public WardrobeService(WardrobeItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public WardrobeItemResponse addItem(String ownerEmail, String name, String category,
                                         String color, MultipartFile image, String baseUrl) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        String filename = saveImageFile(image);

        WardrobeItem item = new WardrobeItem();
        item.setOwner(owner);
        item.setName(name);
        item.setCategory(category);
        item.setColor(color);
        item.setImageFilename(filename);
        itemRepository.save(item);

        return WardrobeItemResponse.from(item, baseUrl);
    }

    public List<WardrobeItemResponse> listItems(String ownerEmail, String baseUrl) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return itemRepository.findByOwnerIdOrderByCreatedAtDesc(owner.getId())
                .stream()
                .map(item -> WardrobeItemResponse.from(item, baseUrl))
                .toList();
    }

    public void deleteItem(String ownerEmail, Long itemId) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!itemRepository.existsByIdAndOwnerId(itemId, owner.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        itemRepository.deleteById(itemId);
    }

    private String saveImageFile(MultipartFile image) {
        try {
            Path dir = Path.of(uploadDir);
            Files.createDirectories(dir);

            String extension = image.getOriginalFilename() != null && image.getOriginalFilename().contains(".")
                    ? image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf('.'))
                    : ".png";
            String filename = UUID.randomUUID() + extension;

            Files.copy(image.getInputStream(), dir.resolve(filename));
            return filename;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not save image", e);
        }
    }
}
