package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nlu.dao.entity.LoaiCongViec;
import com.nlu.service.LoaiCongViecService;

@Controller
@RequestMapping(value = "/loaicongviec")
public class LoaiCongViecController {
	@Autowired
	LoaiCongViecService loaiCongViecService;

	@RequestMapping(value = "")
	public String Index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "Search", defaultValue = "") String Search) {
		Page<LoaiCongViec> pageProduct = loaiCongViecService.list("%" + Search + "%", new PageRequest(p, 2));
		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		model.addAttribute("Search", Search);
		return "loaicongviec/loaicongviec";
	}
}
