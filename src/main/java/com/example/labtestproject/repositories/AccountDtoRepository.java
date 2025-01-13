package com.example.labtestproject.repositories;

import com.example.labtestproject.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDtoRepository extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT MAX(a.id) FROM AccountEntity a")
    Optional<Integer> findMaxId();
}
