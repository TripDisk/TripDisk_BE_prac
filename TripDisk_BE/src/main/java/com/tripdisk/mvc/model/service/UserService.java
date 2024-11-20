package com.tripdisk.mvc.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.tripdisk.mvc.model.dto.User;

public interface UserService {
	
//	// 회원가입
//	boolean addUser(User user);
	
	// 회원가입
	boolean addUser(String username, String email, String password, MultipartFile profileImg);

	// 로그인
	User checkUser(String email, String password);
	
	// 회원 탈퇴
	boolean removeUser(int userId);

	
	

}
