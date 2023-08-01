package com.mindhub.homebanking.services.Implement;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public List<AccountDTO> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByNumber(String string) {
        return accountRepository.findByNumber(string);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }
    @Override
    public void saveAll(List<Account> accounts) {
        for (Account account : accounts) {
            this.save(account);
        }
    }
}
