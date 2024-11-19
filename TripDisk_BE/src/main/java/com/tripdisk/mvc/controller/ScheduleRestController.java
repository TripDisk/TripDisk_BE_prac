package com.tripdisk.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripdisk.mvc.model.dto.Schedule;
import com.tripdisk.mvc.model.service.ScheduleService;

@RestController
@RequestMapping("/api-schedule")
public class ScheduleRestController {

	// 서비스 의존성 주입
	private final ScheduleService scheduleService;

	@Autowired
	public ScheduleRestController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	// 우선 사용자 구분 없이 CRUD
	// 1. 일정 전체 조회 - 검색정렬 나중에 구현
	@GetMapping("/schedule")
	public ResponseEntity<List<Schedule>> list() { // 로컬스토리지나 쿠키나 토큰에서 꺼낸 id (클라이언트가 서버한테)
		int userId = 1;
		List<Schedule> list = scheduleService.getScheduleList(userId);
		if (list == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		else if (list.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 2. 일정 상세 조회
	@GetMapping("/schedule/{scheduleId}")
	public ResponseEntity<Schedule> detail(@PathVariable("scheduleId") int scheduleId) {
		Schedule schedule = scheduleService.getSchedule(scheduleId);
		if(schedule == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(schedule);
	}
	// 3. 일정 등록
	// schedule 반환
	@PostMapping("/schedule")
	public ResponseEntity<String> write(@RequestBody Schedule schedule){
		// 세션에서 가져온 userId
		int userId = 1; 
		// User user = session.getAttribute("user");
		// userId = user.getUserId();
		schedule.setUserId(userId);
		boolean isWritten = scheduleService.writeSchedule(schedule);
		if(isWritten) {
			return ResponseEntity.status(HttpStatus.OK).body("일정 등록에 성공했습니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일정 등록에 실패했습니다.");
	}
	// 4. 일정 수정
	@PutMapping("/schedule/{scheduleId}")
	public ResponseEntity<?> update(@PathVariable("scheduleId") int scheduleId, @RequestBody Schedule schedule){
		schedule.setScheduleId(scheduleId); // 아니면 프론트에서 hidden으로 이미 schedule에 넣어서 보내면 url변수로 안보내도 될듯
		boolean isUpdated = scheduleService.modifySchedule(schedule);
		if(isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body("일정 수정에 성공했습니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일정 수정에 실패했습니다.");
	}
	// 5. 일정 삭제
	@DeleteMapping("/schedule/{scheduleId}")
	public ResponseEntity<?> remove(@PathVariable("scheduleId") int scheduleId){
		boolean isDeleted = scheduleService.removeSchedule(scheduleId);
		if(isDeleted) {
			return ResponseEntity.status(HttpStatus.OK).body("일정 삭제에 성공했습니다.");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일정 삭제에 실패했습니다.");
	}
	

}
