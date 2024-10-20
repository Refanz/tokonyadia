package com.refanzzzz.tokonyadia.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import com.refanzzzz.tokonyadia.service.JwtService;
import com.refanzzzz.tokonyadia.service.RedisService;
import com.refanzzzz.tokonyadia.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${tokonyadia.jwt-secret}")
    private String JWT_SECRET_KEY;

    @Value("${tokonyadia.jwt-expiration-in-minutes}")
    private Long JWT_EXP_IN_MINUTES;

    @Value("${tokonyadia.jwt-issuer}")
    private String JWT_ISSUER;

    private final String BLACKLISTED = "BLACKLISTED";

    private final RedisService redisService;

    @Override
    public String generateAccessToken(UserAccount userAccount) {
        log.info("Generating JWT Token for User: {}", userAccount.getId());

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
        log.info("Extract JWT Token - {}", System.currentTimeMillis());
        DecodedJWT decodedJWT = extractClaimJWT(token);

        if (decodedJWT != null)
            return decodedJWT.getSubject();

        return null;
    }

    @Override
    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return JwtUtil.parseJwtToken(bearerToken);
    }

    @Override
    public void blacklistAccessToken(String bearerToken) {
        String token = JwtUtil.parseJwtToken(bearerToken);

        DecodedJWT decodedJWT = extractClaimJWT(token);

        if (decodedJWT == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Token invalid!");

        Date expiresAt = decodedJWT.getExpiresAt();
        long timeLeft = expiresAt.getTime() - System.currentTimeMillis();

        redisService.save(token, BLACKLISTED, Duration.ofMillis(timeLeft));
    }

    @Override
    public Boolean isTokenBlacklisted(String token) {
        String blacklistedToken = redisService.get(token);
        return blacklistedToken != null && blacklistedToken.equals(BLACKLISTED);
    }

    private DecodedJWT extractClaimJWT(String jwtToken) {
        log.info("Extract JWT Token: {}", System.currentTimeMillis());

        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();

            return verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            log.error("Error while validating JWT Token: {}", e.getMessage());
            return null;
        }
    }

}
