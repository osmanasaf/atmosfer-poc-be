package org.atmosfer.seed.businessservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.atmosfer.seed.businessservice.type.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String HEADER_STRING = "Authorization";

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
    }

    private String getToken(){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();

        String token = request.getHeader(HEADER_STRING);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token;
    }

    public UserRole getUserRoleFromToken(String token){

        Claims claims = getClaims(token);
        String role = (String) claims.get("role");
        if (role != null) {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            return userRole;
        }
        return null;
    }

    public UserRole getCurrentUserRole() {
        String token = getToken();
        return getUserRoleFromToken(token);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String getUsernameFromToken(){
        String token = getToken();
        return getUsernameFromToken(token);
    }

}