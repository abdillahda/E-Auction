package com.eauction.application.util.mapper;

import com.eauction.application.domain.auctionStock.AuctionStockQueryResponse;
import com.eauction.application.domain.common.AuctionStatus;
import com.eauction.application.domain.stock.UpdateStockRequest;
import com.eauction.application.domain.stock.UploadStockRequest;
import com.eauction.application.model.AuctionStock;
import com.eauction.application.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AuctionStockMapper {
    public static final AuctionStockMapper INSTANCE = Mappers.getMapper(AuctionStockMapper.class);

    public abstract AuctionStockQueryResponse convertToAuctionStockQueryResponse(AuctionStock auctionStock);

    public static AuctionStock convertFromUploadStockRequest(UploadStockRequest request, Stock stock) {
        AuctionStock auctionStock = new AuctionStock();
        auctionStock.setStockId(stock.getStockId());
        auctionStock.setAuctionName(request.getAuctionName() == null ? stock.getStockName() : request.getAuctionName());
        auctionStock.setAuctionStart(request.getAuctionStart());
        auctionStock.setAuctionEnd(request.getAuctionEnd());
        auctionStock.setBidMultiple(request.getBidMultiple());
        auctionStock.setStatus(AuctionStatus.DRAFT.name());
        return auctionStock;
    }

    public static AuctionStock updateAuctionStock(AuctionStock auctionStock, UpdateStockRequest request) {
        if (request.getAuctionName() != null) {
            auctionStock.setAuctionName(request.getAuctionName());
        }
        if (request.getAuctionStart() != null) {
            auctionStock.setAuctionStart(request.getAuctionStart());
        }
        if (request.getAuctionEnd() != null) {
            auctionStock.setAuctionEnd(request.getAuctionEnd());
        }
        if (request.getBidMultiple() != null) {
            auctionStock.setBidMultiple(request.getBidMultiple());
        }
        return auctionStock;
    }
}
