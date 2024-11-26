package com.tripdisk.mvc.model.dto;

public class Likes {
	private int likeId;
	private int userId;
	private int postId;
	
	public int getLikeId() {
		return likeId;
	}
	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	@Override
	public String toString() {
		return "Likes [likeId=" + likeId + ", userId=" + userId + ", postId=" + postId + "]";
	}

	
	
}
