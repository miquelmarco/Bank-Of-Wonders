package com.mindhub.homebanking.dtos;

public class LoanCreationDTO {
    private String name;
    private String maxPayment;
    private Double maxAmount;
    private Integer percentage;
    public LoanCreationDTO() {
    }
    public LoanCreationDTO(String name, String maxPayment, Double maxAmount, Integer percentage) {
        this.name = name;
        this.maxPayment = maxPayment;
        this.maxAmount = maxAmount;
        this.percentage = percentage;
    }
    public String getName() {
        return name;
    }
    public String getMaxPayment() {
        return maxPayment;
    }
    public Double getMaxAmount() {
        return maxAmount;
    }
    public Integer getPercentage() {
        return percentage;
    }
}