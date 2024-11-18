package com.tripdisk.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripdisk.mvc.model.dto.User;
import com.tripdisk.mvc.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	private final UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody User user) {
		
		boolean check = userService.addUser(user);
		
		if (check) {
			return new ResponseEntity<String> ("회원가입 완료!", HttpStatus.CREATED);
		}
		return new ResponseEntity<String> ("회원가입 실패!", HttpStatus.BAD_REQUEST);
	}
	

}
