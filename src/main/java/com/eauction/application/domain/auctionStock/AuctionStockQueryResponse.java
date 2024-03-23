package com.eauction.application.domain.auctionStock;

import com.eauction.application.domain.common.GeneralResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuctionStockQueryResponse extends GeneralResponse {
    private String auctionId;
    private String stockId;
    private String auctionName;
    private Date auctionDate;
    private BigInteger highestBid;
    private BigInteger bidMultiple;

}
