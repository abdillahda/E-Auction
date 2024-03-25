package com.eauction.application.domain.stock;

import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadStockRequest {
    @NonNull
    private String sellerId;

    @NonNull
    private String stockName;

    @NonNull
    private BigInteger startPrice;

    private String description;

    @NonNull
    private String warranty;

    @NonNull
    private Integer amountStock;

    @NonNull
    private String weightStock;

    private String category;

    @NonNull
    private String shippingAddress;

    private String auctionName;

    @NonNull
    private Date auctionStart;

    @NonNull
    private Date auctionEnd;

    @NonNull
    private BigInteger bidMultiple;
}
