package com.eauction.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private transient PasswordEncoder passwordEncoder;

    public User(@Qualifier("bCryptPasswordEncoder") PasswordEncoder encoder) {
        this.passwordEncoder = encoder;
    }

    @Id
    private String id = UUID.randomUUID().toString();
    private String roleId;
    private String username;
    private String password;
    private String session;
    private Boolean enabled;
    private String name;
    private String phoneNumber;
    private String address;
    private Date sessionValid;

    public void setPassword(String value) {
        this.password = passwordEncoder.encode(value);
    }
}
