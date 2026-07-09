package com.urlshortener.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {
    List<URL> findByUserId(Long userId);
    URL findOneByHashedCode(String hashedCode);
}
