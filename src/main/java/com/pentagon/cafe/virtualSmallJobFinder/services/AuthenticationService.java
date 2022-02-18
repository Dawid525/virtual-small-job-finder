package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.exceptions.TokenRefreshException;
import com.pentagon.cafe.virtualSmallJobFinder.payload.JwtResponse;
import com.pentagon.cafe.virtualSmallJobFinder.payload.TokenRefreshRequest;
import com.pentagon.cafe.virtualSmallJobFinder.payload.TokenRefreshResponse;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.RefreshTokenRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.RefreshToken;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.utils.JwtUtils;
import com.pentagon.cafe.virtualSmallJobFinder.payload.LoginRequest;
import com.pentagon.cafe.virtualSmallJobFinder.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final  AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    public JwtResponse authenticateLoginRequest(LoginRequest loginRequest){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtUtils.generateJwtToken(authentication);
        Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return new JwtResponse(token, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }


    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        refreshTokenService.verifyExpiration(refreshToken);
        UserEntity user = refreshToken.getUser();
        String token = jwtUtils.generateJwtTokenByUsername(user.getUsername());
        return new TokenRefreshResponse(token, refreshToken.getToken());
    }

}
