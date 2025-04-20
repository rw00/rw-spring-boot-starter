package com.rw.apps.starter.common.security.jwt;

import com.rw.apps.starter.common.exceptions.auth.UnauthorizedException;
import com.rw.apps.starter.common.exceptions.auth.UnauthorizedExpiredSessionException;
import com.rw.apps.starter.common.security.model.CurrentUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.MacAlgorithm;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtService {
    static final MacAlgorithm SIGNING_ALGORITHM = SIG.HS384;
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    private static final String KEY_ALGO = "HmacSHA384";
    private static final String USER_CLAIM_KEY = "user";
    private final SecretKey signingKey;
    private final Duration expiration;

    public JwtService(JwtConfigProperties jwtConfigProperties) {
        byte[] key = Base64.getDecoder().decode(jwtConfigProperties.secret().getBytes(StandardCharsets.UTF_8));
        this.signingKey = new SecretKeySpec(key, KEY_ALGO);
        this.expiration = jwtConfigProperties.expiration();
    }

    public String generateToken(CurrentUser currentUser) {
        long expiryTimeInMillis = System.currentTimeMillis() + expiration.toMillis();
        return Jwts.builder()
                   .subject(currentUser.getUsername())
                   .issuedAt(new Date())
                   .expiration(new Date(expiryTimeInMillis))
                   .claim(USER_CLAIM_KEY, currentUser)
                   .signWith(signingKey, SIGNING_ALGORITHM)
                   .compact();
    }

    public CurrentUser parseJwt(String token) {
        try {
            Map<String, Class<?>> jsonDeserializerMap = Map.of(USER_CLAIM_KEY, CurrentUser.class);
            Claims claims = (Claims) Jwts.parser()
                                         .verifyWith(signingKey)
                                         .json(new JacksonDeserializer<>(jsonDeserializerMap))
                                         .build()
                                         .parse(token)
                                         .getPayload();
            return claims.get(USER_CLAIM_KEY, CurrentUser.class);
        } catch (ExpiredJwtException e) {
            log.debug("Expired Jwt", e);
            throw new UnauthorizedExpiredSessionException();
        } catch (Exception e) {
            log.warn("Invalid Jwt", e);
            throw new UnauthorizedException("Invalid login");
        }
    }
}
