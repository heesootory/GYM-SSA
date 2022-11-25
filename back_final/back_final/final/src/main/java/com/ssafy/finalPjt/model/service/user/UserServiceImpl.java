package com.ssafy.finalPjt.model.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.finalPjt.model.dao.UserDao;
import com.ssafy.finalPjt.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getUserList() {
		return userDao.getAllUser();
	}

	@Override
	public boolean registUser(User user) {
		System.out.println(user.toString());
		return userDao.createUser(user) == 1;
	}

	@Override
	public String loginUser(String id, String pw) {

		String raw = pw;

		// sha512 암호화

		// -------
		System.out.println(id);
		User found_user = userDao.findUser(id);
		System.out.println(found_user);

		if (found_user == null)
			return "fail";
		else {
			String DB_pw = found_user.getPw();
			System.out.println(DB_pw.equals(pw));
			if (DB_pw.equals(pw))
				return "success";
			else
				return "wrong";
		}
	}

	@Override
	public User datailUser(String id) {
		return userDao.findUser(id);
	}

	@Override
	public void dropUser(String id) {
		System.out.println(id);
		userDao.deleteUser(id);
	}

	@Override
	public boolean modifyUser(User user) {
		User orginUser = userDao.findUser(user.getU_id());
		orginUser.setPw(user.getPw());
		orginUser.setEmail(user.getEmail());
		orginUser.setName(user.getName());
		orginUser.setPhone_no(user.getPhone_no());
		orginUser.setNickname(user.getNickname());
		orginUser.setGender(user.getGender());
		orginUser.setProfile(user.getProfile());
		return userDao.updateUser(orginUser) == 1;
	}

	@Override
	public boolean checkId(String id) {
		return (0 == userDao.chkUserID(id));
	}

	@Override
	public String checkJoin(User user) {
		if (0 != userDao.chkEmail(user.getEmail())) {
			System.out.println("중복 메일");
			return "중복 이메일";
		}

		if (0 != userDao.chkNickname(user.getNickname())) {
			System.out.println("중복 닉네임");
			return "중복 닉네임";
		}

		if (0 != userDao.chkPhoneNo(user.getPhone_no())) {
			System.out.println("중복 번호");
			return "중복 전화번호";
		}

		return "성공";
	}

	

}
