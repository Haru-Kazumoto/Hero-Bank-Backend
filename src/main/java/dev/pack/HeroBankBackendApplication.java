package dev.pack;

import dev.pack.GlobalException.GlobalExceptionHandling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalExceptionHandling.class)
public class HeroBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeroBankBackendApplication.class, args);
    }

}
