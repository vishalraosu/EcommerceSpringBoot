package com.softvision.spring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.softvision.spring.dao.UserDAOImpl;
import com.softvision.spring.model.FeedbackBo;
import com.softvision.spring.model.UserRegister;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDAOImpl userdao;

	public UserDAOImpl getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDAOImpl userdao) {
		this.userdao = userdao;
	}

	@Override
	@Transactional
	public void addPerson(UserRegister p) {
		logger.info("User List:: service entered" + p);
		this.userdao.addPerson(p);
	}

	@Override
	@Transactional
	public void updatePerson(UserRegister p) {

		this.userdao.updatePerson(p);
	}

	@Override
	@Transactional
	public List<UserRegister> listUsers() {
		return this.userdao.listUsers();
	}

	@Override
	@Transactional
	public UserRegister getPersonByEmail(UserRegister userRegister, HttpSession session) {
		logger.info("User List:: service entered :getPersonByEmail");
		return this.userdao.getDetails(userRegister, session);
	}

	@Override
	@Transactional
	public UserRegister getUserById(UserRegister userRegister) {
		logger.info("edit service entered");

		return this.userdao.getUserById(userRegister);
	}

	@Override
	@Transactional
	public FeedbackBo addFeedback(FeedbackBo feedbackBo) {
		logger.info("Service feedback controller entered");

		return this.userdao.saveFeedback(feedbackBo);

	}
}
