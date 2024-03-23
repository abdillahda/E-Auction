package com.eauction.application.domain.common.login;

import com.eauction.application.domain.common.GeneralResponse;
import com.eauction.application.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LoginResponse extends GeneralResponse {
    private String username;
    private String session;
    private String name;
    private Role role;
}
