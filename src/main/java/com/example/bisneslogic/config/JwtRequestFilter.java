//package com.example.bisneslogic.config;
//
//
//import com.example.bisneslogic.util.JwtTokenUtils;
//import io.jsonwebtoken.ExpiredJwtException;
//
//import io.jsonwebtoken.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JwtRequestFilter extends OncePerRequestFilter {
//    private final JwtTokenUtils jwtTokenUtils;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.debug("Checking access for URL: {}", request.getRequestURI());
//
//        String authHeader = request.getHeader("Authorization");
//        String username = null;
//        String jwt = null;
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(7);
//            try {
//                username = jwtTokenUtils.getUsername(jwt);
//            } catch (ExpiredJwtException e) {
//                log.debug("Время жизни токена вышло");
//            } catch (SignatureException e) {
//                log.debug("Подпись неправильная");
//            }
//        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            log.debug("Access granted for URL: {}", request.getRequestURI());
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                    username,
//                    null,
//                    jwtTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
//            );
//            SecurityContextHolder.getContext().setAuthentication(token);
//            //            response.addHeader("Token-Type", "JWT");
//        }
//        filterChain.doFilter(request, response);
//    }
//
//
//}