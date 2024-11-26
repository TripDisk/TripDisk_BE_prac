package com.tripdisk.mvc.model.dao;

import java.util.HashMap;
import java.util.List;

import com.tripdisk.mvc.model.dto.ImageFile;
import com.tripdisk.mvc.model.dto.Post;

public interface PostDao {

	// 0. 사용자 무관 공유 게시글 조회
	public List<Post> selectShared();
	// 1. 게시글 전체 조회 + 검색
	public List<Post> selectAll(HashMap<String, Object> param);
	// mapper에 넘길 parameterType이 여러개일 때 @Param 사용
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
	// 8. 스케줄id로 게시글 조회
	public List<Post> selectPostsByScheduleId(int scheduleId);
	// 9. 기존 이미지 삭제
	public void deleteImageFilesByPostId(int postId);
	// 10. fileName으로 이미지 삭제
	public void deleteImageFileByFileName(String fileName);
	// 11. 좋아요 카운트 증가
	public int countUpLikes(int postId);
	// 12. 좋아요 카운트 감소
	public int countDownLikes(int postId);
	// 13. 내 게시물 좋아요 업데이트
	public int checkMyLike(int userId, int postId);

}
