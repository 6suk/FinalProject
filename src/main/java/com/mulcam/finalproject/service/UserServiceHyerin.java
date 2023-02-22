package com.mulcam.finalproject.service;

import javax.servlet.http.HttpSession;

import com.mulcam.finalproject.dto.UserDTO;
import com.mulcam.finalproject.entity.User;

public interface UserServiceHyerin {
	public static final int CORRECT_LOGIN = 0;
	public static final int WRONG_PASSWORD = 1;
	public static final int ID_NOT_EXIST = 2;
	
	/** DTO 가져오기 */
	UserDTO findByUid(Long uid);
	
	UserDTO findById(String id);
	
	/** 로그인 */
	int login(String id, String pwd, HttpSession session);
	
	/** 회원가입 */
	void join(User u);


	/** 아이디 중복 검사 */
	String checkID(String id, String type);

//	void delete(String id);
}