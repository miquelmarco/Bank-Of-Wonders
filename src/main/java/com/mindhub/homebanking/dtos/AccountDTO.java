package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Account;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
public class AccountDTO {
    private long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    private List<TransactionDTO> transactions;
    public AccountDTO(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
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
    public List<TransactionDTO> getTransactions() {
        return transactions;
    }
    public long getId() {
        return id;
    }
}
