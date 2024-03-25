package com.eauction.application.util.mapper;

import com.eauction.application.domain.stock.StockQueryResponse;
import com.eauction.application.domain.stock.UpdateStockRequest;
import com.eauction.application.domain.stock.UploadStockRequest;
import com.eauction.application.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StockMapper {
    public static final StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    public abstract StockQueryResponse convertToStockQueryResponse(Stock stock);

    public abstract Stock convertFromUploadStockRequest(UploadStockRequest request);

    public abstract Stock convertFromUpdateStockRequest(UpdateStockRequest request);

    public static Stock updateStock(Stock dataStock, UpdateStockRequest request) {
        if (request.getStockName() != null) {
            dataStock.setStockName(request.getStockName());
        }
        if (request.getStartPrice() != null) {
            dataStock.setStartPrice(request.getStartPrice());
        }
        if (request.getDescription() != null) {
            dataStock.setDescription(request.getDescription());
        }
        if (request.getWarranty() != null) {
            dataStock.setWarranty(request.getWarranty());
        }
        if (request.getAmountStock() != null) {
            dataStock.setAmountStock(request.getAmountStock());
        }
        if (request.getWeightStock() != null) {
            dataStock.setWeightStock(request.getWeightStock());
        }
        if (request.getCategory() != null) {
            dataStock.setCategory(request.getCategory());
        }
        if (request.getShippingAddress() != null) {
            dataStock.setShippingAddress(request.getShippingAddress());
        }
        return dataStock;
    }
}
