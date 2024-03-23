package com.eauction.application.domain.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String roleId;
    @NonNull
    private String name;
    private String phoneNumber;
    private String address;
}
