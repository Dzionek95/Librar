package com.bartek.library.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtilities {

    public String retrieveNameFromAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

}
