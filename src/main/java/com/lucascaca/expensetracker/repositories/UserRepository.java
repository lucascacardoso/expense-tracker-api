package com.lucascaca.expensetracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.exceptions.EtAuthException;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Long countByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findById(Long userId);

}
