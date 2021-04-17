package com.salesmicroservices.product.modules.jwt.provider;

import com.salesmicroservices.product.modules.jwt.util.RequestUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtTokenProvider {

    private static final String BLANK_SPACE = " ";
    private static final Integer INDICE_TOKEN = 1;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Authentication getAuthentication(String token) {
        var userDetails = new UserDetails() {
            private static final long serialVersionUID = 1L;

            public boolean isEnabled() {
                return true;
            }

            public boolean isCredentialsNonExpired() {
                return true;
            }

            public boolean isAccountNonLocked() {
                return true;
            }

            public boolean isAccountNonExpired() {
                return true;
            }

            public String getUsername() {
                return "";
            }

            public String getPassword() {
                return "";
            }

            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
        };
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getAccessToken() {
        return resolveToken(RequestUtil.getCurrentRequest());
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
}
