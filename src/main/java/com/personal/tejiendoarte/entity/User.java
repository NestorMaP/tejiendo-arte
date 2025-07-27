package com.personal.tejiendoarte.entity;

import com.personal.tejiendoarte.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String first_name;

    @NotNull
    private String surname;

    private String second_surname;

    @NotNull
    private Date birthdate;

    @NotNull
    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
}
