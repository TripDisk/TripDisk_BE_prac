package com.tripdisk.mvc.service;

import com.tripdisk.mvc.model.dao.UserDao;
import com.tripdisk.mvc.model.dto.User;

public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean addUser(User user) {
		int result = userDao.insertUser(user);
		if (result == 1) return true;
		return false;
	}

}
