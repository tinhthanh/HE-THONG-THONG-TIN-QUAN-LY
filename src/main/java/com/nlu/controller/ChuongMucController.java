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

import com.nlu.dao.entity.ChuongMuc;
import com.nlu.dao.entity.MonHoc;
import com.nlu.service.ChuongMucService;
import com.nlu.service.MonHocService;

@Controller
@RequestMapping(value = "/chuongmuc")
public class ChuongMucController {
	@Autowired
	ChuongMucService chuongMucService;
	@Autowired
	MonHocService monHocService;

	@RequestMapping(value = "")
	public String Index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,

			@RequestParam(name = "mon", defaultValue = "1") int ch,
			@RequestParam(name = "Search", defaultValue = "") String Search) {
		Page<ChuongMuc> pageProduct = chuongMucService.list("%" + Search + "%", ch, new PageRequest(p, 2));
		int pagesCount = pageProduct.getTotalPages();

		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		model.addAttribute("Search", Search);
		return "chuongmuc/chuongmuc";
	}

	@RequestMapping(value = "/tao-de-cuong/{id_mon_hoc}")
	public String taoDeCuong(@PathVariable("id_mon_hoc") int maMonHoc, HttpServletRequest request,
			@RequestParam(name = "tieu_de", defaultValue = "-1") String tieuDe,
			@RequestParam(name = "mo_ta", defaultValue = "-1") String moTa, Model model) {
		@SuppressWarnings("unused")
		com.nlu.dao.entity.GiangVien giangVien = ManagerSession.userAdmin(request);
		MonHoc monHoc = monHocService.findOndById(maMonHoc);

		// kiểm tra mã giáo viên trong session có phải là giảng viên chính của,
		// và kiểm tra trạng thái môn học đang dùng hay chưa sử dụng
		// môn hay ko
		if (monHoc.getTrangthai() /* đuyệt phân công */) {
			return "redirect:/"/* trả về trang chủ */;
		}
		System.out.println("Tiêu đề: " + tieuDe);
		System.out.println("Mô tả: " + moTa);
		if (!tieuDe.equals("-1") && !moTa.equals("-1")) {
			System.out.println("save");
			ChuongMuc chuongMuc = new ChuongMuc();
			chuongMuc.setMota(moTa);
			chuongMuc.setTieude(tieuDe);
			chuongMuc.setMamon(monHoc);
			chuongMucService.insert(chuongMuc);

			monHoc = monHocService.findOndById(maMonHoc);
		}
		// else làm tiếp
		// lấy danh sách chương mục đã có lưu vào MOdel
		model.addAttribute("monHoc", monHoc);
		return "chuongmuc/taodecuong";
	}

	@RequestMapping(value = "/xoa-chuong-muc", method = RequestMethod.POST)
	public String xoaChuongMuc(@RequestParam(name = "ma_chuong", defaultValue = "-1") int maChuong,
			@RequestParam(name = "ma_mon", defaultValue = "-1") int maMonHoc, HttpServletRequest request) {
		@SuppressWarnings("unused")
		com.nlu.dao.entity.GiangVien giangVien = ManagerSession.userAdmin(request);
		MonHoc monHoc = monHocService.findOndById(maMonHoc);
		if (( /* đuyệt phân công */monHoc.getTrangthai().booleanValue())) {
			return "redirect:/"/* trả về trang chủ */;
		}
		@SuppressWarnings("unused")
		boolean isSuccess = chuongMucService.xoaChuongMuc(maChuong);

		return "redirect:/chuongmuc/tao-de-cuong/" + monHoc.getMamon().intValue();
	}

	@RequestMapping(value = "/lam-lai-de-cuong", method = RequestMethod.POST)
	public String xoa(@RequestParam(name = "ma_mon", defaultValue = "-1") int maMonHoc, HttpServletRequest request) {
		@SuppressWarnings("unused")
		com.nlu.dao.entity.GiangVien giangVien = ManagerSession.userAdmin(request);
		MonHoc monHoc = monHocService.findOndById(maMonHoc);
		System.out.println("Mamon " + maMonHoc);
		if (( /* đuyệt phân công */monHoc.getTrangthai().booleanValue())) {
			return "redirect:/"/* trả về trang chủ */;
		}
		@SuppressWarnings("unused")
		boolean isSuccess = chuongMucService.deleteDeCuong(maMonHoc);

		return "redirect:/chuongmuc/tao-de-cuong/" + monHoc.getMamon().intValue();
	}

	@RequestMapping(value = "/xem-de-cuong/{id_mon_hoc}", method = RequestMethod.GET)
	public String xoa(HttpServletRequest request, @PathVariable("id_mon_hoc") int maMonHoc, Model model) {
		MonHoc monHoc = monHocService.findOndById(maMonHoc);
		model.addAttribute("monHoc", monHoc);
		return "chuongmuc/xemdecuong";
	}

	@RequestMapping(value = "/nop-de-cuong")
	public String nopDeCuong(@RequestParam("ma_mon") int maMon) {

		monHocService.updateTrangThai(maMon, true);
		return "redirect:/congviec/congviecduocphancong";
	}

}
