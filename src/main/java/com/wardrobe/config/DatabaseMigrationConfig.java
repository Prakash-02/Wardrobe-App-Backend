package com.wardrobe.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseMigrationConfig {

    @Bean
    CommandLineRunner migrateUsersTable(JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("ALTER TABLE users ADD COLUMN IF NOT EXISTS username VARCHAR(255)");
            jdbcTemplate.update("UPDATE users SET username = CONCAT('user_', id) WHERE username IS NULL OR username = ''");
            jdbcTemplate.execute("CREATE UNIQUE INDEX IF NOT EXISTS users_username_idx ON users (username)");
            jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN username SET NOT NULL");
            jdbcTemplate.execute("ALTER TABLE users ADD COLUMN IF NOT EXISTS public_profile BOOLEAN NOT NULL DEFAULT FALSE");
        };
    }
}