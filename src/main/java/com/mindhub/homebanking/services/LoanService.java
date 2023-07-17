package com.mindhub.homebanking.services;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import java.util.List;
public interface LoanService {
    List<LoanDTO> findAll();
    Loan findById(Long id);
    void save(Loan loan);
    void saveAll(List<Loan> loans);
}
