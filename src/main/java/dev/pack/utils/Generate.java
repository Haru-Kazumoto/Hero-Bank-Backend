package dev.pack.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Generate {
    public String randomIdNumber(int num) {
        Random random_number = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < num; i++) {
            int randomDigit = random_number.nextInt(9) + 1;
            builder.append(randomDigit);
        }

        return builder.toString();
    }
}
