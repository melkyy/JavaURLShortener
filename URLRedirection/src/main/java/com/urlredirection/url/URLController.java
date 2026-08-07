package com.urlredirection.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.util.HashMap;

@Controller
public class URLController {
    @Autowired
    URLRepository repository;

    @GetMapping("/{hashedCode}")
    ResponseEntity<Object> validateCode(@PathVariable String hashedCode){
        URL existHashedCode = repository.findOneByHashedCode(hashedCode);
        if(existHashedCode == null){
            return ResponseEntity.notFound().build();
        }else{
            HttpHeaders headers = new HttpHeaders();
            String urlWebsite = "";
            if(!existHashedCode.getHashedCode().startsWith("http://") || !existHashedCode.getHashedCode().startsWith("https://")){
                urlWebsite = "http://"+URI.create(existHashedCode.getOriginalUrl()).toString();
            }else{
                urlWebsite = URI.create(existHashedCode.getOriginalUrl()).toString();
            }
            headers.setLocation(URI.create(urlWebsite));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }
}
