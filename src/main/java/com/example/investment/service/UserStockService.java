package com.example.investment.service;

import com.example.investment.model.Stock;
import com.example.investment.model.User;
import com.example.investment.model.UserStock;
import com.example.investment.repository.StockRepository;
import com.example.investment.repository.UserRepository;
import com.example.investment.repository.UserStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStockService {

    @Autowired
    private UserStockRepository userStockRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    public UserStock buyStock(String username, String stockSymbol, Integer quantity) {
        User user = userRepository.findByUsername(username);
        Stock stock = stockRepository.findBySymbol(stockSymbol);
        if (user == null || stock == null) {
            throw new RuntimeException("User or stock not found");
        }
        UserStock userStock = new UserStock();
        userStock.setUser(user);
        userStock.setStock(stock);
        userStock.setQuantity(quantity);
        return userStockRepository.save(userStock);
    }

    public List<UserStock> getUserStocks(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return userStockRepository.findByUser(user);
    }
}
