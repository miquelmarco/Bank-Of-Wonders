package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import static com.mindhub.homebanking.utils.Utilities.cardNumberGenerator;
import static com.mindhub.homebanking.utils.Utilities.cvvGenerator;
@RequestMapping("/api")
@RestController
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;
    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCurrentClientCards(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        List<CardDTO> cards = client.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
        return cards.stream().filter(cardDTO -> cardDTO.getActive()).collect(Collectors.toList());
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        if (client.getCards().stream().filter(card -> card.getType() == cardType && card.getColor() == cardColor && card.getActive()).count() >= 1) {
            return new ResponseEntity<>("You already have one of this card!", HttpStatus.FORBIDDEN);
        } else {
            Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5), true);
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
        return new ResponseEntity<>("Card Approved", HttpStatus.OK);
    }
    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> deleteCard(@RequestParam String cardNumber, Authentication authentication) {
        if (cardNumber.isBlank()) {
            return new ResponseEntity<>("Missing card data", HttpStatus.FORBIDDEN);
        }
        if (authentication.getName() == null) {
            return new ResponseEntity<>("not autheticated client", HttpStatus.FORBIDDEN);
        }
        Client client = clientService.findByEmail(authentication.getName());
        Card card = cardService.findByNumber(cardNumber);
        if (client.getCards().stream().noneMatch(card1 -> card1.getNumber().equals(cardNumber))) {
            return new ResponseEntity<>("This card is not yours", HttpStatus.FORBIDDEN);
        }
        card.setActive(false);
        cardService.save(card);
        return new ResponseEntity<>("Card deleted", HttpStatus.OK);
    }
    @PostMapping("/cards/renew")
    public ResponseEntity<Object> renewCard(Authentication authentication, @RequestParam String number) {
        if (authentication.getName().isBlank()) {
            return new ResponseEntity<>("Client must be authenticated",HttpStatus.FORBIDDEN);
        }
        if (number.isBlank()) {
            return  new ResponseEntity<>("Error, try again!", HttpStatus.FORBIDDEN);
        }
        Client client = clientService.findByEmail(authentication.getName());
        Card card = cardService.findByNumber(number);
        if (!client.getCards().contains(card)) {
            return new ResponseEntity<>("Not your card!", HttpStatus.FORBIDDEN);
        }
        if (!card.getThruDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>("Card is not expired!", HttpStatus.FORBIDDEN);
        }
        card.setActive(false);
        cardService.save(card);
        createCard(card.getType(), card.getColor(), authentication);
        return new ResponseEntity<>("Card renew", HttpStatus.OK);
    }
}