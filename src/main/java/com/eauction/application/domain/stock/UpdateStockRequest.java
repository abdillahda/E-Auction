package com.eauction.application.domain.stock;

import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStockRequest {
    @NonNull
    private String stockId;
    private String stockName;
    private BigInteger startPrice;
    private String description;
    private String warranty;
    private Integer amountStock;
    private String weightStock;
    private String category;
    private String shippingAddress;
    private String auctionName;
    private Date auctionStart;
    private Date auctionEnd;
    private BigInteger bidMultiple;
}
