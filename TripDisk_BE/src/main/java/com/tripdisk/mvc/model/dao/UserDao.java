package com.tripdisk.mvc.model.dao;

import org.apache.ibatis.annotations.Param;

import com.tripdisk.mvc.model.dto.User;

public interface UserDao {
	
	// 회원가입
	public int insertUser(User user);

	// 로그인
	public User findUser(@Param("email") String email, @Param("password") String password);
	
	// 회원 탈퇴
	public int deleteUser(int userId);
	
	// 회원 정보 수정
	public int updateUser(User user);

}
