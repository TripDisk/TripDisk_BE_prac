package com.tripdisk.mvc.model.dao;

import org.apache.ibatis.annotations.Param;

import com.tripdisk.mvc.model.dto.User;

public interface UserDao {

	public int insertUser(@Param("User") User user);

}
