package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlu.dao.entity.GiangVien;
import com.nlu.service.GiangVienService;

@Controller
@RequestMapping(value = "/giangvien")
public class GiangVienController {
	@Autowired
	GiangVienService giangVienService;

	@RequestMapping(value = "")
	public String Index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "Search", defaultValue = "") String Search) {
		Page<GiangVien> pageProduct = giangVienService.list("%" + Search + "%", new PageRequest(p, 20));
		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		model.addAttribute("Search", Search);
		return "giangvien/giangvien";
	}

	@RequestMapping(value = "/lay-ten-giang-vien", method = RequestMethod.POST)
	@ResponseBody
	public String layTenGiangVien(int maGv) {
		GiangVien giangVien = giangVienService.findOneById(maGv);
		return String.format("%s %s", giangVien.getHogv(), giangVien.getTengv());
	}
}
