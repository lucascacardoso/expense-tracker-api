package com.lucascaca.expensetracker.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.lucascaca.expensetracker.domain.Transactions;
import com.lucascaca.expensetracker.exceptions.EtBadRequestException;
import com.lucascaca.expensetracker.exceptions.EtResourceNotFoundException;

public interface TransactionsService {
	
	List<Transactions> fetchAllTransactions(Long userId, Long categoryId);

    Transactions fetchTransactionById(Long userId, Long categoryId, Long transactionId) throws EtResourceNotFoundException;

    Transactions addTransaction(Long userId, Long categoryId, BigDecimal amount, String note, LocalDate transactionDate) throws EtBadRequestException;

    void updateTransaction(Long userId, Long categoryId, Long transactionId, BigDecimal amount, String note, LocalDate transactionDate) throws EtBadRequestException;

    void removeTransaction(Long userId, Long categoryId, Long transactionId) throws EtResourceNotFoundException;

}
