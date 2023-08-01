package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
@RequestMapping("/api") @RestController
public class TransactionController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transactionMaker (@RequestParam Double amount, @RequestParam String originAccountNumber,
                                                    @RequestParam String destinyAccountNumber, @RequestParam String description,
                                                    Authentication authentication) {
        if (amount <= 0.0) {
            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);
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
        if (description.isBlank()) {
            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
        }
        Account originAccount = accountService.findByNumber(originAccountNumber);
        Account destinyAccount = accountService.findByNumber(destinyAccountNumber);
        if (originAccount == null) {
            return new ResponseEntity<>("Origin account do not exist", HttpStatus.FORBIDDEN);
        }
        if (destinyAccount == null) {
            return new ResponseEntity<>("Destination account do not exist", HttpStatus.FORBIDDEN);
        }
        if (!destinyAccount.isActive()) {
            return new ResponseEntity<>("Inactive account", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(authentication.getName()).getAccounts().stream().noneMatch(account -> account.getNumber().equals(originAccountNumber))) {
            return new ResponseEntity<>("Not your account!", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        Transaction debit = new Transaction(Double.parseDouble("-" + amount), "To " + destinyAccountNumber + ": " + description, TransactionType.DEBIT, LocalDateTime.now(), originAccount.getBalance() - amount, true);
        Transaction credit = new Transaction(Double.parseDouble("+" + amount), "From " + originAccountNumber + ": " + description, TransactionType.CREDIT, LocalDateTime.now(), destinyAccount.getBalance() + amount, true);
        originAccount.addTransaction(debit);
        destinyAccount.addTransaction(credit);
        originAccount.setBalance(originAccount.getBalance() - amount);
        destinyAccount.setBalance(destinyAccount.getBalance() + amount);
        transactionService.save(debit);
        transactionService.save(credit);
        accountService.save(originAccount);
        accountService.save(destinyAccount);
        return new ResponseEntity<>("Transaction ok", HttpStatus.OK);
    }
}