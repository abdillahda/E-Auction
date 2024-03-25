package com.eauction.application.repository;

import com.eauction.application.model.StockDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDetailRepository extends JpaRepository<StockDetail,String>, JpaSpecificationExecutor<StockDetail> {
    void deleteAllByStockId(String stockId);
}
