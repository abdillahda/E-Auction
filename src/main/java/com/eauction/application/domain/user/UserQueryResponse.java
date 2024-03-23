package com.eauction.application.domain.user;

import com.eauction.application.domain.common.GeneralResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserQueryResponse extends GeneralResponse {
    private String username;
    private String name;
    private String phoneNumber;
    private String address;
}
