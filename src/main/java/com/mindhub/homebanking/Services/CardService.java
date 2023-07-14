package com.mindhub.homebanking.Services;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import java.util.List;
public interface CardService {
    void save(Card card);
    void saveAll(List<Card> cards);
    List<Card> findAll();
    boolean existsByNumber(String string);
    Card findByNumber(String string);
}