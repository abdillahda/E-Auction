package com.eauction.application.service;

import com.eauction.application.domain.auctionStock.AuctionStockQueryResponse;
import com.eauction.application.domain.common.AuctionStatus;
import com.eauction.application.domain.common.StockStatus;
import com.eauction.application.model.AuctionStock;
import com.eauction.application.model.Stock;
import com.eauction.application.repository.AuctionStockRepository;
import com.eauction.application.repository.StockRepository;
import com.eauction.application.util.mapper.AuctionStockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AuctionStockService {
    final AuctionStockRepository auctionStockRepository;
    final StockRepository stockRepository;

    @Autowired
    public AuctionStockService(AuctionStockRepository auctionStockRepository, StockRepository stockRepository) {
        this.auctionStockRepository = auctionStockRepository;
        this.stockRepository = stockRepository;
    }

    public Page<AuctionStockQueryResponse> getAllAuctionStockFilter(Specification<AuctionStock> specification, Pageable pageable) {
        Page<AuctionStock> responseList = auctionStockRepository.findAll(specification, pageable);
        return new PageImpl<AuctionStockQueryResponse>
                (responseList.getContent().stream().map(AuctionStockMapper.INSTANCE::convertToAuctionStockQueryResponse).toList(),
                        pageable,
                        responseList.getTotalElements());
    }

    public void updateLiveAuction() {
        log.info("[AuctionStockService.updateLiveAuction][Start]" + new Date());
        List<AuctionStock> liveAuction = auctionStockRepository.findAllLiveAuction();
        for (AuctionStock auction : liveAuction) {
            auction.setStatus(AuctionStatus.LIVE.name());
            auctionStockRepository.save(auction);
        }
        log.info("[AuctionStockService.updateLiveAuction][Finish]" + new Date());
    }

    public void updateEndsAuction() {
        log.info("[AuctionStockService.updateEndsAuction][Start]" + new Date());
        List<AuctionStock> endedAuction = auctionStockRepository.findAllEndAuction();
        for (AuctionStock auction : endedAuction) {
            auction.setStatus(AuctionStatus.DONE.name());
            auctionStockRepository.save(auction);
            Stock dataStock = stockRepository.findById(auction.getStockId()).orElse(null);
            dataStock.setStatus(StockStatus.SOLD.name());
            stockRepository.save(dataStock);
        }
        log.info("[AuctionStockService.updateEndsAuction][Finish]" + new Date());
    }
}
