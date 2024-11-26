package com.tripdisk.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripdisk.mvc.model.dto.ImageFile;
import com.tripdisk.mvc.model.dto.Post;
import com.tripdisk.mvc.model.dto.SearchCondition;
import com.tripdisk.mvc.model.dto.User;
import com.tripdisk.mvc.model.service.PostService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/post")
@CrossOrigin("http://localhost:5173")
public class PostRestController {

	private final PostService postService;

	@Autowired
	public PostRestController(PostService postService) {
		this.postService = postService;
	}

	// 0. 사용자 무관 공유 게시글 조회
	@GetMapping("/shared")
	public ResponseEntity<List<Post>> shared(@ModelAttribute SearchCondition condition, HttpSession session) {
		// 로그인 사용자 조회 (로그인 만료 시 처리)
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<Post> list = postService.getSharedPosts(user.getUserId());
		if (list == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 1. 사용자 전용 게시글 전체 조회 + 검색
	@GetMapping("")
	public ResponseEntity<List<Post>> list(@ModelAttribute SearchCondition condition, HttpSession session) {
		// 로그인 사용자 조회 (로그인 만료 시 처리)
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		int userId = user.getUserId();
		HashMap<String, Object> param = new HashMap<>();
		param.put("condition", condition);
		param.put("userId", userId);
		List<Post> list = postService.getPostList(param);
		if (list == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		System.out.println("list 여기1 : " + list);

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 2. 게시글 상세 조회
	@GetMapping("/{postId}")
	public ResponseEntity<Post> detail(@PathVariable("postId") int postId, HttpSession session) {
		// 로그인 사용자 조회 (로그인 만료 시 처리 401 - 인증x)
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		// 게시글 조회 (일정 존재X 처리)
		Post post = postService.getPost(user.getUserId(), postId);
		if (post == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		// 작성자 검증 (로그인 사용자가 아닌 사용자가 url로 접근했을 경우 처리 403 - 인가x)
		if (post.getUserId() != user.getUserId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		List<ImageFile> imageFile = postService.getPostImageFileList(postId);
		post.setFiles(imageFile);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}
	// 상세 조회 시 게시글, 이미지를 동시에 갖고 오기 위해 PostDetail (Dto)를 추가적으로 만들자

	// 3. 게시글 등록
	@PostMapping("")
	public ResponseEntity<?> write(@RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
			@RequestPart Post post, HttpSession session) { // ======> 나중에 @RequestPart로 수정

		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();
		post.setUserId(userId);
		boolean isWritten = postService.writePost(post);

		if (isWritten) {
			postService.imageFileUpload(imageFiles, post); // postId만 보내도 되나?
			int postId = post.getPostId();
			Map<String, Object> response = new HashMap<>();
			response.put("message", "게시글 등록에 성공했습니다.");
			response.put("postId", postId);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("message", "게시글 등록에 실패했습니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

	}
	// @RequestParam과 @RequestBody는 서로 다른 방식으로 데이터를 처리한다.
	// 따라서 이미지, dto를 둘 다 받을 때 RequestPart 사용하는게 좋다.
	// requestpart : 파일 업로드와 다른 데이터(ex. json)를 함께 처리할 수 있게 해주는 방법
	// required는 기본값이 true라서 이미지가 null이려면 false로 명시해줘야함.

	// 4. 게시글 수정
	@PatchMapping("/{postId}")
	public ResponseEntity<String> update(
			@RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
			@RequestPart(value = "fileNames", required = false) String fileNames, @RequestPart Post post,
			@PathVariable("postId") int postId, HttpSession session) {
		post.setPostId(postId);
		User user = (User) session.getAttribute("user");
		post.setUserId(user.getUserId());

		boolean isUpdated = postService.modifyPost(post);
		if (isUpdated) {
			if (fileNames != null) {
				List<String> removedFiles;
				try {
					removedFiles = new ObjectMapper().readValue(fileNames, List.class);
					postService.deleteImageFiles(removedFiles);
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			postService.imageFileUpload(imageFiles, post);
			return ResponseEntity.status(HttpStatus.OK).body("게시글 수정에 성공했습니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 수정에 실패했습니다.");
	}

	// 5. 게시글 삭제
	@DeleteMapping("/{postId}")
	public ResponseEntity<String> delete(@PathVariable("postId") int postId) {
		boolean isDeleted = postService.removePost(postId);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제에 성공했습니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 삭제에 실패했습니다.");
	}

	// 8. 스케줄id로 게시글 조회
	@GetMapping("/{scheduleId}/post")
	public ResponseEntity<List<Post>> postlist(@PathVariable("scheduleId") int scheduleId, HttpSession session) {
		// 로그인 사용자 조회 (로그인 만료 시 처리 401 - 인증x)
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		// 게시글 조회 (일정 존재X 처리)
		List<Post> list = postService.getPostByScheduleId(user.getUserId(), scheduleId);
		if (list == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} else if (list.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}
