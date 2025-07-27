package com.personal.tejiendoarte.entity;

import com.personal.tejiendoarte.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String first_name;

    private String surname;

    private String second_surname;

    private Date birthdate;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
}
