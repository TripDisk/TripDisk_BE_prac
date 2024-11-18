package com.tripdisk.mvc.model.dto;

public class User {
	private int userId;
	private String username;
	private String password;
	private String email;
	private String profileImg;
	private String refreshToken;
	private SocialType socialType;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getProfileImg() {
		return profileImg;
	}
	
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public SocialType getSocialType() {
		return socialType;
	}
	
	public void setSocialType(SocialType socialType) {
		this.socialType = socialType;
	}
}
