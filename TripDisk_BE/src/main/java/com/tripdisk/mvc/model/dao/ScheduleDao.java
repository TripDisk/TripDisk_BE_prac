package com.tripdisk.mvc.model.dao;

import java.util.List;

import com.tripdisk.mvc.model.dto.Schedule;

public interface ScheduleDao {
	// 1. 일정 전체 조회
	public List<Schedule> selectAll(int userId);
	// 2. 일정 상세 조회
	public Schedule selectOne(int scheduleId);
	// 3. 일정 등록
	public int insertSchedule(Schedule schedule);
	// 4. 일정 수정
	public int updateSchedule(Schedule schedule);
	// 5. 일정 삭제
	public int deleteSchedule(int scheduleId);

}