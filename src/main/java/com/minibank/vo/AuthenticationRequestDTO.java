package com.minibank.vo;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthenticationRequestDTO {

    @Schema(example = "mail@mail.com", description = "")
    private String email;
    @Schema(example = "password", description = "")
    private String password;

    public AuthenticationRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationRequestDTO() {
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
}
