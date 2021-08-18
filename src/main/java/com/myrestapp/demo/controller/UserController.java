package com.myrestapp.demo.controller;

import java.security.Principal;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myrestapp.demo.model.User;
import com.myrestapp.demo.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:59279", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/sign-up")
	public void postUser(@RequestBody User user) {
		String code = user.getUsername();
		String username = new String(Base64.getDecoder().decode(code)).split(":")[0];
		String password = new String(Base64.getDecoder().decode(code)).split(":")[1];
		user.setUsername(username);
		user.setPassword(password);
		String encPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encPassword);
		userRepository.save(user);
	}
	
	@GetMapping("/login")
	public Principal login(Principal p) {
		if (p.getName() == null) {
			throw new Error("Invalid Credentials");
		}
		return p;
	}
}
