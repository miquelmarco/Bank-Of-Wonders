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
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String encodedPass = passwordEncoder.encode(password);
        Client clientRegister = new Client(firstName, lastName, email, encodedPass);
        clientRepository.save(clientRegister);
        Account account = new Account("", LocalDate.now(),0.0);
        clientRegister.addAccount(account);
        accountRepository.save(account);
        account.setNumber(Utilities.numberAccountGenerator(account));
        accountRepository.save(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getCurrent (Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
}

//      NOTAS:
//          -- En el método generador de números random
//          El símbolo "%" indica el comienzo de la especificación de formato.
//          "0" indica que el relleno debe ser realizado con ceros.
//          "8" indica que el ancho total del campo debe ser de 8 caracteres, incluyendo ceros a la izquierda si es necesario.
//          "d" indica que el argumento proporcionado es un número entero decimal.

//          -- Números de Status
//          403 - forbidden
//          201 - created