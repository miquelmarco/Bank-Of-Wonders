package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountDTO> accounts;


    public ClientDTO(Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().distinct().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public List<AccountDTO> getAccounts() {
        return accounts;
    }
    public long getId() {
        return id;
    }
}