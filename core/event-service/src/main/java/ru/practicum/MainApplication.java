package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
    }
}