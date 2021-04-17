package com.salesmicroservices.auth.modules.jwt;

import com.salesmicroservices.auth.modules.user.UserService;
import com.salesmicroservices.auth.modules.user.model.User;
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

    private static final String BLANK_SPACE = " ";
    private static final Integer INDICE_TOKEN = 1;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length-in-minutes}")
    private Integer expire;

    @Autowired
    private UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user, List<String> roles){
        var claims = Jwts.claims().setSubject(user.getUsername());

        claims.put("username", user.getUsername());
        claims.put("userId", user.getId());
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
        var bearerToken = req.getHeader(AUTHORIZATION_HEADER);
        var token = Strings.EMPTY;
        if (!isEmpty(bearerToken)) {
            if (bearerToken.contains(BLANK_SPACE)) {
                token = bearerToken.split(BLANK_SPACE)[INDICE_TOKEN];
                return token;
            } else {
                return bearerToken;
            }
        }
        return Strings.EMPTY;
    }

    public boolean validateToken(String token) {
        try {
            var claims = getClaims(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    public Date gerarExpiracao() {
        return Date.from(
            LocalDateTime.now()
                .plusMinutes(expire)
                .atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}
