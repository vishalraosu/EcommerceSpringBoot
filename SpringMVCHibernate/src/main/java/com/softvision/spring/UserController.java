package com.softvision.spring;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.softvision.spring.model.FeedbackBo;
import com.softvision.spring.model.UserRegister;
import com.softvision.spring.service.UserService;

@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserRegister userData = null;

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/registerperson", method = RequestMethod.GET)
	public String registerPerson(Model model) {
		model.addAttribute("userRegister", new UserRegister());
		model.addAttribute("register", this.userService.listUsers());
		return "person";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("user") UserRegister p) {

		if (p.getId() == 0) {
			logger.info(" signup controller entered");

			this.userService.addPerson(p);
		} else {
			this.userService.updatePerson(p);
		}
		return "redirect:/userLogin";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(UserRegister userRegister, HttpSession session, Model model) {
		logger.info("User Data: " + userRegister);
		userData = userService.getPersonByEmail(userRegister, session);
		if (userData != null) {
			model.addAttribute("userData", userData);
			return new ModelAndView("redirect:viewProduct");
		} else {
			model.addAttribute("message", "Invalid Credentials, Please try again !!");
			return new ModelAndView("Login");
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(UserRegister userRegister, HttpSession session, Model model) {
		logger.info("User Data: " + userRegister);
		if (session != null) {
			session.removeAttribute("username");
			session.invalidate();
		}
		return "redirect:/index.jsp";
	}

	@RequestMapping("/profile")
	public ModelAndView profile() {
		logger.info("profile entered controller");
		UserRegister user = userData;
		if (user != null) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("user", user);
			mv.setViewName("editProfile");
			return mv;
		} else {
			return new ModelAndView("redirect:/userLogin");
		}
	}

	@RequestMapping("/editdata/{id}")
	public String editPerson(UserRegister userRegister, Model model) {

		UserRegister user = new UserRegister();
		model.addAttribute("user", this.userService.getUserById(userRegister));
		logger.info("user " + user);
		userData = null;
		return ("redirect:/profileUpdate.jsp");
	}

	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		return mv;
	}

	@RequestMapping("/userLogin")
	public ModelAndView userLogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Login");
		return mv;
	}

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public String addFeedback(Model model, FeedbackBo feedback) {
		logger.info("feedback " + feedback);
		model.addAttribute("feedback", this.userService.addFeedback(feedback));
		return "redirect:/thanks.jsp";
	}
}
