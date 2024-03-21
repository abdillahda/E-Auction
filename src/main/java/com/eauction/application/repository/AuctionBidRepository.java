package com.eauction.application.repository;

import com.eauction.application.model.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionBidRepository extends JpaRepository<AuctionBid,String>, JpaSpecificationExecutor<AuctionBid> {
}
