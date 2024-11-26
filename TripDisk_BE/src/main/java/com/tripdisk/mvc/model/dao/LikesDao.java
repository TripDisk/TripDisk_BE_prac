package com.tripdisk.mvc.model.dao;

import com.tripdisk.mvc.model.dto.Likes;

public interface LikesDao {
	
	// 좋아요 등록
	public int insertLike(int userId, int postId);
	
	// 좋아요 취소
	public int deleteLike(int userId, int postId);

	// 클릭 여부 검사
	public Likes checkLike(int userId, int postId);
}
