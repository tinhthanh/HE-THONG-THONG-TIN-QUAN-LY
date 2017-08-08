package com.nlu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nlu.dao.ManagerRole;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	@RequestMapping(value = "")
	public String index(HttpServletRequest request) {

		if ((ManagerSession.userAdmin(request)) == null) {
			return "login/login";
		} else {

			return "home";
		}
	}

	@RequestMapping(value = "/{id_role}", method = RequestMethod.GET)
	public String indexWithRole(HttpServletRequest request, @PathVariable("id_role") int idRole) {

		if ((ManagerSession.userAdmin(request)) == null) {
			return "login/login";
		} else {
			ManagerRole managerRole = ManagerSession.ROLE(request);
			managerRole.changeCurrentRole(idRole);
			return "home";
		}
	}
}
