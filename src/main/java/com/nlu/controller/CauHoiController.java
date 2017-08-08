package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nlu.dao.entity.CauHoi;
import com.nlu.dao.entity.ChuongMuc;
import com.nlu.dao.entity.MonHoc;
import com.nlu.service.CauHoiService;
import com.nlu.service.ChuongMucService;
import com.nlu.service.MonHocService;

@Controller
@RequestMapping(value = "/cauhoi")
public class CauHoiController {
	@Autowired
	CauHoiService cauHoiService;
	@Autowired
	ChuongMucService chuongMucService;
	@Autowired
	MonHocService monHocService;

	@RequestMapping(value = "")
	public String Index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "Search", defaultValue = "") String Search) {
		Page<CauHoi> pageProduct = cauHoiService.list("%" + Search + "%", new PageRequest(p, 2));
		int pagesCount = pageProduct.getTotalPages();
		int[] pages = new int[pagesCount];
		for (int i = 0; i < pagesCount; i++)
			pages[i] = i;
		model.addAttribute("page", pages);
		model.addAttribute("list", pageProduct);
		model.addAttribute("pageIndex", p);
		model.addAttribute("Search", Search);
		return "cauhoi/cauhoi";
	}

	@RequestMapping(value = "/_select", method = RequestMethod.GET)
	public String select(Model model) {

		return "cauhoi/_select";
	}

	@RequestMapping(value = "/xem-danh-sach-cau-hoi/{id_mon_hoc}/{id_chuong}", method = RequestMethod.GET)
	public String xemDanhSachCauHoiTheoChuong(Model model, @PathVariable("id_mon_hoc") int maMonHoc,
			@PathVariable("id_chuong") int maChuong) {
		// List<CauHoi> listCauHoi = cauHoiService.getListCauHoi(maMonHoc,
		// maChuong);
		ChuongMuc chuong = chuongMucService.findOne(maChuong);

		model.addAttribute("chuongMuc", chuong);
		return "cauhoi/xemcauhoi";
	}

	@RequestMapping(value = "/xem-danh-sach-cau-hoi/{id_mon_hoc}", method = RequestMethod.GET)
	public String xemDanhSachCauHoiTheoMon(Model model, @PathVariable("id_mon_hoc") int maMonHoc) {
		// List<CauHoi> listCauHoi = cauHoiService.getListCauHoi(maMonHoc,
		// maChuong);
		MonHoc monHoc = monHocService.findOne(maMonHoc);
		model.addAttribute("monHoc", monHoc);
		return "cauhoi/xemcauhoiofmonhoc";
	}
  
}
