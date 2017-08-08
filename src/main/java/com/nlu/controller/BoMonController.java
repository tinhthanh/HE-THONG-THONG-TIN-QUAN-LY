package com.nlu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nlu.dao.ManagerRole;
import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.GiangVien;
import com.nlu.service.BoMonService;
import com.nlu.service.KhoaService;

@Controller
@RequestMapping(value = "/bomon")
public class BoMonController {
	@Autowired
	BoMonService boMonService;
	@Autowired
	KhoaService khoaService;

	@RequestMapping(value = "")
	public String Index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "Search", defaultValue = "") String Search) {
		Page<BoMon> pageProduct = boMonService.list("%" + Search + "%", new PageRequest(p, 2));
		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		model.addAttribute("Search", Search);
		return "bomon/bomon";
	}

	@RequestMapping(value = "/danh-sach-bo-mon", method = RequestMethod.GET)
	public String danhSachBoMon(HttpServletRequest request, Model model,
			@RequestParam(name = "page", defaultValue = "0") int p) {
		GiangVien giangVien = ManagerSession.userAdmin(request);
		Page<BoMon> pageProduct = null;
		ManagerRole managerRole = ManagerSession.ROLE(request);
		if (managerRole.getCurrentRole().getRoleName() == 1) {
			int maKhoa = khoaService.findByMagv(giangVien.getMagv().intValue()).getMakhoa().intValue();
			pageProduct = boMonService.listBoMonOfKhoa(maKhoa, new PageRequest(p, 5));
		} else {
			pageProduct = boMonService.listBoMonOfGiaoVien(giangVien.getMagv().intValue(), new PageRequest(p, 5));
		}
		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		return "bomon/bomon";
	}

}
