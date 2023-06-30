package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getOneClient(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password
    ){
        if (firstName.isBlank()){
            return new ResponseEntity<>("the first name is missing", HttpStatus.FORBIDDEN);
        } else if (lastName.isBlank()) {
            return new ResponseEntity<>("the last name is missing", HttpStatus.FORBIDDEN);
        } else if (email.isBlank()) {
            return new ResponseEntity<>("the email is missing", HttpStatus.FORBIDDEN);
        } else if (password.isBlank()) {
            return new ResponseEntity<>("the password is missing", HttpStatus.FORBIDDEN);
        } else if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("email already exist", HttpStatus.FORBIDDEN);
        }
        String encodedPass = passwordEncoder.encode(password);
        Client clientRegister = new Client(firstName, lastName, email, encodedPass);
        clientRepository.save(clientRegister);
        Account account = new Account("", LocalDate.now(),0.0);
        clientRegister.addAccount(account);
        accountRepository.save(account);
        account.setNumber(Utilities.numberAccountGenerator(account));
        accountRepository.save(account);
        return new ResponseEntity<>("registration completed successfully!", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getCurrent (Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
}
//          -- Números de Status
//          403 - forbidden
//          401 - unauthorized
//          201 - created