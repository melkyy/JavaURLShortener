package com.urlshortener.url;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.math.BigInteger;

@Service
public class URLService {
    public String generateCode(){
        String BASE62_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        BigInteger BASE = BigInteger.valueOf(62);

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rnd.nextInt(10));
        }
        String randomCode = sb.toString();
        System.out.println("Random code generated! :"+ randomCode);
        byte[] bytes = randomCode.getBytes();
        BigInteger number = new BigInteger(1, bytes);
        StringBuilder result = new StringBuilder();
        while(number.compareTo(BigInteger.ZERO) > 0){
            BigInteger[] quotientAndRemainder = number.divideAndRemainder(BASE);
            result.append(BASE62_ALPHABET.charAt(quotientAndRemainder[1].intValue()));
            number = quotientAndRemainder[0];
        }
        return result.isEmpty() ? "0" : result.reverse().toString();
    }
}
