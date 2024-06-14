package com.example.investment.controller;

import com.example.investment.dto.UserRegistrationDto;
import com.example.investment.model.User;
import com.example.investment.model.UserStock;
import com.example.investment.service.UserService;
import com.example.investment.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-stocks")
public class UserStockController {

    @Autowired
    private UserStockService userStockService;

    @PostMapping("/buy")
    public ResponseEntity<UserStock> buyStock(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String stockSymbol = request.get("stockSymbol");
        Integer quantity = Integer.parseInt(request.get("quantity"));
        UserStock userStock = userStockService.buyStock(username, stockSymbol, quantity);
        return ResponseEntity.ok(userStock);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<UserStock>> getUserStocks(@PathVariable String username) {
        List<UserStock> userStocks = userStockService.getUserStocks(username);
        return ResponseEntity.ok(userStocks);
    }
}
