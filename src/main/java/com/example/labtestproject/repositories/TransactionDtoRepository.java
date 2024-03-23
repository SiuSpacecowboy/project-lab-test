package com.example.labtestproject.repositories;

import com.example.labtestproject.dto.TransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDtoRepository extends JpaRepository<TransactionDto, Long> {
    List<TransactionDto> findByAccountId(long accountId);
}
