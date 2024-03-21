package com.eauction.application.repository;

import com.eauction.application.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String>, JpaSpecificationExecutor<Admin> {
}
