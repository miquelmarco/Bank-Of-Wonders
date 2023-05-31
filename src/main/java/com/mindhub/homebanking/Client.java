package com.mindhub.homebanking;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public Client(){ }

    public Client(String first, String last, String mail){
        firstName = first;
        lastName = last;
        email = mail;
    }
    public String getFirstName(){
        return firstName;
    }
    public String setFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String setLastName(){
        return lastName;
    }
    public String getEmail(){
        return email;
    }
    public String setEmail(){
        return email;
    }
    public String toString(){
        return firstName + " " + lastName;
    }

}