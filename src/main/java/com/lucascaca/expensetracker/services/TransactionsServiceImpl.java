package com.lucascaca.expensetracker.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucascaca.expensetracker.domain.Categories;
import com.lucascaca.expensetracker.domain.Transactions;
import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.exceptions.EtBadRequestException;
import com.lucascaca.expensetracker.exceptions.EtResourceNotFoundException;
import com.lucascaca.expensetracker.repositories.CategoriesRepository;
import com.lucascaca.expensetracker.repositories.TransactionsRepository;
import com.lucascaca.expensetracker.repositories.UserRepository;

@Service
@Transactional
public class TransactionsServiceImpl implements TransactionsService {
	
	@Autowired
	TransactionsRepository transactionsRepository;
	
	@Autowired
	CategoriesRepository categoriesRepository;
	
	@Autowired
    UserRepository userRepository;

	@Override
	public List<Transactions> fetchAllTransactions(Long userId, Long categoryId) {

		return transactionsRepository.findAllByUserIdAndCategoryId(userId, categoryId);
	}

	@Override
	public Transactions fetchTransactionById(Long userId, Long categoryId, Long transactionId)
			throws EtResourceNotFoundException {

		return transactionsRepository.findByUserIdAndCategoryIdAndTransactionId(userId, categoryId, transactionId);
	}

	@Override
	public Transactions addTransaction(Long userId, Long categoryId, BigDecimal amount, String note,
			LocalDate transactionDate) throws EtBadRequestException {
		
		Transactions transaction = new Transactions();
		transaction.setAmount(amount);
		transaction.setNote(note);
		transaction.setTransactionDate(transactionDate);
		
		User user = userRepository.findById(userId).get();
		Categories category = categoriesRepository.findById(categoryId).get();
		
		transaction.setUser(user);
		transaction.setCategory(category);
		
		return transactionsRepository.save(transaction);
	}

	@Override
	public void updateTransaction(Long userId, Long categoryId, Long transactionId, BigDecimal amount, String note,
			LocalDate transactionDate) throws EtBadRequestException {

		Transactions transaction = transactionsRepository.findByUserIdAndCategoryIdAndTransactionId(userId, categoryId, transactionId);
		
		transaction.setAmount(amount);
		transaction.setNote(note);
		transaction.setTransactionDate(transactionDate);
		
		transactionsRepository.save(transaction);
	}

	@Override
	public void removeTransaction(Long userId, Long categoryId, Long transactionId)
			throws EtResourceNotFoundException {

		Transactions transaction = transactionsRepository.findByUserIdAndCategoryIdAndTransactionId(userId, categoryId, transactionId);
		
		transactionsRepository.delete(transaction);
	}

}
