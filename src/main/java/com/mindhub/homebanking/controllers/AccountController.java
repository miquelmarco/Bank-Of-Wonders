package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
//    @GetMapping("/accounts/{id}")
//    public AccountDTO getOneAccount(@PathVariable Long id) {
//        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
//    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getOneAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null){
            return new ResponseEntity<>("account not found", HttpStatus.NOT_FOUND);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("is not your account", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
    }
//    @GetMapping("/accounts/{id}")
//    public ResponseEntity<Object> getOneAccount(@PathVariable Long id, Authentication authentication) {
//        Client client = clientRepository.findByEmail(authentication.getName());
//        try {
//            return new ResponseEntity<>(accountRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Account not found")),HttpStatus.OK);
//        } catch (ObjectNotFoundException exception) {
//            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().stream().count() < 3) {
            Account account = new Account("", LocalDate.now(), 0.0);
            accountRepository.save(account);
            account.setNumber(Utilities.numberAccountGenerator(account));
            client.addAccount(account);
            accountRepository.save(account);
            clientRepository.save(client);
            return new ResponseEntity<>("account created :D",HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("3accounts", HttpStatus.FORBIDDEN);
        }
    }
}