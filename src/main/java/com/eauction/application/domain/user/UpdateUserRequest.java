package com.eauction.application.domain.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    @NonNull
    private String username;
    private String name;
    private String phoneNumber;
    private String address;
}
