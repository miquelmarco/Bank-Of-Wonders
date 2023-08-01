package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.*;
import com.mindhub.homebanking.services.*;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.findAll();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> loanRequest(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        if (loanApplicationDTO.getAmount() == null || loanApplicationDTO.getPayments() == null || loanApplicationDTO.getLoanTypeId() == null || loanApplicationDTO.getDestinationAcc() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        Loan loanType = loanService.findById(loanApplicationDTO.getLoanTypeId());
        Account account = accountService.findByNumber(loanApplicationDTO.getDestinationAcc());
        if (loanType == null) {
            return new ResponseEntity<>("Incorrect loan type", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("Account used to apply to the loan do not exist", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 9999) {
            return new ResponseEntity<>("Insufficient amount", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("Incorrect payments amount", HttpStatus.FORBIDDEN);
        }
        if (!loanType.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("That number of payments is not allowed", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loanType.getMaxAmount()) {
            return new ResponseEntity<>("Exceeded the loan maximun amount", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().stream().noneMatch(chocolate -> chocolate.getNumber().equals(loanApplicationDTO.getDestinationAcc()))) {
            return new ResponseEntity<>("Account do not belongs to the client", HttpStatus.FORBIDDEN);
        }
        double plusPercentage = (loanApplicationDTO.getAmount() * loanType.getPercentage() / 100) + (loanApplicationDTO.getAmount());
        ClientLoan newLoan = new ClientLoan(plusPercentage, loanApplicationDTO.getPayments(), plusPercentage, loanApplicationDTO.getPayments());
        Transaction newTransaction = new Transaction(loanApplicationDTO.getAmount(), loanType.getName() + ": " + "loan approved", TransactionType.CREDIT, LocalDateTime.now(), account.getBalance() + loanApplicationDTO.getAmount(), true);
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        account.addTransaction(newTransaction);
        client.addClientLoan(newLoan);
        loanType.addClientLoan(newLoan);
        transactionService.save(newTransaction);
        clientLoanService.save(newLoan);
        accountService.save(account);
        loanService.save(loanType);
        clientService.save(client);
        return new ResponseEntity<>("Loan added to the account", HttpStatus.CREATED);
    }

    @GetMapping("/clients/current/loans")
    public List<ClientLoanDTO> getLoans(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        return client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toList());
    }

//    @PostMapping("/loans/new")
//    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanCreationDTO loanCreationDTO) {
//        if (loanCreationDTO.getName().isBlank() || loanCreationDTO.getMaxPayment().isEmpty() || loanCreationDTO.getPercentage() == null || loanCreationDTO.getMaxAmount().isNaN()) {
//            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
//        }
//        if (!authentication.isAuthenticated()) {
//            return new ResponseEntity<>("Admin must be authenticated", HttpStatus.FORBIDDEN);
//        }
//        if (loanService.findByName(loanCreationDTO.getName()) != null) {
//            return new ResponseEntity<>("Loan name is used", HttpStatus.FORBIDDEN);
//        }
//        List<Integer> payments = new ArrayList<>();
//        if (loanCreationDTO.getMaxPayment().equals("12")) {
//            payments.addAll(Arrays.asList(3, 6, 12));
//        }
//        if (loanCreationDTO.getMaxPayment().equals("24")) {
//            payments.addAll(Arrays.asList(3, 6, 12, 24));
//        }
//        if (loanCreationDTO.getMaxPayment().equals("36")) {
//            payments.addAll(Arrays.asList(3, 6, 12, 24, 36));
//        }
//        if (loanCreationDTO.getMaxPayment().equals("48")) {
//            payments.addAll(Arrays.asList(3, 6, 12, 24, 36, 48));
//        }
//        Loan newLoan = new Loan(loanCreationDTO.getName(), loanCreationDTO.getMaxAmount(), loanCreationDTO.getPercentage(), payments);
//        loanService.save(newLoan);
//        return new ResponseEntity<>("Loan created ok", HttpStatus.CREATED);
//    }
    @PostMapping("/loans/new")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanCreationV2DTO loanCreationV2DTO) {
        if (authentication.getName() == null || loanCreationV2DTO == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (loanService.findByName(loanCreationV2DTO.getName()) != null) {
            return new ResponseEntity<>("Loan name is used", HttpStatus.FORBIDDEN);
        }
        List<Integer> orderedList = loanCreationV2DTO.getPayments().stream().sorted().collect(Collectors.toList());

        Loan newLoan = new Loan(loanCreationV2DTO.getName(), loanCreationV2DTO.getMaxAmount(), loanCreationV2DTO.getPercentage(), orderedList);
        loanService.save(newLoan);
        return new ResponseEntity<>("Loan type correctly added", HttpStatus.CREATED);
    }
}