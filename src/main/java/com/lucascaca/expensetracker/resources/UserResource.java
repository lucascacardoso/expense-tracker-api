package com.lucascaca.expensetracker.resources;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucascaca.expensetracker.domain.User;
import com.lucascaca.expensetracker.security.jwt.JwtUtil;
import com.lucascaca.expensetracker.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
		String email = (String) userMap.get("email");
		String password = (String) userMap.get("password");
		
		User user = userService.validateUser(email, password);		
				
		return new ResponseEntity<>(jwtTokenUtil.generateJWTToken(user), HttpStatus.OK);
	}	

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
		String firstName = (String) userMap.get("firstName");
		String lastName = (String) userMap.get("lastName");
		String email = (String) userMap.get("email");
		String password = (String) userMap.get("password");
		String authority = (String) userMap.get("authority");
		
		User user = userService.registerUser(firstName, lastName, email, password, authority);		
		return new ResponseEntity<>(jwtTokenUtil.generateJWTToken(user), HttpStatus.OK);
	}	
	

}
