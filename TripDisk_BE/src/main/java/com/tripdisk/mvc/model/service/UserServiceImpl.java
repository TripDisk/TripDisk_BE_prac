package com.tripdisk.mvc.model.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tripdisk.mvc.model.dao.UserDao;
import com.tripdisk.mvc.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
//	// 회원가입
//	@Override
//	public boolean addUser(User user) {
////		System.out.println(user);
//		int result = userDao.insertUser(user);
//		if (result == 1) return true;
//		return false;
//	}
	
	// 회원가입
	@Override
	public boolean addUser(String username, String email, String password, MultipartFile profileImg) {
		try {
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			
			if (profileImg != null && !profileImg.isEmpty()) {
				String fileName = UUID.randomUUID() + "_" + profileImg.getOriginalFilename();
	            Path imagePath = Paths.get("uploads/profile/" + fileName); // 이미지 저장 경로
	            Files.copy(profileImg.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
	            user.setProfileImg(fileName); // 저장된 파일 이름을 DB에 저장
			}
			
			int result = userDao.insertUser(user);
			return result == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
