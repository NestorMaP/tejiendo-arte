package com.personal.tejiendoarte.dto;

import com.personal.tejiendoarte.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String first_name;
    private UserRole userRole;

}
