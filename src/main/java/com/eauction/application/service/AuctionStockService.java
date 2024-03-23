package com.eauction.application.service;

import com.eauction.application.domain.auctionStock.AuctionStockQueryResponse;
import com.eauction.application.model.AuctionStock;
import com.eauction.application.repository.AuctionStockRepository;
import com.eauction.application.util.mapper.AuctionStockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuctionStockService {
    final AuctionStockRepository auctionStockRepository;

    @Autowired
    public AuctionStockService(AuctionStockRepository auctionStockRepository) {
        this.auctionStockRepository = auctionStockRepository;
    }

    public Page<AuctionStockQueryResponse> getAllAuctionStockFilter(Specification<AuctionStock> specification, Pageable pageable) {
        Page<AuctionStock> responseList = auctionStockRepository.findAll(specification, pageable);
        return new PageImpl<AuctionStockQueryResponse>
                (responseList.getContent().stream().map(AuctionStockMapper.INSTANCE::convertToAuctionStockQueryResponse).toList(),
                        pageable,
                        responseList.getTotalElements());
    }
}
