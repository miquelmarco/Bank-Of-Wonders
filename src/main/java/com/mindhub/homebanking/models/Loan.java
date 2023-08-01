package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private Double maxAmount;
    private Integer percentage;
    @ElementCollection
    private List<Integer> payments;
    @OneToMany (mappedBy = "loan", fetch = FetchType.EAGER)
    private List<ClientLoan> clientLoans = new ArrayList<>();
    public Loan() {
    }
    public Loan(String name, Double maxAmount, Integer percentage, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.percentage = percentage;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }
    public Integer getPercentage() {
        return percentage;
    }
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
    public List<Integer> getPayments() {
        return payments;
    }
    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
    public List<ClientLoan> getClientLoans() {
        return clientLoans;
    }
    public void setClientLoans(List<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }
    @JsonIgnore
    public Set<Client> getClients() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toSet());
    }
    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        this.clientLoans.add(clientLoan);
    }
}