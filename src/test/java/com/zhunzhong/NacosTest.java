package com.zhunzhong;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NacosTest {


    @Test
    public void createToken() {
        String userName = "nacos";
        String encodedSecretKey = "SecretKey012345678901234567890123456789012345678901234567890123456789zz";
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecretKey));

        Date validity = new Date(
                System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60 * 10));

        Claims claims = Jwts.claims().setSubject(userName);
        String token = Jwts.builder().setClaims(claims).setExpiration(validity).signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        System.out.println(token);
    }
}
