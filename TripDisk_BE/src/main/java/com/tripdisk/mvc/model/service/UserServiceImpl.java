package com.tripdisk.mvc.model.service;

import org.springframework.stereotype.Service;

import com.tripdisk.mvc.model.dao.UserDao;
import com.tripdisk.mvc.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	// 회원가입
	@Override
	public boolean addUser(User user) {
//		System.out.println(user);
		int result = userDao.insertUser(user);
		if (result == 1) return true;
		return false;
	}

	// 로그인
	@Override
	public User checkUser(String email, String password) {
		User result = userDao.findUser(email, password);
		if (result != null) {
			return result;
		}
		return null;
	}
	
	// 회원 탈퇴
	@Override
	public boolean removeUser(int userId) {
		int result = userDao.deleteUser(userId);
		if (result == 1) return true;
		return false;
	}

}
