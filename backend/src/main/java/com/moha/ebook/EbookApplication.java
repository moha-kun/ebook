package com.moha.ebook;

import com.moha.ebook.dto.RegisterRequest;
import com.moha.ebook.entities.Role;
import com.moha.ebook.services.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

import static com.moha.ebook.entities.Role.ADMIN;
import static com.moha.ebook.entities.Role.MANAGER;

@SpringBootApplication
public class EbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbookApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();

            var manager = RegisterRequest.builder()
                    .firstName("manager")
                    .lastName("manager")
                    .email("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();


            System.out.println("~~ Admin token: " + service.register(admin).getAccessToken());
            System.out.println("~~ Manager token: " + service.register(manager).getAccessToken());
        };
    }
}


