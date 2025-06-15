package com.hitwh.service.impl;

import com.hitwh.entity.User;
import com.hitwh.repository.UserRepository;
import com.hitwh.service.LoginService;
import com.hitwh.utils.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserRepository userRepository;


    @Override
    public ResponseEntity<String> login(String username, String password) {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户名不存在");
        }
        User user = optional.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        return ResponseEntity.ok().body(JWTUtil.getToken(map));
    }

    @Override
    public ResponseEntity<String> register(String username, String password) {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return ResponseEntity.ok("注册成功");
    }
}
