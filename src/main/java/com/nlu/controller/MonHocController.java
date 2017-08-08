package com.nlu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nlu.dao.entity.GiangVien;
import com.nlu.dao.entity.MonHoc;
import com.nlu.service.MonHocService;

@Controller
@RequestMapping(value = "/monhoc")
public class MonHocController {
	@Autowired
	MonHocService monHocService;

	@RequestMapping(value = "")
	public String Index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "Search", defaultValue = "") String Search) {
		Page<MonHoc> pageProduct = monHocService.list("%" + Search + "%", new PageRequest(p, 2));
		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		model.addAttribute("Search", Search);
		return "monhoc/monhoc";
	}

	/**
	 * Lấy danh sách môn học mà giảng viên đó giảng dạy, phân trang nếu có quá
	 * nhiều
	 * 
	 * @param request
	 * @param model
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/danh-sach-mon-hoc", method = RequestMethod.GET)
	public String danhDachMonHoc(HttpServletRequest request, Model model,
			@RequestParam(name = "page", defaultValue = "0") int p) {
		GiangVien giangVien = ManagerSession.userAdmin(request);
		Page<MonHoc> pageProduct = monHocService.listMonHocOfGiaoVien(giangVien.getMagv().intValue(),
				new PageRequest(p, 5));
		int pagesCount = pageProduct.getTotalPages();
		if (pageProduct.getSize() != 0)
			for (MonHoc monHoc : pageProduct) {
				System.out.println(monHoc + " môn học");
			}
		else {
			System.out.println("Null con mẹ nó rồi!");
		}

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);

		return "monhoc/monhoc";
	}

	@RequestMapping(value = "/danh-sach-mon-hoc/{id_bomon}", method = RequestMethod.GET)
	public String danhSachMonHocOfBoMon(@PathVariable("id_bomon") int maBoMon, HttpServletRequest request, Model model,
			@RequestParam(name = "page", defaultValue = "0") int p) {
		System.out.println("Ma bo mon "+ maBoMon);
		Page<MonHoc> pageProduct = monHocService.listMonHocOfBoMon(maBoMon, new PageRequest(p, 5));
		System.out.println("Danh sách Môn học " + pageProduct.toString() + " " + pageProduct.getSize());

		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		model.addAttribute("mabomon", maBoMon);
		return "monhoc/monhoc";
	}
}
