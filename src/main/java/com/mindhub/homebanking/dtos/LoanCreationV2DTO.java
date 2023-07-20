package com.mindhub.homebanking.dtos;
import java.util.List;
public class LoanCreationV2DTO {
    private String name;
    private List<Integer> payments;
    private Double maxAmount;
    private Integer percentage;
    public LoanCreationV2DTO() {
    }
    public LoanCreationV2DTO(String name, List<Integer> payments, Double maxAmount, Integer percentage) {
        this.name = name;
        this.payments = payments;
        this.maxAmount = maxAmount;
        this.percentage = percentage;
    }
    public String getName() {
        return name;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public Double getMaxAmount() {
        return maxAmount;
    }
    public Integer getPercentage() {
        return percentage;
    }
}