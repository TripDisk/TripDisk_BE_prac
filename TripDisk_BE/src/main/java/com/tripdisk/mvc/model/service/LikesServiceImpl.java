package com.tripdisk.mvc.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripdisk.mvc.model.dao.LikesDao;

@Service
public class LikesServiceImpl implements LikesService {
	
	private final LikesDao likesDao;
	
	@Autowired
	public LikesServiceImpl(LikesDao likesDao) {
		this.likesDao = likesDao;
	}
	
	// 좋아요 등록
	@Override
	public boolean addLike( int userId, int postId) {
		int result = likesDao.insertLike(userId, postId);
		return result == 1;
	}
	
	// 좋아요 취소
	@Override
	public boolean deleteLike(int userId, int postId) {
		int result = likesDao.deleteLike(userId, postId);
		return result == 1;
	}

	// 클릭 여부 검사
	@Override
	public boolean checkLike(int userId, int postId) {
		return likesDao.checkLike(userId, postId) != null;
	}

}
