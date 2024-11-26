package com.tripdisk.mvc.model.service;

public interface LikesService {
	
	// 좋아요 등록
	public boolean addLike(int userId, int postId);
	
	// 좋아요 취소
	public boolean deleteLike(int userId, int postId);
	
	// 클릭 여부 검사
	public boolean checkLike(int userId, int postId);

}
