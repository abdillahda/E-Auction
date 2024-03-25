package com.eauction.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "auction_stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionStock {
    @Id
    private String auctionId = UUID.randomUUID().toString();
    private String stockId;
    private String auctionName;
    private Date auctionStart;
    private Date auctionEnd;
    private String status;
    private BigInteger highestBid;
    private BigInteger bidMultiple;
}
