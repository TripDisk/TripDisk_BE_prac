package com.tripdisk.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripdisk.mvc.model.dto.Likes;
import com.tripdisk.mvc.model.service.LikesService;

@RestController
@RequestMapping("/api-likes")
@CrossOrigin("http://localhost:5173")
public class LikesRestController {
	
	private final LikesService likesService;
	
	@Autowired
	public LikesRestController(LikesService likesService) {
		this.likesService = likesService;
	}
	
	// 좋아요 등록
	@PostMapping("/add")
	public ResponseEntity<?> addLike(@RequestBody Likes likes) {
//		System.out.println("좋아요 등록 시작");
//		System.out.println("userId : " + likes.getUserId());
//		System.out.println("postId : " + likes.getPostId());
		boolean check = likesService.addLike(likes.getUserId(), likes.getPostId());
		
		if (check) {
			return new ResponseEntity<>("좋아요 등록 성공!", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("좋아요 등록 실패!", HttpStatus.BAD_REQUEST);
	}
	
	// 좋아요 취소
	@PostMapping("/delete")
	public ResponseEntity<?> deleteLike(@RequestBody Likes likes) {
//		System.out.println("좋아요 취소 시작");
		boolean check = likesService.deleteLike(likes.getUserId(), likes.getPostId());
		
		if (check) {
			return new ResponseEntity<>("좋아요 취소 성공!", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("좋아요 취소 실패!", HttpStatus.BAD_REQUEST);
	}
	
	// 좋아요 클릭 여부 검사
	@GetMapping("/check")
	public ResponseEntity<?> checkLike(Likes likes) {
//		System.out.println("좋아요 클릭 여부 검사 시작");
//		System.out.println(likes);
		boolean check = likesService.checkLike(likes.getUserId(), likes.getPostId());
		
		if (check) {
//			System.out.println(true + " : 좋아요 클릭함");
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
//		System.out.println(false + " : 좋아요 클릭 안함");
		return new ResponseEntity<>(false, HttpStatus.OK);
	}

}
