package com.tripdisk.mvc.model.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tripdisk.mvc.model.dto.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	// 1. 일정 전체 조회
	@Override
	public List<Schedule> getScheduleList() {
		// TODO Auto-generated method stub
		return null;
	}
	// 2. 일정 상세 조회
	@Override
	public Schedule getSchedule(int scheduleId) {
		// TODO Auto-generated method stub
		return null;
	}
	// 3. 일정 등록
	@Override
	public void writeSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		
	}
	// 4. 일정 수정
	@Override
	public void modifySchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		
	}
	// 5. 일정 삭제
	@Override
	public boolean removeSchedule(int scheduleId) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
