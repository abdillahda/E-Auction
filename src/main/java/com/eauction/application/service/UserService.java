package com.eauction.application.service;

import com.eauction.application.domain.common.GeneralResponse;
import com.eauction.application.domain.common.ResponseMessage;
import com.eauction.application.domain.user.RegisterUserRequest;
import com.eauction.application.domain.user.UpdateUserRequest;
import com.eauction.application.domain.user.UserQueryResponse;
import com.eauction.application.model.User;
import com.eauction.application.repository.RoleRepository;
import com.eauction.application.repository.UserRepository;
import com.eauction.application.util.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final AuthenticationService authenticationService;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationService = authenticationService;
    }

    public Page<UserQueryResponse> getAllUserFilter(Specification<User> specification, Pageable pageable, String session) {
        if (authenticationService.checkerSessionInvalid(session)) {
            Page<UserQueryResponse> responseList = null;
            responseList.getContent().add(UserQueryResponse.builder()
                    .messageCode(ResponseMessage.SESSION_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.SESSION_INVALID.getMessageDetail()).build());
            return responseList;
        } else {
            Page<User> responseList = userRepository.findAll(specification, pageable);
            return new PageImpl<UserQueryResponse>
                    (responseList.getContent().stream().map(UserMapper.INSTANCE::convertToUserQueryResponse).toList(),
                            pageable,
                            responseList.getTotalElements());
        }
    }

    public GeneralResponse register(RegisterUserRequest registerUserRequest) {
        User dataUser = userRepository.findByUsername(registerUserRequest.getUsername());
        if (dataUser != null) {
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.USERNAME_DUPLICATE.getMessageCode())
                    .messageDetail(ResponseMessage.USERNAME_DUPLICATE.getMessageDetail()).build();
        } else {
            userRepository.save(UserMapper.registUser(registerUserRequest));
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.SUCCESS_REGISTER.getMessageCode())
                    .messageDetail(ResponseMessage.SUCCESS_REGISTER.getMessageDetail()).build();
        }
    }

    public GeneralResponse updateUser(UpdateUserRequest registerUserRequest, String session) {
        if (authenticationService.checkerSessionInvalid(session)) {
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.SESSION_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.SESSION_INVALID.getMessageDetail()).build();
        } else {
            User dataUser = userRepository.findByUsername(registerUserRequest.getUsername());
            if (dataUser == null) {
                return GeneralResponse.builder()
                        .messageCode(ResponseMessage.USER_INVALID.getMessageCode())
                        .messageDetail(ResponseMessage.USER_INVALID.getMessageDetail()).build();
            } else {
                userRepository.save(UserMapper.updateUser(dataUser, registerUserRequest));
                return GeneralResponse.builder()
                        .messageCode(ResponseMessage.SUCCESS_UPDATE_USER.getMessageCode())
                        .messageDetail(ResponseMessage.SUCCESS_UPDATE_USER.getMessageDetail()).build();
            }
        }
    }

    public GeneralResponse deleteUser(String username, String session) {
        if (authenticationService.checkerSessionInvalid(session)) {
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.SESSION_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.SESSION_INVALID.getMessageDetail()).build();
        } else {
            User dataUser = userRepository.findByUsername(username);
            if (dataUser == null) {
                return GeneralResponse.builder()
                        .messageCode(ResponseMessage.USER_INVALID.getMessageCode())
                        .messageDetail(ResponseMessage.USER_INVALID.getMessageDetail()).build();
            } else {
                userRepository.delete(dataUser);
                return GeneralResponse.builder()
                        .messageCode(ResponseMessage.SUCCESS_DELETE_USER.getMessageCode())
                        .messageDetail(ResponseMessage.SUCCESS_DELETE_USER.getMessageDetail()).build();

            }
        }
    }
}
