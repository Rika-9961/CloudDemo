package com.hitwh.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

@Component
public class JWTUtil {
    public static final String key = "k8s/s6";

    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 1);
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        System.out.println(instance.getTime());
        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(key));
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(key)).build().verify(token);
    }

    public boolean validateToken(String token) {
        try {
            verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
