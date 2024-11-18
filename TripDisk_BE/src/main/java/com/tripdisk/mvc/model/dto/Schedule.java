package com.tripdisk.mvc.model.dto;

import java.time.LocalDateTime;

public class Schedule {
	int scheduleId;
	int userId;
	String startDate;
	String endDate;
	String location;
	
	public Schedule(int userId, String startDate, String endDate, String location) {
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Schedule [scheduleId=" + scheduleId + ", userId=" + userId + ", startDate=" + startDate + ", endDate="
				+ endDate + ", location=" + location + "]";
	}

}
