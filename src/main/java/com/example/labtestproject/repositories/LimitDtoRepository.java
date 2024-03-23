package com.example.labtestproject.repositories;

import com.example.labtestproject.dto.LimitDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimitDtoRepository extends JpaRepository<LimitDto, Long> {

    List<LimitDto> findByAccountId(long accountId);

    @Transactional
    @Modifying
    @Query("update LimitDto l set l.limitRem = ?1 where l.accountId = ?2")
    void updateLimitRemById(double limitRem, long id);

    @Query("SELECT l FROM LimitDto l WHERE l.accountId = ?1 AND l.expenseCategory = ?2 ORDER BY l.limitDatetime DESC LIMIT 1")
    Optional<LimitDto> findTopByAccIdAndCategoryOrdByIdDesc(long accId, String category);

   @Query(value = "SELECT " +
           "tr.account_from, tr.account_to, tr.currency_shortname, tr.sum, " +
           "tr.expense_category AS expense_category, " +
           "l.limit_sum, l.limit_datetime, l.limit_currency_shortname, f.flag " +
           "FROM transactions AS tr " +
           "JOIN flags AS f ON f.trans_id = tr.id " +
           "JOIN limits AS l ON tr.account_id = l.account_id " +
           "WHERE f.flag = 'true' and tr.account_id = ?1 " +
           "AND l.limit_datetime = (SELECT MAX(limit_datetime) FROM limits " +
           "WHERE account_id = ?1 AND limit_datetime <= tr.datetime)", nativeQuery = true)
    List<Object[]> findTransactionsWithFlagsAndLimits(long id);
}
