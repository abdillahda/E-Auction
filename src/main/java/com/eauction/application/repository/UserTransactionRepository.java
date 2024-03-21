package com.eauction.application.repository;

import com.eauction.application.model.User;
import com.eauction.application.model.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction,String>, JpaSpecificationExecutor<UserTransaction> {
}
