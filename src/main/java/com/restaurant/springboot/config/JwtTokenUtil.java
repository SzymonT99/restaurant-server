package com.restaurant.springboot.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.entity.UserToken;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.domain.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 4 * 60 * 60;

    private final UserTokenRepository userTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    public JwtTokenUtil(UserTokenRepository userTokenRepository, UserRepository userRepository) {
        this.userTokenRepository = userTokenRepository;
        this.userRepository = userRepository;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String token = doGenerateToken(claims, userDetails.getUsername());

        User user = userRepository.findByLogin(userDetails.getUsername());
        if (userTokenRepository.existsByUser(user)) {
            UserToken updatedUserToken = userTokenRepository.findByUser(user);
            updatedUserToken.setToken(token);
            updatedUserToken.setActive(true);
            userTokenRepository.save(updatedUserToken);
        } else {
            UserToken newUserToken = new UserToken(token, user, true);
            userTokenRepository.save(newUserToken);
        }
        return token;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails, Long userId) {
        if(token == null || userDetails == null || userId == null) return false;
        final String username = getUsernameFromToken(token);
        User sender = userRepository.findById(userId).orElse(null);
        UserToken userToken = userTokenRepository.findByToken(token).orElse(null);
        if (userToken == null) return false;
        boolean tokenState = userToken.isActive();

        return (username.equals(userDetails.getUsername())
                && username.equals(Objects.requireNonNull(sender).getLogin())
                && tokenState
                && !isTokenExpired(token));
    }
}
