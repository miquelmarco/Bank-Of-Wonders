package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> loanRequest(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Loan loanType = loanRepository.findById(loanApplicationDTO.getLoanTypeId()).orElse(null);
        Account account = accountRepository.findByNumber(loanApplicationDTO.getDestinationAcc());
        if (loanType == null) {
            return new ResponseEntity<>("Incorrect loan type", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("Account used to apply to the loan do not exist", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() <= 10000) {
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
        Double plusPercentage = (loanApplicationDTO.getAmount() * 20 / 100) + (loanApplicationDTO.getAmount());
        ClientLoan newLoan = new ClientLoan(plusPercentage, loanApplicationDTO.getPayments());
        Transaction newTransaction = new Transaction(loanApplicationDTO.getAmount(), loanType.getName() + ": " + "loan approved", TransactionType.CREDIT, LocalDateTime.now());
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        client.addClientLoan(newLoan);
        loanType.addClientLoan(newLoan);
        transactionRepository.save(newTransaction);
        clientLoanRepository.save(newLoan);
        accountRepository.save(account);
        loanRepository.save(loanType);
        clientRepository.save(client);
        return new ResponseEntity<>("Loan added to the account", HttpStatus.CREATED);
    }
}