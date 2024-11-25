package com.tripdisk.mvc.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tripdisk.mvc.model.dao.PostDao;
import com.tripdisk.mvc.model.dto.ImageFile;
import com.tripdisk.mvc.model.dto.Post;

@Service
public class PostServiceImpl implements PostService {

	private final PostDao postDao;
	private final ResourceLoader resourceLoader;

	@Autowired
	public PostServiceImpl(PostDao postDao, ResourceLoader resourceLoader) {
		this.postDao = postDao;
		this.resourceLoader = resourceLoader;
	}

	// 1. 게시글 전체 조회 + 검색
	@Override
	public List<Post> getPostList(HashMap<String, Object> param) {
		return postDao.selectAll(param);
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
		if (imageFiles != null && imageFiles.size() > 0) {

			try {

				// 파일들 저장할 경로(위치) 설정
//				Resource resource = resourceLoader.getResource("classpath:/static/img"); 
//				File uploadDir = resource.getFile(); 

				Path uploadDir = Paths.get("src/../target/classes/static/img/");
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir); // 폴더가 없으면 생성
				}

//				// 기존 이미지 삭제
//				List<ImageFile> existingFiles = postDao.selectImageFileByPostId(post.getPostId());
//				for (ImageFile existingFile : existingFiles) {
//					// 파일 시스템에서 삭제
//					Path filePath = uploadDir.resolve(existingFile.getFileName());
//					Files.deleteIfExists(filePath);
//				}
//				// DB에서 기존 파일 정보 삭제
//				postDao.deleteImageFilesByPostId(post.getPostId());

				// 새로운 파일 하나씩 처리
				List<ImageFile> list = new ArrayList<>();
				for (MultipartFile imageFile : imageFiles) {
					String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename(); // 고유 이름 생성
					String fileId = "/img/" + fileName; // 경로

					// 파일 저장
//					imageFile.transferTo(new File(uploadDir, fileId)); // fileId로 저장
					Path filePath = uploadDir.resolve(fileName); // 파일 저장 경로
//                  imageFile.transferTo(filePath.toFile()); // 파일을 지정된 경로에 저장
					Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

					// 이미지파일 정보 저장 객체 생성
					ImageFile saveImagefile = new ImageFile();
					saveImagefile.setFileId(fileId);
					saveImagefile.setPostId(post.getPostId());
					saveImagefile.setFileName(fileName);

					System.out.println(fileId);
					System.out.println(fileName);
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

	// 8. 스케줄id로 게시글 조회
	@Override
	public List<Post> getPostByScheduleId(int scheduleId) {
		return postDao.selectPostsByScheduleId(scheduleId);
	}

	// 9. fileName으로 기존 이미지 삭제
	@Override
	public void deleteImageFiles(List<String> fileNames) {
		
		try {
			Path uploadDir = Paths.get("src/../target/classes/static/img/");

			for (String fileName : fileNames) {
				// 파일 시스템에서 삭제
				Path filePath = uploadDir.resolve(fileName);
				Files.deleteIfExists(filePath);

				// DB에서 삭제
				postDao.deleteImageFileByFileName(fileName);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 10. 좋아요 카운트 증가
	@Override
	public boolean countUpLikes(int postId) {
		int result = postDao.countUpLikes(postId);
		return result == 1;
	}

	// 11. 좋아요 카운트 감소
	@Override
	public boolean countDownLikes(int postId) {
		int result = postDao.countDownLikes(postId);
		return result == 1;
	}

}
