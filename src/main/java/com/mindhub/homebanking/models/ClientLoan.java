package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private Double amount;
    private Integer payments;
    private Integer remainPayments;
    private Double remainAmount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;
    public ClientLoan() {
    }
    public ClientLoan(Double amount, Integer payments, Double remainAmount, Integer remainPayments) {
        this.amount = amount;
        this.payments = payments;
        this.remainAmount = remainAmount;
        this.remainPayments = remainPayments;
    }
    public Long getId() {
        return id;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Integer getPayments() {
        return payments;
    }
    public void setPayments(Integer payments) {
        this.payments = payments;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    public Integer getRemainPayments() {
        return remainPayments;
    }
    public void setRemainPayments(Integer remainPayments) {
        this.remainPayments = remainPayments;
    }
    public Double getRemainAmount() {
        return remainAmount;
    }
    public void setRemainAmount(Double remainAmount) {
        this.remainAmount = remainAmount;
    }
}