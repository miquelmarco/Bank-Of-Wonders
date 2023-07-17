package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@RequestMapping("/api")
@RestController
public class ClientLoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/clientLoan/payments")
    public ResponseEntity<Object> payLoan(Authentication authentication, @RequestParam Long loanToPay, String account) {
        if (authentication == null) {
            return new ResponseEntity<>("Client must be authenticated", HttpStatus.FORBIDDEN);
        }
        if (loanToPay == null) {
            return new ResponseEntity<>("Missing loan information", HttpStatus.FORBIDDEN);
        }
        if (account.isBlank()) {
            return new ResponseEntity<>("Missing account", HttpStatus.FORBIDDEN);
        }
        Client client = clientService.findByEmail(authentication.getName());
        Account accToPay = accountService.findByNumber(account);
        ClientLoan clientLoan = clientLoanService.findById(loanToPay);
        Double payment = clientLoan.getAmount() / clientLoan.getPayments();
        if (client == null) {
            return new ResponseEntity<>("Client doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (accToPay == null) {
            return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (accToPay.getBalance() < payment) {
            return new ResponseEntity<>("Insufficient amount", HttpStatus.FORBIDDEN);
        }
        if (clientLoan.getRemainAmount() <= 0) {
            return new ResponseEntity<>("Fully paid loan", HttpStatus.FORBIDDEN);
        }
        Transaction newTransaction = new Transaction(payment, "Loan Payment", TransactionType.DEBIT, LocalDateTime.now(), accToPay.getBalance() - payment, true);
        accToPay.setBalance(accToPay.getBalance() - payment);
        clientLoan.setRemainPayments(clientLoan.getRemainPayments() - 1);
        clientLoan.setRemainAmount(clientLoan.getRemainAmount() - payment);

        accToPay.addTransaction(newTransaction);
        accountService.save(accToPay);
        clientLoanService.save(clientLoan);
        transactionService.save(newTransaction);
        return new ResponseEntity<>("Loan payment correct", HttpStatus.OK);
    }
    @GetMapping("/clientLoans/{id}")
    public ResponseEntity<?> getOneLoan(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        ClientLoan loan = clientLoanService.findById(id);
        if (loan == null){
            return new ResponseEntity<>("Loan not found", HttpStatus.NOT_FOUND);
        }
        if (!client.getClientLoans().contains(loan)) {
            return new ResponseEntity<>("Is not your loan", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new ClientLoanDTO(loan), HttpStatus.OK);
    }
}