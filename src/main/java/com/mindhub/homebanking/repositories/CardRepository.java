package com.mindhub.homebanking.repositories;
import com.mindhub.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByNumber(String number);
    boolean existsByNumber(String string);
}