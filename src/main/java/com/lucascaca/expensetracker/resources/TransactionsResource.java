package com.lucascaca.expensetracker.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucascaca.expensetracker.domain.Transactions;
import com.lucascaca.expensetracker.services.TransactionsService;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionsResource {
	
	@Autowired
    TransactionsService transactionsService;
	
    @GetMapping("")
    public ResponseEntity<List<Transactions>> getAllTransactions(HttpServletRequest request,
                                                                @PathVariable("categoryId") Long categoryId) {
    	
    	Long userId = (Long) request.getAttribute("userId");
        List<Transactions> transactions = transactionsService.fetchAllTransactions(userId, categoryId);
        
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transactions> getTransactionById(HttpServletRequest request,
                                                          @PathVariable("categoryId") Long categoryId,
                                                          @PathVariable("transactionId") Long transactionId) {
    	
    	Long userId = (Long) request.getAttribute("userId");
        Transactions transaction = transactionsService.fetchTransactionById(userId, categoryId, transactionId);
        
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
	
	@PostMapping("")
    public ResponseEntity<Transactions> addTransaction(HttpServletRequest request,
                                                      @PathVariable("categoryId") Long categoryId,
                                                      @RequestBody Map<String, Object> transactionMap) {
		
        Long userId = (Long) request.getAttribute("userId");
        BigDecimal amount = new BigDecimal(transactionMap.get("amount").toString());
        String note = (String) transactionMap.get("note");
        LocalDate transactionDate = LocalDate.parse(
        		transactionMap.get("transactionDate").toString(), 
        		DateTimeFormatter.ofPattern("dd/MM/yyyy")
        		); 		
        		
        Transactions transaction = transactionsService.addTransaction(userId, categoryId, amount, note, transactionDate);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
	
	@PutMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Long categoryId,
                                                                  @PathVariable("transactionId") Long transactionId,
                                                                  @RequestBody Map<String, Object> transactionMap) {
		
		Long userId = (Long) request.getAttribute("userId");
		
		BigDecimal amount = new BigDecimal(transactionMap.get("amount").toString());
        String note = (String) transactionMap.get("note");
        LocalDate transactionDate = LocalDate.parse(
        		transactionMap.get("transactionDate").toString(), 
        		DateTimeFormatter.ofPattern("dd/MM/yyyy")
        		);
		
        transactionsService.updateTransaction(userId, categoryId, transactionId, amount, note, transactionDate);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
	
	@DeleteMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Long categoryId,
                                                                  @PathVariable("transactionId") Long transactionId) {
		Long userId = (Long) request.getAttribute("userId");
        transactionsService.removeTransaction(userId, categoryId, transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
