package com.minibank.vo;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.models.constants.Country;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserVO {

    private Long id;
    @Schema(example = "1", description = "")
    private String firstName;
    @Schema(example = "User", description = "")
    private String lastName;
    @Schema(example = "Userovicz", description = "")
    private Country country;
    @Schema(example = "42343", description = "")
    private Long phoneNumber;
    @Schema(example = "2023-05-27T20:13:27.989Z", description = "")
    private OffsetDateTime dateTime;
    @Schema(example = "mail@mail.com", description = "")
    private String email;
    @Schema(example = "password", description = "")
    private String password;
    @Schema(example = "CLIENT", description = "")
    private UserRole role;
    @Schema(example = "ACTIVE", description = "")
    private Status status;


    public UserVO(Long id, String firstName, String lastName, Country country, Long phoneNumber, OffsetDateTime dateTime, String email, String password, UserRole role, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public UserVO() {
    }

    public static UserVO valueOf(User user) {
        UserVO userVO = new UserVO(user.getId(), user.getFirstName(), user.getLastName(), user.getCountry(),
                user.getPhoneNumber(), user.getDateTime(), user.getEmail(), user.getPassword(), user.getRole(),
                user.getStatus());

//        userVO.setAccounts(user.getAccounts());
        return userVO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}