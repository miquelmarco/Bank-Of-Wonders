package com.mindhub.homebanking.services.Implement;
import com.mindhub.homebanking.services.ClientLoanService;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ClientLoanServiceImplement implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Override
    public void save(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }
    @Override
    public void saveAll(List<ClientLoan> clientLoans) {
        for (ClientLoan clientLoan : clientLoans) {
            clientLoanRepository.save(clientLoan);
        }
    }
    @Override
    public ClientLoan findById(Long id) {
       return clientLoanRepository.findById(id).orElse(null);
    }
}