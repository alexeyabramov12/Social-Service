package ru.skillbox.diplom.group33.social.service.config.storage;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Getter
@Setter
@RequiredArgsConstructor
public class StorageConfig {

    private final CloudinaryEndPoints cloudinaryEndPoints;

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", cloudinaryEndPoints.getCloud_name());
        config.put("api_key", cloudinaryEndPoints.getApi_key());
        config.put("api_secret", cloudinaryEndPoints.getApi_secret());
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}
