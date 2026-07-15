package com.urlredirection.url;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "urls")
public class URL {
    private @Id
    @GeneratedValue Long id;
    private String originalUrl;
    private String hashedCode;
    private String userId;
    private String createdDate;

    public URL(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUser_id(String getUserId) {
        this.userId = getUserId;
    }

    public String getHashedCode() {
        return hashedCode;
    }

    public void setHashedCode(String hashedCode) {
        this.hashedCode = hashedCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreated_date(String created_date) {
        this.createdDate = created_date;
    }
}

