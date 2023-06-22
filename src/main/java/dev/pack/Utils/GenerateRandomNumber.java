package dev.pack.Utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateRandomNumber {

    public String generateAccountNumber() {
        Random random_number = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randomDigit = random_number.nextInt(9) + 1;
            builder.append(randomDigit);
        }

        return builder.toString();
    }

}
