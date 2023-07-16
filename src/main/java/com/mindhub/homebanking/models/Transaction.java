package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Double amount;
    private String description;
    private TransactionType type;
    private LocalDateTime date;
    private Double actualAmount;
    private boolean isActive;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
    //Constructores
    public Transaction() {
    }
    public Transaction(Double amount, String description, TransactionType type, LocalDateTime date, Double actualAmount, boolean isActive) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.date = date;
        this.actualAmount = actualAmount;
        this.isActive = isActive;
    }
    //accesores
    public long getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public Double getActualAmount() {
        return actualAmount;
    }
    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    //accesores relacionales
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", account=" + account +
                '}';
    }
}