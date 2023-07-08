package com.mindhub.homebanking.Services;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import java.util.List;
public interface AccountService {
    List<AccountDTO> findAll();
    Account findById(Long id);
    Account findByNumber(String string);
    void save(Account account);
    void saveAll(List<Account> accounts);
}
