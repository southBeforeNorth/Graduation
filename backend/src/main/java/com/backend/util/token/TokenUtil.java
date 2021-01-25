package com.backend.util.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.feature.maintenance.user.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class TokenUtil {
    private static final long EXPIRETIME = 60*60*1000;
    private static final String TOKENSECRET="JKKLJOoasdlfj";

    public static String sign(User user){
        String token = null;
        try {
            Date expireAt = new Date(System.currentTimeMillis()+EXPIRETIME);
            // json web token
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("Id", user.getId())
                    .withClaim("userName", user.getName())
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKENSECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    public static String verify(String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKENSECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("userName").asString();
        }catch (Exception e){
            return null;
        }
    }
}
