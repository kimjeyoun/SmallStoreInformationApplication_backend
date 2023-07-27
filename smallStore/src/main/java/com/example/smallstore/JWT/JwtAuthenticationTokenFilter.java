package com.example.smallstore.JWT;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println(path);

        if (path.contains("/swagger-ui/index.html") || path.contains("/users/login") || path.contains("/users/signup") || path.contains("/users/email") || path.contains("/users/emailVerify")) {
            System.out.println("check");
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveAccessToken(request);
        System.out.println(token);

        if(token == null){
            String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
            if (jwtTokenProvider.validateToken(refreshToken)) {
                token = jwtTokenProvider.reissueAccessToken(refreshToken);
                jwtTokenProvider.setHeaderAccessToken(response, token);
                this.setAuthentication(token);
            }
        } else if(token != null){
            if(jwtTokenProvider.validateToken(token)){
                this.setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }


    // SecurityContext에 Authentication 저장
    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
