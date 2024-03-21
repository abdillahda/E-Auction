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
@Table(name = "stock_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDetail {
    @Id
    private String id = UUID.randomUUID().toString();
    private String stockId;
    private String description;
    private String warranty;
    private Integer amountStock;
    private String weightStock;
    private String imageStock;
}
