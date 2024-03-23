package com.eauction.application.service;

import com.eauction.application.domain.stock.StockQueryResponse;
import com.eauction.application.model.Stock;
import com.eauction.application.repository.StockRepository;
import com.eauction.application.util.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockService {
    final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Page<StockQueryResponse> getAllStockFilter(Specification<Stock> specification, Pageable pageable) {
        Page<Stock> responseList = stockRepository.findAll(specification, pageable);
        return new PageImpl<StockQueryResponse>
                (responseList.getContent().stream().map(StockMapper.INSTANCE::convertToStockQueryResponse).toList(),
                        pageable,
                        responseList.getTotalElements());
    }
}
