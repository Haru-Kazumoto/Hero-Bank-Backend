package dev.pack;

import dev.pack.globalException.GlobalExceptionHandling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Import(GlobalExceptionHandling.class)
@EnableTransactionManagement
public class HeroBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeroBankBackendApplication.class, args);
    }
}
