package com.eauction.application.util.mapper;

import com.eauction.application.domain.user.RegisterUserRequest;
import com.eauction.application.domain.user.UpdateUserRequest;
import com.eauction.application.domain.user.UserQueryResponse;
import com.eauction.application.model.Role;
import com.eauction.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract UserQueryResponse convertToUserQueryResponse(User user);

    public static User registUser(RegisterUserRequest request, Role role) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setPassword(request.getPassword());
        user.setRole(role);
        user.setName(request.getName());
        if (user.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (user.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        return user;
    }

    public static User updateUser(User dataUser, UpdateUserRequest request) {
        if (request.getName() != null) {
            dataUser.setName(request.getName());
        }
        if (request.getPhoneNumber() != null) {
            dataUser.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getAddress() != null) {
            dataUser.setAddress(request.getAddress());
        }
        return dataUser;
    }
}
