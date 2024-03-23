package com.eauction.application.util.mapper;

import com.eauction.application.domain.stock.StockQueryResponse;
import com.eauction.application.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class StockMapper {
    public static final StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    public abstract StockQueryResponse convertToStockQueryResponse(Stock stock);
}
