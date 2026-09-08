package com.wardrobe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WardrobeApplication {
    public static void main(String[] args) {
        SpringApplication.run(WardrobeApplication.class, args);
    }

    @Configuration
    static class StaticFilesConfig implements WebMvcConfigurer {
        @Value("${app.upload.dir}")
        private String uploadDir;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // Serves saved wardrobe images at http://localhost:8080/uploads/<filename>
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + uploadDir + "/");
        }
    }
}
