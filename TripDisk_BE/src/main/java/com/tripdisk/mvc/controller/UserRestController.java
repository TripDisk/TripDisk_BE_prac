package com.tripdisk.mvc.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripdisk.mvc.model.dto.LoginRequest;
import com.tripdisk.mvc.model.dto.User;
import com.tripdisk.mvc.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:5173")
public class UserRestController {
	
	private final UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
		
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		System.out.println("Email: " + email);
	    System.out.println("Password: " + password);
		
		User user = userService.checkUser(email, password);
	    
		if (user != null) {
			session.setAttribute("user", user);
			return new ResponseEntity<>(user.getUsername() + "님 반갑습니다!", HttpStatus.OK);
		}
		return new ResponseEntity<>("이메일 또는 패스워드가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
	}
	
	
	// 회원가입
	@PostMapping("signup")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		
		boolean check = userService.addUser(user);
		
		if (check) {
			return new ResponseEntity<String> ("회원가입 완료!", HttpStatus.CREATED);
		}
		return new ResponseEntity<String> ("회원가입 실패!", HttpStatus.BAD_REQUEST);
	}
	
	// 회원 탈퇴
	@DeleteMapping("signout")
	public ResponseEntity<String> removeUser(HttpSession session) {
		User user = (User)session.getAttribute("user");
		int userId = user.getUserId();
		boolean check = userService.removeUser(userId);
		
		if (check) {
			return new ResponseEntity<String> ("회원 탈퇴 성공!", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String> ("회원 탈퇴 실패!", HttpStatus.BAD_REQUEST);
	}
	
	// 로그아웃
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "로그아웃 되었습니다.";
	}
	
	// 세션 확인
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	if (user != null) {
    		return new ResponseEntity<> (user, HttpStatus.OK);
    	}
    	return new ResponseEntity<> (null, HttpStatus.BAD_REQUEST);
    }

}
