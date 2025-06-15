package com.hitwh.controller;

import com.hitwh.service.LoginService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 */
@Slf4j
@RestController
@RequestMapping()
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功的话返回token
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return loginService.login(username, password);
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回注册是否成功
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        return loginService.register(username, password);
    }
}
