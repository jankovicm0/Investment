package com.example.investment.service;

import com.example.investment.model.Transaction;
import com.example.investment.model.User;
import com.example.investment.model.Stock;
import com.example.investment.repository.TransactionRepository;
import com.example.investment.repository.UserRepository;
import com.example.investment.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        User user = userRepository.findByUsername(transaction.getUser().getUsername());
        Stock stock = stockRepository.findBySymbol(transaction.getStock().getSymbol());

        if (user != null && stock != null) {
            double totalCost = transaction.getPrice() * transaction.getQuantity();
            if (user.getBalance() >= totalCost) {
                user.setBalance(user.getBalance() - totalCost);
                userRepository.save(user);

                transaction.setUser(user);
                transaction.setStock(stock);
                transaction.setDate(new Date());
                transaction.setType("buy"); // Set the type of transaction

                return transactionRepository.save(transaction);
            } else {
                throw new RuntimeException("Nedovoljno sredstava");
            }
        } else {
            throw new RuntimeException("Korisnik ili stock nisu pronadjeni");
        }
    }
}
