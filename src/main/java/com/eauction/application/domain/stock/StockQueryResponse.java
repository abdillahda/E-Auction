package com.eauction.application.domain.stock;

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
public class StockQueryResponse extends GeneralResponse {
    private String stockId;
    private String sellerId;
    private String stockName;
    private BigInteger price;
    private String category;
    private String shippingAddress;
    private String stockCondition;
    private String status;
    private Date createdDate;
    private Date updatedDate;
}
