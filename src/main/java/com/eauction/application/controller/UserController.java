package com.eauction.application.controller;

import com.eauction.application.domain.common.filtering.SearchQuery;
import com.eauction.application.domain.common.login.LoginRequest;
import com.eauction.application.domain.common.login.LoginResponse;
import com.eauction.application.domain.common.logout.LogoutRequest;
import com.eauction.application.domain.common.logout.LogoutResponse;
import com.eauction.application.domain.user.UserQueryResponse;
import com.eauction.application.model.User;
import com.eauction.application.service.AuthenticationService;
import com.eauction.application.service.UserService;
import com.eauction.application.util.search.GenericSpecificationsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService=userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        return null;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public LogoutResponse logout(@RequestBody LogoutRequest request) {
        return null;
    }

    @PostMapping("/query")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<UserQueryResponse>> getAllUserFilter(@RequestBody SearchQuery query) {
        log.info("[UserController.getAllUserFilter]Query :" + query);
        GenericSpecificationsBuilder<User> specBuilder = new GenericSpecificationsBuilder<>();
        specBuilder.with(query.getSearchCriteria());
        Pageable pageable = query.getPageable();

        Page<UserQueryResponse> results = userService.getAllUserFilter(specBuilder.build(),pageable);

        return ResponseEntity.ok()
                .header("Access-Control-Expose-Headers", "Content-Range")
                .header("Content-Range", "customer : " + query.getContentRange() + "/" + Objects.requireNonNull(results).getTotalElements())
                .body(results);
    }
}
