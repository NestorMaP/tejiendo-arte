package com.personal.tejiendoarte.controller;

import com.personal.tejiendoarte.dto.AuthenticationRequestDto;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.repository.UserRepository;
import com.personal.tejiendoarte.service.UserDetailsServiceImpl;
import com.personal.tejiendoarte.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequest,
                                          HttpServletResponse response) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Invalid username and password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );
        }

    }

}
