package com.mindhub.homebanking;
import com.mindhub.homebanking.services.*;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
@SpringBootApplication
public class HomebankingApplication {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }
    @Bean
    public CommandLineRunner initData(ClientService clientService, AccountService accountService, TransactionService transactionService, LoanService loanService, ClientLoanService clientLoanService, CardService cardService) {
        return args -> {
            Client admin = new Client("admin", "admin", "admin@homebanking.com", passwordEncoder.encode("123"));
            Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
            Client client2 = new Client("Fede", "Paez", "fedepaez@outlook.com", passwordEncoder.encode("123"));
            Client client3 = new Client("Batman", "El gato", "batmanElGato@outlook.com", passwordEncoder.encode("123"));
            Client client4 = new Client("Nati", "Requena", "nati@gmail.com", passwordEncoder.encode("123"));
            Account account1 = new Account("VIN001", AccountType.SAVINGS, LocalDate.now(), 0.0, false);
            Account account2 = new Account("VIN002", AccountType.CURRENT, LocalDate.now(), 0.0, false);
            Account account3 = new Account("VIN003", AccountType.SAVINGS, LocalDate.now(), 0.0, true);
            Account account4 = new Account("VIN004", AccountType.CURRENT, LocalDate.now(), 0.0, true);
            Account account5 = new Account("VIN005", AccountType.CURRENT, LocalDate.now(), 10000000.0, true);
            Transaction trans1 = new Transaction(5340.02, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, false);
            Transaction trans2 = new Transaction(482.02, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, false);
            Transaction trans3 = new Transaction(-5084.06, "supermarket purchase", TransactionType.DEBIT, LocalDateTime.now(),0.0, false);
            Transaction trans4 = new Transaction(4432.06, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, false);
            Transaction trans5 = new Transaction(965321.02, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, false);
            Transaction trans6 = new Transaction(-125480.15, "retail purchase", TransactionType.DEBIT, LocalDateTime.now(),0.0, false);
            Transaction trans7 = new Transaction(4421.02, "supermarket purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, false);
            Transaction trans8 = new Transaction(315.06, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, false);
            Transaction trans9 = new Transaction(-4215.03, "retail purchase", TransactionType.DEBIT, LocalDateTime.now(),0.0, true);
            Transaction trans10 = new Transaction(1544.82, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, true);
            Transaction trans11 = new Transaction(432.65, "supermarket purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, true);
            Transaction trans12 = new Transaction(814.21, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, true);
            Transaction trans13 = new Transaction(-32.25, "retail purchase", TransactionType.DEBIT, LocalDateTime.now(),0.0, true);
            Transaction trans14 = new Transaction(215.36, "supermarket purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, true);
            Transaction trans15 = new Transaction(-215.25, "retail purchase", TransactionType.DEBIT, LocalDateTime.now(),0.0, true);
            Transaction trans16 = new Transaction(1256.25, "retail purchase", TransactionType.CREDIT, LocalDateTime.now(),0.0, true);
            Loan loan1 = new Loan("Mortgage", 500000.0, 10, Arrays.asList(12, 24, 36, 48, 60));
            Loan loan2 = new Loan("Personal", 100000.0, 20, Arrays.asList(6, 12, 24));
            Loan loan3 = new Loan("Automotive", 300000.0, 30, Arrays.asList(6, 12, 24, 36));
            ClientLoan clientLoan1 = new ClientLoan(400000.0, 60, 300000.0, 42);
            ClientLoan clientLoan2 = new ClientLoan(50000.0, 12, 30000.0, 12);
            ClientLoan clientLoan3 = new ClientLoan(100000.0, 24, 12211.0, 12);
            ClientLoan clientLoan4 = new ClientLoan(200000.0, 36, 12112.0, 12);
            Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.GOLD, "1234-5678-9101-1121", (short) 894, LocalDate.now(), LocalDate.now().minusDays(5), true);
            Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "9874-5256-3652-5412", (short) 256, LocalDate.now(), LocalDate.now().plusYears(5), false);
            Card card3 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.DEBIT, CardColor.SILVER, "5542-3636-5441-5545", (short) 441, LocalDate.now(), LocalDate.now().minusDays(10), true);
            Card card4 = new Card((client2.getFirstName() + " " + client2.getLastName()), CardType.CREDIT, CardColor.GOLD, "5512-9856-8745-3652", (short) 885, LocalDate.now(), LocalDate.now().plusYears(5), true);
            Card card5 = new Card((client3.getFirstName() + " " + client3.getLastName()), CardType.DEBIT, CardColor.TITANIUM, "4526-7554-5744-6557", (short) 324, LocalDate.now(), LocalDate.now().plusYears(5), true);
            Card card6 = new Card((client4.getFirstName() + " " + client4.getLastName()), CardType.DEBIT, CardColor.TITANIUM, "1234-1234-1234-1234", (short) 111, LocalDate.now(), LocalDate.now().plusYears(5), true);




            client1.addClientLoan(clientLoan1);
            loan1.addClientLoan(clientLoan1);
            client1.addClientLoan(clientLoan2);
            loan2.addClientLoan(clientLoan2);
            client2.addClientLoan(clientLoan3);
            loan2.addClientLoan(clientLoan3);
            client2.addClientLoan(clientLoan4);
            loan3.addClientLoan(clientLoan4);
            List<Account> addAccountClient1 = Arrays.asList(account1, account2);
            List<Account> addAccountClient2 = Arrays.asList(account3, account4);
            List<Account> addAccountClient4 = Arrays.asList(account5);
            client1.addAccounts(addAccountClient1);
            client2.addAccounts(addAccountClient2);
            client4.addAccounts(addAccountClient4);
            List<Transaction> addTransactionClient1 = Arrays.asList(trans1, trans2, trans3, trans4);
            account1.addTransactions(addTransactionClient1);
            List<Transaction> addTransactionClient2 = Arrays.asList(trans5, trans6, trans7, trans8);
            account2.addTransactions(addTransactionClient2);
            List<Transaction> addTransactionClient3 = Arrays.asList(trans9, trans10, trans11, trans12);
            account3.addTransactions(addTransactionClient3);
            List<Transaction> addTransactionClient4 = Arrays.asList(trans13, trans14, trans15, trans16);
            account4.addTransactions(addTransactionClient4);
            List<Card> addCardClient1 = Arrays.asList(card1, card2, card3);
            client1.addCards(addCardClient1);
            List<Card> addCardClient2 = List.of(card4);
            client2.addCards(addCardClient2);
            List<Card> addCardClient3 = List.of(card5);
            client3.addCards(addCardClient3);
            List<Card> addCardClient4 = List.of(card6);
            client4.addCards(addCardClient4);
            List<Client> clients = Arrays.asList(client1, client2, client3, client4, admin);
            clientService.saveAll(clients);
            List<Account> accounts = Arrays.asList(account1, account2, account3, account4, account5);
            accountService.saveAll(accounts);
            List<Transaction> transactions = Arrays.asList(trans1, trans2, trans3, trans4, trans5, trans6, trans7, trans8,
                    trans9, trans10, trans11, trans12, trans13, trans14, trans15, trans16);
            transactionService.saveAll(transactions);
            List<Loan> loans = Arrays.asList(loan1, loan2, loan3);
            loanService.saveAll(loans);
            List<ClientLoan> clientLoans = Arrays.asList(clientLoan1, clientLoan2, clientLoan3, clientLoan4);
            clientLoanService.saveAll(clientLoans);
            List<Card> cards = Arrays.asList(card1, card2, card3, card4, card5, card6);
            cardService.saveAll(cards);
        };
    }
}