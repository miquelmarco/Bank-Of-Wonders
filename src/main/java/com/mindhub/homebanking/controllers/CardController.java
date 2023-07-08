package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import static com.mindhub.homebanking.utils.Utilities.cardNumberGenerator;
import static com.mindhub.homebanking.utils.Utilities.cvvGenerator;
@RequestMapping("/api")
@RestController
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;
    @PostMapping("/clients/current/cards")
    private ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        if (client.getCards().stream().filter(card -> card.getType() == cardType && card.getColor() == cardColor).count() >= 1) {
            return new ResponseEntity<>("you already have this card!", HttpStatus.FORBIDDEN);
        } else {
            Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5));
            cardService.save(newCard);
            newCard.setCvv(cvvGenerator());
            String generatedNumber;
            do {
                generatedNumber = cardNumberGenerator();
                newCard.setNumber(generatedNumber);
            } while (cardService.existsByNumber(generatedNumber));
            client.addCard(newCard);
            cardService.save(newCard);
        }
        return new ResponseEntity<>("Card generated ok", HttpStatus.OK);
    }
}