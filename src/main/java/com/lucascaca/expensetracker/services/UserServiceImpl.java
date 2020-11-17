package com.lucascaca.expensetracker.services;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.exceptions.EtAuthException;
import com.lucascaca.expensetracker.repositories.UserRepository;
import com.lucascaca.expensetracker.security.CustomUserDetailsService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
    PasswordEncoder passwordEncoder;

	@Override
	public User validateUser(String email, String password) throws EtAuthException {
				
		if(email != null) {
			email = email.toLowerCase();
		}
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(email, password)
			);
		}
		catch (BadCredentialsException e) {
			throw new EtAuthException("Invalid email/password");
		}
		
		return (User) customUserDetailsService.loadUserByUsername(email);				

	}

	@Override
	public User registerUser(String firstName, String lastName, String email, String password, String authority) throws EtAuthException {
				
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		
		if(email != null) {
			email = email.toLowerCase();
		}
						
		if(!pattern.matcher(email).matches()) {
			throw new EtAuthException("Invalid email format");
		}
		
		Long count = userRepository.countByEmail(email);
		
		if(count > 0) {
			throw new EtAuthException("Email already in use");
		}
		
	
		User user = User.builder()
					    .email(email)
					    .password(this.passwordEncoder.encode(password))
					    .firstName(firstName)
					    .lastName(lastName)
					    .authorities(Arrays.asList(authority))
					    .build();
		
		return userRepository.save(user);	
		
	}
}
