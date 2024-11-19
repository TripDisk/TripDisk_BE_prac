package com.tripdisk.mvc.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tripdisk.mvc.model.dao.PostDao;
import com.tripdisk.mvc.model.dto.ImageFile;
import com.tripdisk.mvc.model.dto.Post;
import com.tripdisk.mvc.model.dto.SearchCondition;

@Service
public class PostServiceImpl implements PostService {
	
	private final PostDao postDao;
	private final ResourceLoader resourceLoader;
	
	@Autowired
	public PostServiceImpl (PostDao postDao, ResourceLoader resourceLoader) {
		this.postDao = postDao;
		this.resourceLoader = resourceLoader;
	}
	
	// 1. 게시글 전체 조회 + 검색
	@Override
	public List<Post> getPostList(SearchCondition condition) {
		return postDao.selectAll(condition);
	}
	// 2. 게시글 상세 조회
	@Override
	public Post getPost(int postId) {
		return postDao.selectOne(postId);
	}
	// 3. 게시글 등록
	@Override
	public boolean writePost(Post post) {
		int result = postDao.insertPost(post);
		return result == 1;
	}
	// 4. 게시글 수정
	@Override
	public boolean modifyPost(Post post) {
		int result = postDao.updatePost(post);
		return result == 1;
	}
	// 5. 게시글 삭제
	@Override
	public boolean removePost(int postId) {
		int result = postDao.deletePost(postId);
		return result == 1;
	}
	// 6. 다중 이미지파일 업로드
	@Override
	public void imageFileUpload(List<MultipartFile> imageFiles, Post post) {
		if(imageFiles != null && imageFiles.size()>0) {
			
			try {
				
				// 파일들 저장할 경로(위치) 설정
				Resource resource = resourceLoader.getResource("classpath:/static/img"); 
				File uploadDir = resource.getFile(); // String uploadDir = resource.getFile().getAbsolutePath();
				
				// 파일 하나씩 처리
				List<ImageFile> list = new ArrayList<>();
				for(MultipartFile imageFile : imageFiles) {
					String fileName = imageFile.getOriginalFilename(); // 실제 파일 이름
					String fileId = UUID.randomUUID().toString(); // 고유 이름 생성
					
					// 파일 저장
					imageFile.transferTo(new File(uploadDir, fileId));  // fileId로 저장
					
					// 이미지파일 정보 저장 객체 생성
					ImageFile saveImagefile = new ImageFile(); 
					saveImagefile.setFileId(fileId);
					saveImagefile.setPostId(post.getPostId());
					saveImagefile.setFileName(fileName);
					
					list.add(saveImagefile);
				}
				
				// 파일 정보 DB에 저장
				postDao.insertImageFile(list);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	// 7. postId로 이미지 리스트 가져오기
	@Override
	public List<ImageFile> getPostImageFileList(int postId) {
		return postDao.selectImageFileByPostId(postId);
	}
	
	// 스케줄id로 게시글 조회
}
