package dev.pack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HeroBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeroBankBackendApplication.class, args);
    }

}
