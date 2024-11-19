package com.tripdisk.mvc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripdisk.mvc.model.dao.ScheduleDao;
import com.tripdisk.mvc.model.dto.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	private final ScheduleDao scheduleDao;
	
	@Autowired
	public ScheduleServiceImpl(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}
	
	// 1. 일정 전체 조회
	@Override
	public List<Schedule> getScheduleList(int userId) {
		return scheduleDao.selectAll(userId);
	}
	// 2. 일정 상세 조회
	@Override
	public Schedule getSchedule(int scheduleId) {
		return scheduleDao.selectOne(scheduleId);
	}
	// 3. 일정 등록
	@Override
	public boolean writeSchedule(Schedule schedule) {
		int result = scheduleDao.insertSchedule(schedule); // 건드린 레코드 수를 반환
		return result == 1;
	}
	// 4. 일정 수정
	@Override
	public boolean modifySchedule(Schedule schedule) {
		int result = scheduleDao.updateSchedule(schedule);
		return result == 1;
	}
	// 5. 일정 삭제
	@Override
	public boolean removeSchedule(int scheduleId) {
		int result = scheduleDao.deleteSchedule(scheduleId);
		return result == 1;
	}
	
	
}
