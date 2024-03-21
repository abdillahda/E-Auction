package com.eauction.application.service;

import com.eauction.application.domain.UserQueryResponse;
import com.eauction.application.model.User;
import com.eauction.application.repository.UserRepository;
import com.eauction.application.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserQueryResponse> getAllUserFilter(Specification<User> specification, Pageable pageable) {
        Page<User> responseList = userRepository.findAll(specification, pageable);
        Page<UserQueryResponse> responsePage = new PageImpl<UserQueryResponse>
                (responseList.getContent().stream().map(UserMapper.INSTANCE::convertToUserQueryResponse).toList(),
                        pageable,
                        responseList.getTotalElements());
        return responsePage;
    }
}
