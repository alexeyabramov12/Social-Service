package ru.skillbox.diplom.group33.social.service.config.security;


import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import ru.skillbox.diplom.group33.social.service.model.auth.Role;
import ru.skillbox.diplom.group33.social.service.model.auth.User;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret.access}")
    private String jwtAccessSecret;
    @Value("${jwt.secret.refresh}")
    private String jwtRefreshSecret;
    @Value("${jwt.secret.access}")
    private String secret;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        jwtAccessSecret = Base64.getEncoder().encodeToString(jwtAccessSecret.getBytes());
        jwtRefreshSecret = Base64.getEncoder().encodeToString(jwtRefreshSecret.getBytes());
    }

    public String createAccessToken(@NonNull User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", getTypesNames(user.getRoles()));
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());

        final ZonedDateTime now = ZonedDateTime.now();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.plusMinutes(60).toInstant()))
                .signWith(SignatureAlgorithm.HS256, jwtAccessSecret)
                .compact();
    }

    public String createRefreshToken(@NonNull User user) {
        final ZonedDateTime now = ZonedDateTime.now();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.plusDays(30).toInstant()))
                .signWith(SignatureAlgorithm.HS256, jwtRefreshSecret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    public boolean validateToken(@NonNull String token, @NonNull String secret) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    private String getName(String token) {
        return Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getUserId(String token) {
        Jwt jwt = jwtDecoder().decode(token);
        return jwt.getClaim("userId");
    }

    private JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

    private List<String> getTypesNames(List<Role> roles) {
        List<String> result = new ArrayList<>();
        roles.forEach(t -> result.add(t.getName()));
        return result;
    }
}
