package com.urlshortener.user;

import com.urlshortener.auth.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository repository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    UserController(UserRepository repo, JWTService JWTServce,AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.repository = repo;
        this.jwtService = JWTServce;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    List<User> all() {
        return repository.findAll();
    }

    @PostMapping("/register")
    User newUser(@RequestBody User user) {
        User newUser = new User();
        repository.findByUsername(user.getUsername().toLowerCase()).map(existingUser -> {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        });

        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setId(UserService.generateId());
        return repository.save(newUser);
    }

    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No User Found"));
    }

    @PutMapping("/user/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id).map(user -> {
            user.setUsername(newUser.getUsername());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            return repository.save(user);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> LogIn(@RequestBody User userBody) {
        try {
            System.out.println(userBody.getUsername());
            System.out.println(passwordEncoder.encode(userBody.getPassword()));
            User existingUser = new User();
            repository.findByUsername(userBody.getUsername().toLowerCase()).map(userResult -> {
                existingUser.setUsername(userResult.getUsername());
                existingUser.setPassword(userResult.getPassword());
                existingUser.setId(userResult.getId());
                return existingUser;
            }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
            boolean ismach = passwordEncoder.matches(userBody.getPassword(), existingUser.getPassword());
            if(!ismach){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(existingUser.getUsername(), userBody.getPassword()));
            String id = existingUser.getId();
            String token = jwtService.generateToken(existingUser.getUsername(), existingUser.getId());
            System.out.println("token :: " + token);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Map.of("message", "error"));
        }
    }


}
