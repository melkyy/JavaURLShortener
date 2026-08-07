package com.urlshortener.user;
import java.util.UUID;
public class UserService {
    public static String generateId() {
        UUID uuidv7 = UUID.randomUUID();
        return uuidv7.toString();
    }
}
