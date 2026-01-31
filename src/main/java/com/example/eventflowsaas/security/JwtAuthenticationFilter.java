package com.example.eventflowsaas.security;

import com.example.eventflowsaas.service.BlockListTokenService;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    protected final JwtService jwtService;
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final BlockListTokenService blockListTokenService;
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsServiceImpl customUserDetailsService, BlockListTokenService blockListTokenService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.blockListTokenService = blockListTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = parseJwt(request);
        try {
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (blockListTokenService.isTokenInBlockList(token)){
                filterChain.doFilter(request, response);
                return;
            }
                String tenant = jwtService.getTenant(token);
                if (tenant != null) {
                    CurrentTenant.setTenant(tenant);
                    String username = jwtService.getSubject(token);
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
        }
            filterChain.doFilter(request, response);
        }catch (Exception e) {
            log.warn(e.getMessage());
        }
        finally {
            CurrentTenant.clear();
        }
    }

    public String parseJwt(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (Strings.hasText(authorization) && authorization.startsWith("Bearer ")){
            return authorization.substring(7);
        }
        return null;
    }
}
