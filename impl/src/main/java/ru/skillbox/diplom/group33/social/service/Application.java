package ru.skillbox.diplom.group33.social.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.skillbox.diplom.group33.social.service.repository.base.BaseRepositoryImpl;

@EnableScheduling
@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(basePackages = "ru.skillbox.diplom.group33.social.service.repository",
        repositoryBaseClass = BaseRepositoryImpl.class)
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }
}