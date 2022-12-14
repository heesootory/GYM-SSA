package com.ssafy.finalPjt.model.service.user;

import java.util.List;

import com.ssafy.finalPjt.model.dto.User;

public interface UserService {
	
	// 전체 회원 가져오기
	List<User> getUserList();

	// 회원 등록
	boolean registUser(User user);

	// 로그인
	String loginUser(String id, String pw);

	// 회원정보 수정
	boolean modifyUser(User user);

	// 회원정보 상세
	User datailUser(String id);

	// 탈퇴
	void dropUser(String id);

	// 아이디 중복확인
	boolean checkId(String id);

	String checkJoin(User user);
}
