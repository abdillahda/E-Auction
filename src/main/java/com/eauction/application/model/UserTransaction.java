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
@Table(name = "user_transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {
    @Id
    private String transactionid = UUID.randomUUID().toString();
    private String buyerId;
    private String sellerId;
    private String stockId;
    private BigInteger priceAmount;
    private BigInteger totalAmount;
    private String bankTransfer;
    private Integer uniqueCode;
    private Date transactionDate;
    private String shippingAddress;
    private String destinationAddress;
    private String expedition;
    private BigInteger expeditionFee;
    private String expeditionReceipt;
    private Integer expeditionEstimation;
}
