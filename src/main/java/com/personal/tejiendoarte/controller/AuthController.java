package com.personal.tejiendoarte.controller;

import com.personal.tejiendoarte.dto.AuthRequestDto;
import com.personal.tejiendoarte.dto.SignupRequestDto;
import com.personal.tejiendoarte.dto.UserDto;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.repository.UserRepository;
import com.personal.tejiendoarte.service.UserDetailsServiceImpl;
import com.personal.tejiendoarte.security.JwtUtil;
import com.personal.tejiendoarte.service.auth.AuthServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthRequestDto authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {

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

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequestDto signupRequestDto) {

        if(authServiceImpl.hasUserWithEmail(signupRequestDto.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.UNAUTHORIZED);
        }

        UserDto userDto = authServiceImpl.createUser(signupRequestDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

}
