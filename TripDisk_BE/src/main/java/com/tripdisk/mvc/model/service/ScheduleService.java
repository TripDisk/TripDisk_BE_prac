package com.tripdisk.mvc.model.service;

import java.util.List;

import com.tripdisk.mvc.model.dto.Schedule;

public interface ScheduleService {
	// 1. 일정 전체 조회 
	public List<Schedule> getScheduleList(int userId);
	// 2. 일정 상세 조회 
	public Schedule getSchedule(int scheduleId);
	// 3. 일정 등록
	public boolean writeSchedule(Schedule schedule);
	// 4. 일정 수정
	public boolean modifySchedule(Schedule schedule); 
	// 5. 일정 삭제
	public boolean removeSchedule(int scheduleId);
	
}
