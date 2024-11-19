package com.tripdisk.mvc.model.dto;

public class ImageFile {
	
	private String fileId;
	private int postId;
	private String fileName;
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "File [fileId=" + fileId + ", postId=" + postId + ", fileName=" + fileName + "]";
	}

}
