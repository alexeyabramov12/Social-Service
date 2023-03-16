package ru.skillbox.diplom.group33.social.service.config.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cloudinary")
@ConfigurationPropertiesScan
public class CloudinaryEndPoints {

    private String cloud_name;
    private String api_key;
    private String api_secret;
    private String defaultPhoto;
}
