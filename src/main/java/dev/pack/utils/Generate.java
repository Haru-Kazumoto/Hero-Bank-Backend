package dev.pack.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Generate {

    @Value("${payment.top-up.history.prefix-history-id}") String prefixIdPayment;
    @Value("${payment.top-up.history.char}") String character;
    @Value("${payment.top-up.history.length-history-id}") int lengthIdChar;

    public String randomPaymentId(){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < lengthIdChar; i ++ ){
            int index = random.nextInt(character.length());
            char randomChar = character.charAt(index);

            builder.append(randomChar);
        }

        return prefixIdPayment + builder;
    }

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
