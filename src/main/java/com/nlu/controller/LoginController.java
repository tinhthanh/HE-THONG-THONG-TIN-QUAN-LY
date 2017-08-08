package com.nlu.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlu.dao.entity.GiangVien;
import com.nlu.entities.MD5;
import com.nlu.service.GiangVienService;
import com.nlu.service.RoleService;

@Controller
@RequestMapping(value = "")
public class LoginController {
	@Autowired
	GiangVienService giangVienService;
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "")
	public String index(HttpServletRequest request) {
		if (ManagerSession.userAdmin(request) != null)
			return "redirect:/home";
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String Login(String username, String password, HttpServletRequest request) {
		String result = giangVienService.loginResMsg(username, MD5.getMD5Hash(password));
		try {
			int id = Integer.parseInt(result);
			// UserLogin userSession = giangVienService.finUserProfile(id);
			GiangVien temp = giangVienService.giengVienById(id);
			ManagerSession.userAdmin(temp, request);
			ManagerSession.ADD_ROLE(roleService.getListRole(temp.getMagv().intValue()), request);
			return "true";
		} catch (NumberFormatException | SQLException ex) {
			// e.printStackTrace();
			// database bắc tắc cả các lổi không đănh nhập đc ném về người dùng
			return result;
		}
	}

	@GetMapping(value = "/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
