package com.tripdisk.mvc.model.dto;

import java.util.List;

public class Post {
	private int postId;
	private int userId;
	private int scheduleId;
	private String place;
	private String date;
	private String title;
	private String content;
	private String createdAt;
	private String updatedAt;
	private boolean isShared;
	private List<ImageFile> imageFiles;
	private int likesCount;
	private boolean isLiked;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public boolean getIsShared() {
		return isShared;
	}
	public void setIsShared(boolean isShared) {
		this.isShared = isShared;
	}
	
	public List<ImageFile> getImageFiles() {
		return imageFiles;
	}
	public void setFiles(List<ImageFile> imageFiles) {
		this.imageFiles = imageFiles;
	}
	public int getLikesCount() {
		return likesCount;
	}
	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}
	
	public boolean getIsLiked() {
		return isLiked;
	}
	public void setIsLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	@Override
	public String toString() {
		return "Post [postId=" + postId + ", userId=" + userId + ", scheduleId=" + scheduleId + ", place=" + place
				+ ", date=" + date + ", title=" + title + ", content=" + content + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", isShared=" + isShared + ", imageFiles=" + imageFiles + ", likesCount="
				+ likesCount + ", isLiked=" + isLiked + "]";
	}
	
	
	
}
