package com.salesmicroservices.auth.modules.jwt;

import com.salesmicroservices.auth.modules.user.UserService;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtTokenProvider {

    private static final Integer DEZ_MINUTOS = 10;
    private static final String BEARER = "bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long expire;

    @Autowired
    private UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles){

        var claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        return Jwts
            .builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(gerarExpiracao())
            .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Authentication getAuthentication(String token) {
        var userDetails = userService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
        if (!isEmpty(bearerToken) && bearerToken.toLowerCase().startsWith(BEARER)) {
            bearerToken = bearerToken.toLowerCase();
            bearerToken = bearerToken.replace(BEARER, Strings.EMPTY);
            return bearerToken;
        }
        return Strings.EMPTY;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Date gerarExpiracao() {
        return Date.from(
            LocalDateTime.now()
                .plusMinutes(DEZ_MINUTOS)
                .atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}
