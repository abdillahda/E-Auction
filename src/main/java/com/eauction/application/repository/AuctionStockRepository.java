package com.eauction.application.repository;

import com.eauction.application.model.AuctionStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionStockRepository extends JpaRepository<AuctionStock,String>, JpaSpecificationExecutor<AuctionStock> {
    AuctionStock findByStockId(String stockId);
}
