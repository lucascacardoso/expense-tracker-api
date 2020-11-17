package com.lucascaca.expensetracker.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.exceptions.EtAuthException;

public interface UserService {
	
	User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password, String authority) throws EtAuthException;    

}
