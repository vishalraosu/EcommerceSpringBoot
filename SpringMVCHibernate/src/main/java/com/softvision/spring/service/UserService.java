package com.softvision.spring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.softvision.spring.model.FeedbackBo;
import com.softvision.spring.model.UserRegister;

public interface UserService {
	public void addPerson(UserRegister p);

	public void updatePerson(UserRegister p);

	public List<UserRegister> listUsers();

	public UserRegister getPersonByEmail(UserRegister userRegister, HttpSession session);

	public UserRegister getUserById(UserRegister userRegister);

	public FeedbackBo addFeedback(FeedbackBo feedbackBo);
}
