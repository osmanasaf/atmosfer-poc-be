package org.atmosfer.seed.businessservice.config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.businessservice.dto.ErrorDto;
import org.atmosfer.seed.businessservice.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtils jwtTokenUtil;
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken, email;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                sendError(response, "Unable to get JWT Token");
                return;
            } catch (ExpiredJwtException e) {
                sendError(response, "JWT Token has expired");
                return;
            } catch (Exception e) {
                sendError(response, "wrong jwt");
                return;
            }
        } else {
            sendError(response, "JWT Token does not begin with Bearer String");
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email, null, null));
        chain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse res, String errMssg) {
        ErrorDto dto = new ErrorDto();
        dto.setErrorMessage(errMssg);
        dto.setResultCode(HttpStatus.BAD_REQUEST.value());
        dto.setResult(HttpStatus.BAD_REQUEST.name());
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        try {
            res.getOutputStream().print(objectMapper.writeValueAsString(dto));
            res.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
