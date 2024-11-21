package com.tripdisk.mvc.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tripdisk.mvc.model.dto.ImageFile;
import com.tripdisk.mvc.model.dto.Post;

public interface PostService {
	
	// 1. 게시글 전체 조회 + 검색
	public List<Post> getPostList(HashMap<String, Object> param);
	// 2. 게시글 상세 조회
	public Post getPost(int postId);
	// 3. 게시글 등록
	public boolean writePost(Post post);
	// 4. 게시글 수정
	public boolean modifyPost(Post post);
	// 5. 게시글 삭제
	public boolean removePost(int postId);
	// 6. 다중 이미지파일 업로드
	public void imageFileUpload(List<MultipartFile> imageFiles, Post post);
	// 7. postId로 이미지 리스트 가져오기
	public List<ImageFile> getPostImageFileList(int postId);
	// 8. 스케줄id로 게시글 조회
	public List<Post> getPostByScheduleId(int scheduleId);

}
