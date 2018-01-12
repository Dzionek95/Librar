package com.aldona.library.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
class SecurityUtilities {

    String retrieveNameFromAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

}
