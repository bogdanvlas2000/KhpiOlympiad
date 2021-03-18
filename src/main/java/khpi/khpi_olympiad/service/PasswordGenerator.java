package khpi.khpi_olympiad.service;

import org.apache.commons.text.RandomStringGenerator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGenerator {

    public String generatePassword(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
