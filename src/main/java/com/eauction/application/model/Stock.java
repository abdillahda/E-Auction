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
@Table(name = "stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    private String stockId = UUID.randomUUID().toString();
    private String sellerId;
    private String stockName;
    private BigInteger startPrice;
    private String description;
    private String warranty;
    private Integer amountStock;
    private String weightStock;
    private String category;
    private String shippingAddress;
    private String stockCondition;
    private String status;
    private Date createdDate;
    private Date updatedDate;
}
