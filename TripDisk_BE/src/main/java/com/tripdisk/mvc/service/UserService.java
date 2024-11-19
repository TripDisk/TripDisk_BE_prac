package com.tripdisk.mvc.service;

import com.tripdisk.mvc.model.dto.User;

public interface UserService {
	
	// 회원가입
	boolean addUser(User user);
	
	// 로그인
	User checkUser(String email, String password);
	
	// 회원 탈퇴
	boolean removeUser(int userId);
	
	

}
