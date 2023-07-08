package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RequestMapping("/api") @RestController
public class AccountController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        return accountService.findAll();
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getOneAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);
        if (account == null){
            return new ResponseEntity<>("account not found", HttpStatus.NOT_FOUND);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("is not your account", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        if (client.getAccounts().stream().count() < 3) {
            Account account = new Account("", LocalDate.now(), 0.0);
            accountService.save(account);
            account.setNumber(Utilities.numberAccountGenerator(account));
            client.addAccount(account);
            accountService.save(account);
            clientService.save(client);
            return new ResponseEntity<>("account created :D",HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("3accounts", HttpStatus.FORBIDDEN);
        }
    }
}