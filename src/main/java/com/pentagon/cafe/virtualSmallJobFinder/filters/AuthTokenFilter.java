package com.pentagon.cafe.virtualSmallJobFinder.filters;


import com.pentagon.cafe.virtualSmallJobFinder.services.UserDetailsServiceImpl;
import com.pentagon.cafe.virtualSmallJobFinder.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String prefixBearer = "Bearer ";
        String headerAuth = request.getHeader("Authorization");
        if( headerAuth != null && headerAuth.startsWith(prefixBearer)){
            String token = headerAuth.substring(prefixBearer.length());
            jwtUtils.validateJwtToken(token);
            String username = jwtUtils.getUsernameFromJwt(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
}
