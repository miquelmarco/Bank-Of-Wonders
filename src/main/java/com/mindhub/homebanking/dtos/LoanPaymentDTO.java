package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanPaymentDTO {
    private String name;
    private List<Integer> maxPayment;
    private Double maxAmount;
    private Integer percentage;
    public LoanPaymentDTO() {
    }
    public LoanPaymentDTO(String name, List<Integer> maxPayment, Double maxAmount, Integer percentage) {
        this.name = name;
        this.maxPayment = maxPayment;
        this.maxAmount = maxAmount;
        this.percentage = percentage;
    }
    public String getName() {
        return name;
    }
    public List<Integer> getMaxPayment() {
        return maxPayment;
    }
    public Double getMaxAmount() {
        return maxAmount;
    }
    public Integer getPercentage() {
        return percentage;
    }
}