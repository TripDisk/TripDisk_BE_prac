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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripdisk.mvc.model.dto.Schedule;
import com.tripdisk.mvc.model.dto.User;
import com.tripdisk.mvc.model.service.ScheduleService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin("http://localhost:5173")
public class ScheduleRestController {

	// 서비스 의존성 주입
	private final ScheduleService scheduleService;

	@Autowired
	public ScheduleRestController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	// 우선 사용자 구분 없이 CRUD
	// 1. 일정 전체 조회 - 검색정렬 나중에 구현
	@GetMapping("")
	public ResponseEntity<List<Schedule>> list(HttpSession session) { // 로컬스토리지나 쿠키나 토큰에서 꺼낸 id (클라이언트가 서버한테)
		// 로그인 사용자 조회 (로그인 만료 시 처리)
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		int userId = user.getUserId();
		List<Schedule> list = scheduleService.getScheduleList(userId);
		if (list == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		else if (list.size() == 0)
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 2. 일정 상세 조회
	@GetMapping("/{scheduleId}")
	public ResponseEntity<Schedule> detail(@PathVariable("scheduleId") int scheduleId, HttpSession session) {
		// 로그인 사용자 조회 (로그인 만료 시 처리 401 - 인증x)
		User user = (User) session.getAttribute("user");
		if (user == null) {
			System.out.println("user null : " + user);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		// 일정 조회 (일정 존재X 처리)
		Schedule schedule = scheduleService.getSchedule(scheduleId);
		if (schedule == null) {
			return ResponseEntity.notFound().build();
		}
		// 작성자 검증 (로그인 사용자가 아닌 사용자가 url로 접근했을 경우 처리 403 - 인가x)
		if (schedule.getUserId() != user.getUserId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(schedule);
	}

	// 3. 일정 등록
	// schedule 반환
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> write(@RequestBody Schedule schedule, HttpSession session) {
		// 세션에서 가져온 userId
		User user = (User) session.getAttribute("user");
		int userId = user.getUserId();
		schedule.setUserId(userId);
		boolean isWritten = scheduleService.writeSchedule(schedule);
		if (isWritten) {
			int scheduleId = schedule.getScheduleId(); // Schedule 객체에 자동 생성된 ID가 세팅되어야 함
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "일정 등록에 성공했습니다.");
	        response.put("scheduleId", scheduleId);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		Map<String, Object> errorResponse = new HashMap<>();
	    errorResponse.put("message", "일정 등록에 실패했습니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	// 4. 일정 수정
	@PutMapping("/{scheduleId}")
	public ResponseEntity<?> update(@PathVariable("scheduleId") int scheduleId, @RequestBody Schedule schedule) {
		schedule.setScheduleId(scheduleId); // 아니면 프론트에서 hidden으로 이미 schedule에 넣어서 보내면 url변수로 안보내도 될듯
		boolean isUpdated = scheduleService.modifySchedule(schedule);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body("일정 수정에 성공했습니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일정 수정에 실패했습니다.");
	}

	// 5. 일정 삭제
	@DeleteMapping("/{scheduleId}")
	public ResponseEntity<?> remove(@PathVariable("scheduleId") int scheduleId) {
		boolean isDeleted = scheduleService.removeSchedule(scheduleId);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("일정 삭제에 성공했습니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일정 삭제에 실패했습니다.");
	}

}
