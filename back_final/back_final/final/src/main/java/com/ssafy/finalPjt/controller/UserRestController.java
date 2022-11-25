package com.ssafy.finalPjt.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.finalPjt.model.dto.User;
import com.ssafy.finalPjt.model.service.user.UserService;
import com.ssafy.finalPjt.util.JwtUtil;

@RestController
@RequestMapping("/api/userApi")
public class UserRestController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String WRONG = "wrong";

	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;
	
	// 모든 회원 가져오기
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUserList(){
		System.out.println("모든 유저 조회");
		List<User> list = userService.getUserList();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	

	// 회원 등록
	@PostMapping("/regist")
	public ResponseEntity<String> userRegist(User user, @RequestPart(required = false) MultipartFile Imagefile) {
		System.out.println("---------------------------------------");
		System.out.println("User 객체 : " + user);
		System.out.println("파일 업로드 : " + Imagefile.getOriginalFilename());
		System.out.println("---------------------------------------");
		// 저장 경로 지정(로컬)
		String uploadPath = "C:\\final_upload\\profile";
		if(!new File(uploadPath).exists()) {
			new File(uploadPath).mkdir();
		}
		// 중복 이름 방지
		String fileName = Imagefile.getOriginalFilename();
		String prefixName = System.currentTimeMillis() + fileName;
		
		// 새로 만든 이름과 경로를 target객체로 만듬
		File target = new File(uploadPath, prefixName);
		try {
			// 파일 복사
			FileCopyUtils.copy(Imagefile.getBytes(), target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// dto에 파일 이름을 매칭 시키기
		user.setProfile(prefixName);
		
		String msg;
		if (!(msg = userService.checkJoin(user)).equals("성공")) {
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}

		userService.registUser(user);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 로그인
	@GetMapping("/login")
	public ResponseEntity<Map<String, Object>> userLogin(String id, String pw) {
		System.out.println("로그인1 :" + id + " " + pw);
		HashMap<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		// id, pw 유효성 처리?
		System.out.println(id + " " + pw);
		String ans = userService.loginUser(id, pw); // 성공, 실패, 불일치

		try {
			if (ans.equals("success")) {
				result.put("access-token", jwtUtil.createToken("id", id));
				result.put("message", SUCCESS);
				status = HttpStatus.OK;
			} else if (ans.equals("wrong")) {
				result.put("message", WRONG);
				status = HttpStatus.OK;
			} else { // ans.equals("fail")
				result.put("message", FAIL);
				status = HttpStatus.OK;
			}
		} catch (UnsupportedEncodingException e) {
			result.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		System.out.println(result);
		return new ResponseEntity<Map<String, Object>>(result, status);

	}

	// 회원정보 상세
	@GetMapping("/detail")
	public ResponseEntity<User> userDetail(String id) {
		System.out.println(id + " 이건 아이디");
		userService.datailUser(id);
		User user = userService.datailUser(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// 탈퇴
	@DeleteMapping("/delete")
	public ResponseEntity<String> userDelete(String id) {
		userService.dropUser(id);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 수정
	@PutMapping("/update")
	public ResponseEntity<String> userUpdate(User user) {
		userService.modifyUser(user);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 회원중복 체크
	@GetMapping("/check")
	public ResponseEntity<String> checkDuplicateId(String id) {
		boolean chk_id = userService.checkId(id);
		System.out.println("회원 중복체크 " + chk_id);
		if (chk_id) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 
	
	

}
