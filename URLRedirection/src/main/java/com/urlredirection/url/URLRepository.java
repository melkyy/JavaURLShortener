package com.urlredirection.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface URLRepository extends JpaRepository<URL, Long> {
    URL findOneByHashedCode(String hashedCode);
}
