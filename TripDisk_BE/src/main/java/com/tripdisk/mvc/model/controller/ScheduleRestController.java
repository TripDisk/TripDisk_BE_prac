package com.tripdisk.mvc.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripdisk.mvc.model.dto.Schedule;
import com.tripdisk.mvc.model.model.service.ScheduleService;

@RestController
@RequestMapping("/api-schedule")
public class ScheduleRestController {
	
	// 서비스 의존성 주입
	private final ScheduleService scheduleService;
	
	@Autowired
	public ScheduleRestController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	// 1. 일정 전체 조회 - 검색정렬 나중에 구현
	@GetMapping("/schedule")
	public ResponseEntity<List<Schedule>> list(int userId){ // 로컬스토리지나 쿠키나 토큰에서 꺼낸 id (클라이언트가 서버한테)
		
		
		
		List<Schedule> list = scheduleService.getScheduleList();
		if(list == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		else if(list.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.status(HttpStatus.OK).body(list);
		
	}
	// 2. 일정 상세 조회
	
	// 3. 일정 등록
	// 4. 일정 수정
	// 5. 일정 삭제
	
}
