package com.mindhub.homebanking.services.Implement;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
    @Override
    public void saveAll(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transactionRepository.save(transaction);
        }
    }
//    @Override
//    public void saveAll(List<Transaction> transactions) {
//        int size = transactions.size();
//        for (int i = 0; i < transactions.size(); i++) {
//            Transaction transaction = transactions.get(i);
//            transactionRepository.save(transaction);
//        }
//    }
}