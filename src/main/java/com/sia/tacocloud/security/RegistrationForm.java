package com.sia.tacocloud.security;

import com.sia.tacocloud.entity.TacoUser;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public TacoUser toUser(PasswordEncoder passwordEncoder) {
        return new TacoUser(
                this.username,
                passwordEncoder.encode(this.password),
                this.fullName,
                this.street,
                this.city,
                this.state,
                this.zip,
                this.phone
        );
    }
}
