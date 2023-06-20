package com.mindhub.homebanking.dtos;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.cardColor;
import com.mindhub.homebanking.models.cardType;
import java.time.LocalDate;
public class CardDTO {
    private long id;
    private String cardHolder;
    private cardType type;
    private cardColor color;
    private String number;
    private short cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    //contructor DTO
    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardOwner().getFirstName() + " " + card.getCardOwner().getLastName();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }
    //getters
    public long getId() {
        return id;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public cardType getType() {
        return type;
    }
    public cardColor getColor() {
        return color;
    }
    public String getNumber() {
        return number;
    }
    public short getCvv() {
        return cvv;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
}
