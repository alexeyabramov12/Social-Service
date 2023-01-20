package ru.skillbox.diplom.group33.social.service.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "whitelist")
@ConfigurationPropertiesScan
public class EndPoints {
   private String[] permit;
}


