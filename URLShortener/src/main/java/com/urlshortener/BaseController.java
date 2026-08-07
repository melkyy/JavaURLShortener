package com.urlshortener;

import com.urlshortener.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    protected User getAuthenticatedUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof User){
            return (User) authentication.getPrincipal();
        }
        throw new IllegalStateException();
    }
    protected String getAuthenticatedUserId(){
        return getAuthenticatedUser().getId();
    }
}
