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
@Table(name = "auction_bid")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBid {
    @Id
    private String bidId = UUID.randomUUID().toString();
    private String stockId;
    private String bidderId;
    private BigInteger bidPrice;
    private Date bidTime;
}
