package com.eauction.application.util.mapper;

import com.eauction.application.domain.auctionStock.AuctionStockQueryResponse;
import com.eauction.application.model.AuctionStock;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AuctionStockMapper {
    public static final AuctionStockMapper INSTANCE = Mappers.getMapper(AuctionStockMapper.class);

    public abstract AuctionStockQueryResponse convertToAuctionStockQueryResponse(AuctionStock auctionStock);
}
