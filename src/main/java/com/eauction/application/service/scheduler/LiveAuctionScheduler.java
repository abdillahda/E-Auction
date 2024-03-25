package com.eauction.application.service.scheduler;

import com.eauction.application.service.AuctionStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LiveAuctionScheduler {

    @Autowired
    private AuctionStockService auctionStockService;

    @Scheduled(cron = "*/5 * * * *")
    public void schedulerLiveAuction() {
        log.info("[LiveAuctionScheduler.schedulerLiveAuction][START]");
        auctionStockService.updateLiveAuction();
        log.info("[LiveAuctionScheduler.schedulerLiveAuction][FINISH]");
    }

    @Scheduled(cron = "*/5 * * * *")
    public void schedulerEndsAuction() {
        log.info("[LiveAuctionScheduler.schedulerEndsAuction][START]");
        auctionStockService.updateEndsAuction();
        log.info("[LiveAuctionScheduler.schedulerEndsAuction][FINISH]");
    }
}
