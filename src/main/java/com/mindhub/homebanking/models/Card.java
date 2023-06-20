package com.mindhub.homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cardHolder;
    private cardType type;
    private cardColor color;
    private String number;
    private short cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Client cardOwner;
    //contructor
    public Card() {
    }
    public Card(String cardHolder, cardType type, cardColor color, String number, short cvv, LocalDate fromDate, LocalDate thruDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }
    //accesores
    public long getId() {
        return id;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
    public cardType getType() {
        return type;
    }
    public void setType(cardType type) {
        this.type = type;
    }
    public cardColor getColor() {
        return color;
    }
    public void setColor(cardColor color) {
        this.color = color;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public short getCvv() {
        return cvv;
    }
    public void setCvv(short cvv) {
        this.cvv = cvv;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
    public Client getCardOwner() {
        return cardOwner;
    }
    public void setCardOwner(Client cardOwner) {
        this.cardOwner = cardOwner;
    }

    @JsonIgnore

    //impresores
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardHolder='" + cardHolder + '\'' +
                ", type=" + type +
                ", color=" + color +
                ", number='" + number + '\'' +
                ", cvv=" + cvv +
                ", fromDate=" + fromDate +
                ", thruDate=" + thruDate +
                ", owner=" + cardOwner +
                '}';
    }
}