package com.example.labtestproject.repositories;

import com.example.labtestproject.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDtoRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByAccountId(long accountId);
}
