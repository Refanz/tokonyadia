package com.refanzzzz.tokonyadia.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import com.refanzzzz.tokonyadia.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${tokonyadia.jwt-secret}")
    private String JWT_SECRET_KEY;

    @Value("${tokonyadia.jwt-expiration-in-minutes}")
    private Long JWT_EXP_IN_MINUTES;

    @Value("${tokonyadia.jwt-issuer}")
    private String JWT_ISSUER;

    @Override
    public String generateAccessToken(UserAccount userAccount) {
//        log.info("Generating JWT Token for User: {}", userAccount.getId());

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            return JWT.create()
                    .withIssuer(JWT_ISSUER)
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plus(JWT_EXP_IN_MINUTES, ChronoUnit.MINUTES))
                    .withSubject(userAccount.getId())
                    .withClaim("role", userAccount.getRole().getDescription())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    @Override
    public String getUserId(String token) {
//        log.info("Extract JWT Token - {}", System.currentTimeMillis());

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
