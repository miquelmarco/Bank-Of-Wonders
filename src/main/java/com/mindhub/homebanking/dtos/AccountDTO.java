package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    private Set<TransactionDTO> transactions;



    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
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
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
    public long getId() {
        return id;
    }
}
