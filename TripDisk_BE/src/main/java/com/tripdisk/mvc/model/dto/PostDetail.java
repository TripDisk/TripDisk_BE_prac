package com.tripdisk.mvc.model.dto;

import java.util.List;

public class PostDetail {
	private Post post;
	private List<ImageFile> files;
	
	public PostDetail() {
	}
	
	public PostDetail(Post post, List<ImageFile> files) {
		super();
		this.post = post;
		this.files = files;
	}
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public List<ImageFile> getFiles() {
		return files;
	}
	public void setFiles(List<ImageFile> files) {
		this.files = files;
	}
	@Override
	public String toString() {
		return "PostDetail [post=" + post + ", files=" + files + "]";
	}
	

}
