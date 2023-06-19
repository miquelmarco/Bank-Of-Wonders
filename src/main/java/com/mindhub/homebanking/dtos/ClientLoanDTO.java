package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.ClientLoan;
public class ClientLoanDTO {
    private long id;
    private long loanId;
    private String name;
    private Double amount;
    private Integer payments;
    // constructor
    public ClientLoanDTO(ClientLoan clientLoan){
        this.id=clientLoan.getId();
        this.loanId=clientLoan.getLoan().getId();
        this.name=clientLoan.getLoan().getName();
        this.amount=clientLoan.getAmount();
        this.payments= clientLoan.getPayments();
    }
    // accesores
    public long getId() {
        return id;
    }
    public long getLoanId() {
        return loanId;
    }
    public String getName() {
        return name;
    }
    public Double getAmount() {
        return amount;
    }
    public Integer getPayments() {
        return payments;
    }
}