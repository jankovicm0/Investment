package com.example.investment.service;

import com.example.investment.model.Stock;
import com.example.investment.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock findBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }
}
