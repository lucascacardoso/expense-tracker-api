package com.lucascaca.expensetracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucascaca.expensetracker.domain.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long>{
	
	@Query("select t from Transactions t join t.category c "
			+ "where c = (select c from Categories c join c.user u where u.userId = :userId) "
			+ "and c.categoryId = :categoryId")
	List<Transactions> findAllByUserIdAndCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);
	
	@Query("select t from Transactions t join t.category c "
			+ "where c = (select c from Categories c join c.user u where u.userId = :userId) "
			+ "and c.categoryId = :categoryId "
			+ "and t.transactionId = :transactionId")
	Transactions findByUserIdAndCategoryIdAndTransactionId(@Param("userId") Long userId, @Param("categoryId") Long categoryId, @Param("transactionId") Long transactionId);

}
