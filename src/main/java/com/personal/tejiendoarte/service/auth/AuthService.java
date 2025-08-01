package com.personal.tejiendoarte.service.auth;

import com.personal.tejiendoarte.dto.SignupRequestDto;
import com.personal.tejiendoarte.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequestDto signupRequestDto);

}
