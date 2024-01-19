package com.taosisheng.common;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

public class CommonUse {

    public long time = 1000*60*60*24;
    public String signature = "admin";

    public String jwt(){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                .claim("username", "tom")
                .claim("role", "admin")

                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())

                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return jwtToken;
    }

}
