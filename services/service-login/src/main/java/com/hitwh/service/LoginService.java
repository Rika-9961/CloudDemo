package com.hitwh.service;

import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<String> login(String username, String password);

    ResponseEntity<String> register(String username, String password);
}
