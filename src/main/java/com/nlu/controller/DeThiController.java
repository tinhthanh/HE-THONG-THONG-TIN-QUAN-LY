package com.nlu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nlu.dao.ManagerRole;
import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.DeThi;
import com.nlu.dao.entity.GiangVien;
import com.nlu.dao.entity.MonHoc;
import com.nlu.service.BoMonService;
import com.nlu.service.DeThiService;

@Controller
@RequestMapping(value = "/dethi")
public class DeThiController {
	@Autowired
	DeThiService deThiService;
	@Autowired
	BoMonService boMonService;

	@RequestMapping(value = "")
	public String Index(Model model, @RequestParam(name = "page", defaultValue = "0") int p) {
		Page<DeThi> pageProduct = deThiService.list(new PageRequest(p, 2));
		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);

		return "dethi/dethi";
	}

	@RequestMapping(value = "/tao-de-thi", method = RequestMethod.GET)
	public String taoDeThi(HttpServletRequest request, Model model,
			@RequestParam(name = "page", defaultValue = "0") int p) {
		GiangVien giangVien = ManagerSession.userAdmin(request);
		ManagerRole managerRole = ManagerSession.ROLE(request);
		if (giangVien == null || !managerRole.isTBM()) {
			return "login/login";
		}
		List<com.nlu.dao.entity.BoMon> boMons = boMonService.getListBoMonByMaGV(giangVien.getMagv().intValue());
		List<MonHoc> monHocs = new ArrayList<>();
		for (BoMon boMon : boMons) {
			monHocs.addAll(boMon.getMonHocList());
		}

		Pageable pageable = new PageRequest(p, 5);
		int start = pageable.getOffset();
		int end = (start + pageable.getPageSize()) > monHocs.size() ? monHocs.size() : (start + pageable.getPageSize());

		Page<MonHoc> pagescover = new PageImpl<MonHoc>(monHocs.subList(start, end), pageable, monHocs.size());
		int pagesCount = pagescover.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("monHocs", pagescover);
		model.addAttribute("pageIndex", p);
		return "dethi/taodethi";
	}

	@RequestMapping(value = "/danh-sach-de-thi/{id_mon_hoc}", method = RequestMethod.GET)
	public String danhSachDeThi(@PathVariable("id_mon_hoc") int maMonHoc, HttpServletRequest request, Model model,
			@RequestParam(name = "page", defaultValue = "0") int p) {
		ManagerRole role = ManagerSession.ROLE(request);
		if (!role.isTBM()) {
			return "redirect:/home";
		}
		List<DeThi> deThis = deThiService.getListDeThiByMaMonHocTrangThaiTrue(maMonHoc);
		Pageable pageable = new PageRequest(p, 5);
		int start = pageable.getOffset();
		int end = (start + pageable.getPageSize()) > deThis.size() ? deThis.size() : (start + pageable.getPageSize());

		Page<DeThi> pagescover = new PageImpl<DeThi>(deThis.subList(start, end), pageable, deThis.size());
		int pagesCount = pagescover.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("maMon", maMonHoc);
		model.addAttribute("deThis", pagescover);
		model.addAttribute("pageIndex", p);
		return "dethi/danhsachdethi";
	}
}
