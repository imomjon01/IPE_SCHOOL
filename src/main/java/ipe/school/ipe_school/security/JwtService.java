package ipe.school.ipe_school.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ipe.school.ipe_school.models.entity.Roles;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Role;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;

    public String generateToken(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return "Bearer " + Jwts.builder()
                .subject(phoneNumber)
                .claim("roles", user.getRoles().stream().map(Roles::getName).collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000L * 60 * 60 * 24)) // 1 kun
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor("123123123123123123123123123123123".getBytes());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public User getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String phoneNumber = claims.getSubject();
        String roles = (String) claims.get("roles");
        List<Roles> authorities = Arrays.stream(roles.split(",")).map(Roles::new).toList();
        return new User(phoneNumber, authorities);
    }
}
