package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
@RequestMapping("/api") @RestController
public class TransactionController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transactionMaker (@RequestParam Double amount, @RequestParam String originAccountNumber,
                                                    @RequestParam String destinyAccountNumber, @RequestParam String description,
                                                    Authentication authentication) {
        Account originAccount = accountRepository.findByNumber(originAccountNumber);
        Account destinyAccount = accountRepository.findByNumber(destinyAccountNumber);
        if (amount.isNaN() || amount == 0.0) {
            return new ResponseEntity<>("Missing transaction amount", HttpStatus.FORBIDDEN);
        }
        if (amount < 1.0) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing origin account", HttpStatus.FORBIDDEN);
        }
        if (destinyAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing destination account", HttpStatus.FORBIDDEN);
        }
        if (destinyAccountNumber.equals(originAccountNumber)) {
            return new ResponseEntity<>("Destination account must be different", HttpStatus.FORBIDDEN);
        }
//        if (originAccount == null || destinyAccount == null) {
//            return new ResponseEntity<>("Account do not exist!", HttpStatus.FORBIDDEN);
//        }
        // separar la condición anterior para que el cliente tenga mayor info.
        if (originAccount == null) {
            return new ResponseEntity<>("Origin account do not exist", HttpStatus.FORBIDDEN);
        }
        if (destinyAccount == null) {
            return new ResponseEntity<>("Destination account do not exist", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(authentication.getName()).getAccounts().stream().noneMatch(account -> account.getNumber().equals(originAccountNumber))) {
            return new ResponseEntity<>("Not your account!", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        Transaction debit = new Transaction(Double.parseDouble("-" + amount), "To " + destinyAccountNumber + ": " + description, TransactionType.DEBIT, LocalDateTime.now());
        Transaction credit = new Transaction(Double.parseDouble("+" + amount), "From " + originAccountNumber + ": " + description, TransactionType.CREDIT, LocalDateTime.now());
        originAccount.addTransaction(debit);
        destinyAccount.addTransaction(credit);
        originAccount.setBalance(originAccount.getBalance() - amount);
        destinyAccount.setBalance(destinyAccount.getBalance() + amount);
        transactionRepository.save(debit);
        transactionRepository.save(credit);
        accountRepository.save(originAccount);
        accountRepository.save(destinyAccount);
        return new ResponseEntity<>("Transaction ok", HttpStatus.OK);
    }
}