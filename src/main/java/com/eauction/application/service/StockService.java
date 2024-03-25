package com.eauction.application.service;

import com.eauction.application.domain.common.GeneralResponse;
import com.eauction.application.domain.common.ResponseMessage;
import com.eauction.application.domain.common.StockStatus;
import com.eauction.application.domain.stock.StockQueryResponse;
import com.eauction.application.domain.stock.UpdateStockRequest;
import com.eauction.application.domain.stock.UploadStockRequest;
import com.eauction.application.model.AuctionStock;
import com.eauction.application.model.Stock;
import com.eauction.application.model.StockDetail;
import com.eauction.application.repository.AuctionStockRepository;
import com.eauction.application.repository.StockDetailRepository;
import com.eauction.application.repository.StockRepository;
import com.eauction.application.util.ImageUtils;
import com.eauction.application.util.mapper.AuctionStockMapper;
import com.eauction.application.util.mapper.StockMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StockService {
    final StockRepository stockRepository;
    final StockDetailRepository stockDetailRepository;
    final AuctionStockRepository auctionStockRepository;
    final AuthenticationService authenticationService;

    @Autowired
    public StockService(StockRepository stockRepository, StockDetailRepository stockDetailRepository,
                        AuctionStockRepository auctionStockRepository, AuthenticationService authenticationService) {
        this.stockRepository = stockRepository;
        this.stockDetailRepository = stockDetailRepository;
        this.auctionStockRepository = auctionStockRepository;
        this.authenticationService = authenticationService;
    }

    public Page<StockQueryResponse> getAllStockFilter(Specification<Stock> specification, Pageable pageable, String session) {
        if (authenticationService.checkerSessionInvalid(session)) {
            Page<StockQueryResponse> responseList = null;
            responseList.getContent().add(StockQueryResponse.builder()
                    .messageCode(ResponseMessage.SESSION_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.SESSION_INVALID.getMessageDetail()).build());
            return responseList;
        } else {
            Page<Stock> responseList = stockRepository.findAll(specification, pageable);
            return new PageImpl<StockQueryResponse>
                    (responseList.getContent().stream().map(StockMapper.INSTANCE::convertToStockQueryResponse).toList(),
                            pageable,
                            responseList.getTotalElements());
        }
    }

    public GeneralResponse uploadStock(List<MultipartFile> listImages, String request, String session) throws IOException {
        if (authenticationService.checkerSessionInvalid(session)) {
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.SESSION_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.SESSION_INVALID.getMessageDetail()).build();
        } else {
            Gson gson = new Gson();
            UploadStockRequest uploadStockRequest = gson.fromJson(request, UploadStockRequest.class);
            Stock stockSaved = StockMapper.INSTANCE.convertFromUploadStockRequest(uploadStockRequest);
            stockSaved.setStatus(StockStatus.DRAFT.name());
            save(stockSaved);
            saveStockDetail(listImages);
            auctionStockRepository.save(AuctionStockMapper.INSTANCE.convertFromUploadStockRequest(uploadStockRequest, stockSaved));
            stockSaved.setStatus(StockStatus.UPLOADED.name());
            save(stockSaved);
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.SUCCESS_CREATE_STOCK.getMessageCode())
                    .messageDetail(ResponseMessage.SUCCESS_CREATE_STOCK.getMessageDetail()).build();
        }
    }

    public GeneralResponse updateStock(UpdateStockRequest request, String session) throws IOException {
        if (authenticationService.checkerSessionInvalid(session)) {
            return GeneralResponse.builder()
                    .messageCode(ResponseMessage.SESSION_INVALID.getMessageCode())
                    .messageDetail(ResponseMessage.SESSION_INVALID.getMessageDetail()).build();
        } else {
            Stock dataStock = stockRepository.findById(request.getStockId()).orElse(null);
            if (dataStock == null) {
                return GeneralResponse.builder()
                        .messageCode(ResponseMessage.STOCK_INVALID.getMessageCode())
                        .messageDetail(ResponseMessage.STOCK_INVALID.getMessageDetail()).build();
            } else {
                if (dataStock.getStatus().equals(StockStatus.UPLOADED.name())) {
                    save(StockMapper.INSTANCE.updateStock(dataStock, request));
                    AuctionStock dataAuctionStock = auctionStockRepository.findByStockId(request.getStockId());
                    auctionStockRepository.save(AuctionStockMapper.INSTANCE.updateAuctionStock(dataAuctionStock, request));
                    return GeneralResponse.builder()
                            .messageCode(ResponseMessage.SUCCESS_UPDATE_STOCK.getMessageCode())
                            .messageDetail(ResponseMessage.SUCCESS_UPDATE_STOCK.getMessageDetail()).build();
                } else {
                    return GeneralResponse.builder()
                            .messageCode(ResponseMessage.STATUS_STOCK_INVALID.getMessageCode())
                            .messageDetail(ResponseMessage.STATUS_STOCK_INVALID.getMessageDetail()).build();
                }

            }
        }
    }

    private void saveStockDetail(List<MultipartFile> imageFile) throws IOException {
        for (int i = 0; i <= imageFile.size(); i++) {
            stockDetailRepository.save(StockDetail.builder()
                    .imageStock(ImageUtils.compressImage(imageFile.get(i).getBytes()))
                    .descriptionImage("Image Stock ke-" + i)
                    .build());
        }
    }

    private Stock save(Stock stock) {
        if (stock.getStockId() == null) {
            stock.setCreatedDate(new Date());
        } else {
            stock.setUpdatedDate(new Date());
        }
        return stockRepository.save(stock);
    }
}
