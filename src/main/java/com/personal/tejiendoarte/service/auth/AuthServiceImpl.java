package com.personal.tejiendoarte.service.auth;

import com.personal.tejiendoarte.dto.SignupRequestDto;
import com.personal.tejiendoarte.dto.UserDto;
import com.personal.tejiendoarte.entity.User;
import com.personal.tejiendoarte.enums.UserRole;
import com.personal.tejiendoarte.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDto createUser(SignupRequestDto signupRequestDto) {

        User user = new User();

        user.setEmail(signupRequestDto.getEmail());
        user.setFirst_name(signupRequestDto.getFirst_name());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        User createdUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User newAdmin = new User();
            newAdmin.setEmail("admin@admin.com");
            newAdmin.setFirst_name("admin");
            newAdmin.setRole(UserRole.ADMIN);
            newAdmin.setPassword(bCryptPasswordEncoder.encode("admin"));

            userRepository.save(newAdmin);
        }
    }

}
