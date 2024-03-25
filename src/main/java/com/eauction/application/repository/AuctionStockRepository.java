package com.eauction.application.repository;

import com.eauction.application.model.AuctionStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionStockRepository extends JpaRepository<AuctionStock,String>, JpaSpecificationExecutor<AuctionStock> {
    AuctionStock findByStockId(String stockId);

    @Query("SELECT a FROM AuctionStock a INNER JOIN Stock s ON a.stockId=s.stockId" +
            " WHERE a.status='PUBLISH' AND s.status='APPROVE' AND a.auctionStart <= CURRENT_DATE ")
    List<AuctionStock> findAllLiveAuction();

    @Query("SELECT a FROM AuctionStock a INNER JOIN Stock s ON a.stockId=s.stockId" +
            " WHERE a.status='LIVE' AND s.status='APPROVE' AND a.auctionEnd <= CURRENT_DATE ")
    List<AuctionStock> findAllEndAuction();
}
