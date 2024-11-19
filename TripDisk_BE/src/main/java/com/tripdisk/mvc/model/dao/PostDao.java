package com.tripdisk.mvc.model.dao;

import java.util.List;

import com.tripdisk.mvc.model.dto.ImageFile;
import com.tripdisk.mvc.model.dto.Post;

public interface PostDao {
	
	// 1. 게시글 전체 조회 + 검색
	public List<Post> selectAll();
	// 2. 게시글 상세 조회
	public Post selectOne(int postId);
	// 3. 게시글 등록
	public int insertPost(Post post);
	// 4. 게시글 수정
	public int updatePost(Post post);
	// 5. 게시글 삭제
	public int deletePost(int postId);
	// 6. 다중 이미지파일 업로드
	public void insertImageFile(List<ImageFile> imageFiles);
	// 7. postId로 이미지 리스트 가져오기
	public List<ImageFile> selectImageFileByPostId(int postId);
	// 스케줄id로 게시글 조회

}
