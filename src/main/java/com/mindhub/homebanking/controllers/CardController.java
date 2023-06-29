package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
@RequestMapping ("/api")
@RestController
public class CardController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

//    @PostMapping("/clients/current/cards")
//    private ResponseEntity<Object> createCard (@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
//        Client client = clientRepository.findByEmail(authentication.getName());
//        if (client.getCards().stream().count() > 6) {
//            return new ResponseEntity<>("Max cards reached!", HttpStatus.FORBIDDEN);
//        } else if (client.getCards().stream().filter(card -> card.getType() == CardType.DEBIT && card.getColor() == CardColor.SILVER).count() > 1) {
//            return new ResponseEntity<>("debitSilver fallo",HttpStatus.FORBIDDEN);
//        } else if (client.getCards().stream().filter(card -> card.getType() == CardType.CREDIT && card.getColor() == CardColor.SILVER).count() > 1) {
//            return new ResponseEntity<>("creditSilver fallo",HttpStatus.FORBIDDEN);
//        } else if (client.getCards().stream().filter(card -> card.getType() == CardType.DEBIT && card.getColor() == CardColor.GOLD).count() > 1) {
//            return new ResponseEntity<>("debitGold fallo", HttpStatus.FORBIDDEN);
//        } else if (client.getCards().stream().filter(card -> card.getType() == CardType.CREDIT && card.getColor() == CardColor.GOLD).count() > 1) {
//            return new ResponseEntity<>("creditGold fallo", HttpStatus.FORBIDDEN);
//        } else if (client.getCards().stream().filter(card -> card.getType() == CardType.DEBIT && card.getColor() == CardColor.TITANIUM).count() > 1) {
//            return new ResponseEntity<>("debitTitanium fallo", HttpStatus.FORBIDDEN);
//        } else if (client.getCards().stream().filter(card -> card.getType() == CardType.CREDIT && card.getColor() == CardColor.TITANIUM).count() > 1) {
//            return new ResponseEntity<>("creditTitanium fallo", HttpStatus.FORBIDDEN);
//        } else {
//            Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5));
//            cardRepository.save(newCard);
//            newCard.setCvv(Utilities.cvvGenerator());
//            Set<String> getAllCardNumbers = cardRepository.findAll().stream().map(card -> card.getNumber()).collect(Collectors.toSet());
//            String generatedNumber = Utilities.cardNumberGenerator();
//            while (getAllCardNumbers.contains(generatedNumber)) {
//                generatedNumber = Utilities.cardNumberGenerator();
//            }
//            newCard.setNumber(generatedNumber);
//            client.addCard(newCard);
//            cardRepository.save(newCard);
//        }
//        return new ResponseEntity<>("Card generated ok", HttpStatus.OK);
//    }

    //INTENTO 2

//@PostMapping("/clients/current/cards")
//private ResponseEntity<Object> createCard (@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
//    Client client = clientRepository.findByEmail(authentication.getName());
//    if (client.getCards().stream().count() >= 6) {
//        return new ResponseEntity<>("Max cards reached!", HttpStatus.FORBIDDEN);
//    } else if (client.getCards().stream().filter(card -> card.getType() == CardType.DEBIT && card.getColor() == CardColor.SILVER).count() > 1
//
//        || client.getCards().stream().filter(card -> card.getType() == CardType.CREDIT && card.getColor() == CardColor.SILVER).count() > 1
//
//        || client.getCards().stream().filter(card -> card.getType() == CardType.DEBIT && card.getColor() == CardColor.GOLD).count() > 1
//
//        || client.getCards().stream().filter(card -> card.getType() == CardType.CREDIT && card.getColor() == CardColor.GOLD).count() > 1
//
//        || client.getCards().stream().filter(card -> card.getType() == CardType.DEBIT && card.getColor() == CardColor.TITANIUM).count() > 1
//
//        || client.getCards().stream().filter(card -> card.getType() == CardType.CREDIT && card.getColor() == CardColor.TITANIUM).count() > 1);
//     {
//        Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5));
//        cardRepository.save(newCard);
//        newCard.setCvv(Utilities.cvvGenerator());
//        Set<String> getAllCardNumbers = cardRepository.findAll().stream().map(card -> card.getNumber()).collect(Collectors.toSet());
//        String generatedNumber = Utilities.cardNumberGenerator();
//        while (getAllCardNumbers.contains(generatedNumber)) {
//            generatedNumber = Utilities.cardNumberGenerator();
//        }
//        newCard.setNumber(generatedNumber);
//        client.addCard(newCard);
//        cardRepository.save(newCard);
//    }
//    return new ResponseEntity<>("Card generated ok", HttpStatus.OK);
//}

// INTENTO 3

    @PostMapping("/clients/current/cards")
    private ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getCards().stream().filter(card -> card.getType() == cardType && card.getColor() == cardColor).count() >= 1) {
            return new ResponseEntity<>("Muchas tarjetas bro aka espectativas fallidas lol", HttpStatus.EXPECTATION_FAILED);
        } else {
            Card newCard = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, LocalDate.now(), LocalDate.now().plusYears(5));
            cardRepository.save(newCard);
            newCard.setCvv(Utilities.cvvGenerator());
            Set<String> getAllCardNumbers = cardRepository.findAll().stream().map(card -> card.getNumber()).collect(Collectors.toSet());
            String generatedNumber = Utilities.cardNumberGenerator();
            while (getAllCardNumbers.contains(generatedNumber)) {
                generatedNumber = Utilities.cardNumberGenerator();
            }
            newCard.setNumber(generatedNumber);
            client.addCard(newCard);
            cardRepository.save(newCard);
        }
        return new ResponseEntity<>("Card generated ok", HttpStatus.OK);
    }
}