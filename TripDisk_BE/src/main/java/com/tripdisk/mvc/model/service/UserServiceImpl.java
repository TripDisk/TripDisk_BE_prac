package com.tripdisk.mvc.model.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tripdisk.mvc.model.dao.UserDao;
import com.tripdisk.mvc.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserDao userDao;
//	private final ResourceLoader resourceLoader;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
//		this.resourceLoader = resourceLoader;
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
				System.out.println("프로필 이미지 존재");
				
				// 파일들 저장할 경로(위치) 설정
//				Resource resource = resourceLoader.getResource("classpath:/static/img"); 
				Path uploadDir = Paths.get("src/../target/classes/static/img/");
	            if (!Files.exists(uploadDir)) {
	                Files.createDirectories(uploadDir); // 디렉토리 생성
	                System.out.println("static/img 디렉토리 생성 완료: " + uploadDir);
	            }
				
				// 파일 저장
				String fileName = UUID.randomUUID() + "_" + profileImg.getOriginalFilename();
				System.out.println("fileName : " + fileName);
//	            Path imagePath = Paths.get("static/img/" + fileName); // 이미지 저장 경로
				Path imagePath = uploadDir.resolve(fileName);
	            System.out.println("imagePath : " + imagePath);
	            Files.copy(profileImg.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("파일 복사 완료");
	            
	            // 저장된 파일 이름을 DB에 저장	
	            user.setProfileImg(fileName); 
			}
			System.out.println(user.getProfileImg());
			
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

	// 회원 정보 수정
	@Override
	public boolean updateUser(int userId, String username, String email, String password, MultipartFile profileImg) {
		try {
			User user = new User();
			user.setUserId(userId);
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			
			System.out.println(user.getUserId() + " " + user.getUsername() + " " + user.getPassword());
			
			if (profileImg != null && !profileImg.isEmpty()) {
				System.out.println("프로필 이미지 존재");
				
				// 파일들 저장할 경로(위치) 설정
//				Resource resource = resourceLoader.getResource("classpath:/static/img"); 
				Path uploadDir = Paths.get("src/../target/classes/static/img/");
	            if (!Files.exists(uploadDir)) {
	                Files.createDirectories(uploadDir); // 디렉토리 생성
	                System.out.println("static/img 디렉토리 생성 완료: " + uploadDir);
	            }
				
				// 파일 저장
				String fileName = UUID.randomUUID() + "_" + profileImg.getOriginalFilename();
				System.out.println("fileName : " + fileName);
				Path imagePath = uploadDir.resolve(fileName);
	            System.out.println("imagePath : " + imagePath);
	            Files.copy(profileImg.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("파일 복사 완료");
	            
	            // 저장된 파일 이름을 DB에 저장	
	            user.setProfileImg(fileName); 
			}
			System.out.println(user.getProfileImg());
			
			int result = userDao.updateUser(user);
			return result == 1;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	


}
