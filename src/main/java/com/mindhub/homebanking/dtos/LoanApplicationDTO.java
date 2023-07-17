package com.mindhub.homebanking.dtos;
public class LoanApplicationDTO {
    private Long loanTypeId;
    private Double amount;
    private Integer payments;
    private String destinationAcc;
    public LoanApplicationDTO() {
    }
    public LoanApplicationDTO(Long loanTypeId, Double amount, Integer payments, String destinationAcc) {
        this.loanTypeId = loanTypeId;
        this.amount = amount;
        this.payments = payments;
        this.destinationAcc = destinationAcc;
    }
    public Long getLoanTypeId() {
        return loanTypeId;
    }
    public Double getAmount() {
        return amount;
    }
    public Integer getPayments() {
        return payments;
    }
    public String getDestinationAcc() {
        return destinationAcc;
    }
}