package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@RequestMapping("/api") @RestController
public class AccountController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        return accountService.findAll();
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getOneAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);
        if (account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("Is not your account", HttpStatus.FORBIDDEN);
        }
        if (!account.isActive()) {
            return new ResponseEntity<>("Inactive account", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam String type) {
        if (authentication.getName().isBlank()) {
            return new ResponseEntity<>("You need to be authenticated", HttpStatus.FORBIDDEN);
        }
        Client client = clientService.findByEmail(authentication.getName());
        if (client == null) {
            return new ResponseEntity<>("Client doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (type == null) {
            return new ResponseEntity<>("Missing account type", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().stream().filter(account -> account.isActive()).count() < 3 && type.equals("SAVINGS")) {
            Account account = new Account("", AccountType.SAVINGS,  LocalDate.now(), 0.0, true);
            accountService.save(account);
            account.setNumber(Utilities.numberAccountGenerator(account));
            client.addAccount(account);
            accountService.save(account);
            clientService.save(client);
            return new ResponseEntity<>("Savings account created :D",HttpStatus.CREATED);
        }
        if (client.getAccounts().stream().filter(account -> account.isActive()).count() < 3 && type.equals("CURRENT")) {
            Account account = new Account("", AccountType.CURRENT,  LocalDate.now(), 0.0, true);
            accountService.save(account);
            account.setNumber(Utilities.numberAccountGenerator(account));
            client.addAccount(account);
            accountService.save(account);
            clientService.save(client);
            return new ResponseEntity<>("Current account created :D",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Account can't be created, try again!", HttpStatus.FORBIDDEN);
    }
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getActiveAccounts(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        List<Account> accounts = client.getAccounts().stream().filter(account -> account.isActive()).collect(Collectors.toList());
        return accounts.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }
    @PatchMapping ("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam String number) {
        if (authentication.getName() == null) {
            return new ResponseEntity<>("Client not authenticated", HttpStatus.FORBIDDEN);
        }
        if (number.isBlank()) {
            return new ResponseEntity<>("Account number incorrect", HttpStatus.FORBIDDEN);
        }
        Account account = accountService.findByNumber(number);
        List<Transaction> transactions = account.getTransactions().stream().filter(transaction -> transaction.isActive()).collect(Collectors.toList());
        if (account.getBalance() != 0) {
            return new ResponseEntity<>("Account balance must be 0 to delete", HttpStatus.FORBIDDEN);
        }
        transactions.forEach(transaction -> transaction.setActive(false));
        account.setActive(false);
        transactionService.saveAll(transactions);
        accountService.save(account);
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }
}