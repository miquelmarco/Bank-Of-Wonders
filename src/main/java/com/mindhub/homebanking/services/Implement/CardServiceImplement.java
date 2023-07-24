package com.mindhub.homebanking.services.Implement;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }
    @Override
    public void saveAll(List<Card> cards) {
        for (Card card : cards) {
            cardRepository.save(card);
        }
    }
    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }
    @Override
    public boolean existsByNumber(String string) {
        return cardRepository.existsByNumber(string);
    }
    @Override
    public Card findByNumber(String string) {
        return cardRepository.findByNumber(string);
    }
}