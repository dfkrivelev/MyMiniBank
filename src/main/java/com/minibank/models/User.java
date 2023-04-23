package com.minibank.models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Country country;
    private int phoneNumber;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

}
