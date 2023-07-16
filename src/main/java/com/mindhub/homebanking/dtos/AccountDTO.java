package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    private boolean isActive;
    private AccountType type;
    private List<TransactionDTO> transactions;
    public AccountDTO() {
    }
    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.type = account.getType();
        this.isActive = account.isActive();
        this.transactions = account.getTransactions().stream().distinct().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }
    public String getNumber() {
        return number;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public Double getBalance() {
        return balance;
    }
    public boolean isActive() {
        return isActive;
    }
    public List<TransactionDTO> getTransactions() {
        return transactions;
    }
    public Long getId() {
        return id;
    }
    public AccountType getType() {
        return type;
    }
}