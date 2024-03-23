package com.example.labtestproject.repositories;

import com.example.labtestproject.dto.AccountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDtoRepository extends JpaRepository<AccountDto, Long> {

    @Query("SELECT MAX(a.id) FROM AccountDto a")
    Optional<Integer> findMaxId();
}
