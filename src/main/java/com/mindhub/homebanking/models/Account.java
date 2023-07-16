package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    private boolean isActive;
    private AccountType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Client owner;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    public Account() {
    }
    public Account(String number, AccountType type, LocalDate creationDate, Double balance, boolean isActive) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.isActive = isActive;
        this.type = type;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public Long getId() {
        return id;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
    @JsonIgnore
    public Client getOwner() {
        return owner;
    }
    public void setOwner(Client owner) {
        this.owner = owner;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
    public void addTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            this.addTransaction(transaction);
        }
    }
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }
}