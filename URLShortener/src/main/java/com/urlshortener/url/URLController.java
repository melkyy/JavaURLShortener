package com.urlshortener.url;

import com.urlshortener.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
public class URLController extends BaseController {

    private final URLService urlService;
    private final URLRepository repository;
    private final AuthenticationManager authenticationManager;
    // Clean Constructor Injection forces Spring to handle dependencies accurately
    public URLController(URLService urlService, URLRepository repository, AuthenticationManager authenticationManager) {
        this.urlService = urlService;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/url")
    public ResponseEntity<List<URL>> all(){
        try {
            List<URL> urls = repository.findByUserId(getAuthenticatedUserId());
            return ResponseEntity.ok(urls);
        } catch (Exception e) {
            System.out.println("====== CRITICAL SERIALIZATION CRASH EXPOSED ======");
            e.printStackTrace(); // This prints the true problem directly to your terminal logs
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Mapping error", e);
        }
    }

    @PostMapping("/url")
    public URL newURL(@RequestBody URL url){
        url.setHashedCode(urlService.generateCode());
        url.setUser_id(getAuthenticatedUserId());
        url.setCreated_date(String.valueOf(LocalDate.now()));
        System.out.println("HOLA MUNDO AQUI ESTA ESTA URL:: "+url.getOriginalUrl());
        return repository.save(url);
    }

    @GetMapping("/url/{id}")
    public URL one(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id not found"));
    }

    @DeleteMapping("/url/{id}")
    public void deleteURL(@PathVariable Long id){
        repository.deleteById(id);
    }
}