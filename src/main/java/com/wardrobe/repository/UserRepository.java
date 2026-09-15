package com.wardrobe.repository;

import com.wardrobe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    // Simple "contains" search, case-insensitive. Caller is responsible for
    // limiting result size (see UserController) so this can't be abused to
    // dump the whole user table with a one-character query.
    List<User> findTop20ByUsernameContainingIgnoreCase(String usernameFragment);
}
