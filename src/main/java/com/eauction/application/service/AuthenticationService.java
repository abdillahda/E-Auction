package com.eauction.application.service;

import com.eauction.application.domain.common.GeneralResponse;
import com.eauction.application.domain.common.ResponseMessage;
import com.eauction.application.domain.common.login.LoginRequest;
import com.eauction.application.domain.common.login.LoginResponse;
import com.eauction.application.domain.common.logout.LogoutRequest;
import com.eauction.application.model.User;
import com.eauction.application.repository.RoleRepository;
import com.eauction.application.repository.UserRepository;
import com.eauction.application.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticationService {
    @Qualifier("bCryptPasswordEncoder")
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User dataUser = userRepository.findByUsername(loginRequest.getUsername());
        if (dataUser == null) {
            return LoginResponse.builder()
                    .messageCode(ResponseMessage.USER_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.USER_INVALID.getMessageDetail()).build();
        } else {
            if (!dataUser.getEnabled()) {
                return LoginResponse.builder()
                        .messageCode(ResponseMessage.USER_NOT_VERIFIED.getMessageCode())
                        .messageDetail(ResponseMessage.USER_NOT_VERIFIED.getMessageDetail()).build();
            } else {
                if (bCryptPasswordEncoder.matches(Utils.decryptAES(dataUser.getPassword()), loginRequest.getPassword())) {
                    LocalDateTime today = LocalDateTime.now();
                    dataUser.setSession(UUID.randomUUID().toString());
                    dataUser.setSessionValid(Date.from(today.atZone(ZoneId.systemDefault()).toInstant()));
                    userRepository.save(dataUser);
                    return LoginResponse.builder()
                            .username(dataUser.getUsername())
                            .session(dataUser.getSession())
                            .name(dataUser.getName())
                            .role(roleRepository.findById(dataUser.getRoleId()).orElse(null))
                            .messageCode(ResponseMessage.SUCCESS_LOGIN.getMessageCode())
                            .messageDetail(ResponseMessage.SUCCESS_LOGIN.getMessageDetail()).build();
                } else {
                    return LoginResponse.builder()
                            .messageCode(ResponseMessage.WRONG_PASSWORD.getMessageCode())
                            .messageDetail(ResponseMessage.WRONG_PASSWORD.getMessageDetail()).build();
                }
            }
        }
    }

    public GeneralResponse logout(LogoutRequest logoutRequest) {
        User dataUser = userRepository.findByUsername(logoutRequest.getUsername());
        if (dataUser == null) {
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.USER_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.USER_INVALID.getMessageDetail()).build();
        } else {
            if (!dataUser.getEnabled()) {
                return GeneralResponse.builder()
                        .messageCode(ResponseMessage.USER_NOT_VERIFIED.getMessageCode())
                        .messageDetail(ResponseMessage.USER_NOT_VERIFIED.getMessageDetail()).build();
            } else {
                dataUser.setSession("-");
                dataUser.setSessionValid(new Date());
                userRepository.save(dataUser);
                return GeneralResponse.builder()
                        .messageCode(ResponseMessage.SUCCESS_LOGIN.getMessageCode())
                        .messageDetail(ResponseMessage.SUCCESS_LOGIN.getMessageDetail()).build();

            }
        }
    }

    //NOTE : TRUE MEAN INVALID
    public Boolean checkerSessionInvalid(String session) {
        User dataUser = userRepository.findBySession(session);
        if (dataUser == null) {
            return true;
        } else {
            if (dataUser.getSessionValid().after(new Date())) {
                dataUser.setSession("-");
                userRepository.save(dataUser);
                return true;
            } else {
                return false;
            }
        }
    }
}
