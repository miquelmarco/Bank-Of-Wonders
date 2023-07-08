package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RequestMapping("/api")
@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/clients")
    public List<ClientDTO> getAllClients() {
        return clientService.findAll();
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getOneClient(@PathVariable Long id) {
        return clientService.getClientDTO(id);
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
        } else if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("email already exist", HttpStatus.FORBIDDEN);
        }
        String encodedPass = passwordEncoder.encode(password);
        Client clientRegister = new Client(firstName, lastName, email, encodedPass);
        clientService.save(clientRegister);
        Account account = new Account("", LocalDate.now(),0.0);
        clientRegister.addAccount(account);
        accountService.save(account);
        account.setNumber(Utilities.numberAccountGenerator(account));
        accountService.save(account);
        return new ResponseEntity<>("registration completed successfully!", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getCurrent (Authentication authentication) {
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }
}
//          -- Números de Status
//          403 - forbidden
//          401 - unauthorized
//          201 - created