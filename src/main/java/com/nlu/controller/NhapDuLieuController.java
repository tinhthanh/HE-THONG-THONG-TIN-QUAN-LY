package com.nlu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.GiangVien;
import com.nlu.dao.entity.MonHoc;
import com.nlu.service.BoMonService;
import com.nlu.service.GiangVienService;
import com.nlu.service.KhoaService;
import com.nlu.service.MonHocService;

@Controller
@RequestMapping(value = "/nhap-du-lieu")
public class NhapDuLieuController {
	@Autowired
	GiangVienService giangVienService;
	@Autowired
	BoMonService boMonService;
	@Autowired
	KhoaService khoaService;
	@Autowired
	MonHocService monHocService;

	/**
	 * vào giao diện thêm bộ môn vào khoa công nghệ thông tin
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bo-mon", method = RequestMethod.GET)
	public String boMon(Model model) {
		List<GiangVien> giangViens = giangVienService.findAll();
		model.addAttribute("giangViens", giangViens);
		return "nhapdulieu/themBoMon";
	}

	/**
	 * xử lý yêu cầu thêm bộ môn và lưu vào cơ sở dữ liệu
	 * 
	 * @return
	 */
	@RequestMapping(value = "/them-bo-mon", method = RequestMethod.POST)
	public String themBoMon(WebRequest wr, RedirectAttributes model) {
		String tenBoMon = wr.getParameter("tenBoMon");
		BoMon boMon = new BoMon();
		try {
			if (!"null".equals(wr.getParameter("maGv"))) {
				boMon.setMagv(Integer.parseInt(wr.getParameter("maGv")));
			}
			boMon.setMakhoa(khoaService.findOne(1));
			boMon.setTenbomon(tenBoMon);
			boMon = boMonService.insert(boMon);
			model.addFlashAttribute("message", "Thêm dữ liệu thành công! Bộ môn  <p  class=\"text text-success \"> "
					+ boMon.getTenbomon() + " " + boMon.getMagv() + "</p> đã được thêm vào cơ sở dữ liệu!");
		} catch (Exception e) {
			model.addFlashAttribute("message", "Lịt pẹ! Nó không làm còn phá nữa!");
		}
		return "redirect:/nhap-du-lieu/bo-mon";
	}

	@RequestMapping(value = "/mon-hoc", method = RequestMethod.GET)
	public String monHoc(Model model) {
		List<BoMon> boMons = boMonService.findAll();
		if (!boMons.isEmpty()) {
			List<GiangVien> giangViens = giangVienService.findAllByIdBoMon(boMons.get(0).getMabomon().intValue());
			System.out.println(boMons.get(0));
			model.addAttribute("giangViens", giangViens);
		}
		model.addAttribute("boMons", boMons);
		return "nhapdulieu/themMonHoc";
	}

	@RequestMapping(value = "/them-mon-hoc", method = RequestMethod.POST)
	public String themMonHoc(WebRequest wr, RedirectAttributes model) {
		MonHoc monHoc = new MonHoc();
		try {
			int maBoMon = Integer.parseInt(wr.getParameter("boMon"));
			monHoc.setMabomon(boMonService.findOne(maBoMon));
			if (!"null".equals(wr.getParameter("giangVienChinh"))) {
				monHoc.setMagv(Integer.parseInt(wr.getParameter("giangVienChinh")));
			}
			if (wr.getParameterValues("giangDay") != null) {
				String[] string = wr.getParameterValues("giangDay");
				List<GiangVien> giangViens = new ArrayList<>();
				for (String string2 : string) {
					GiangVien giangVien = giangVienService.findOneById(Integer.parseInt(string2));
					giangViens.add(giangVien);
				}
				monHoc.setGiangVienList(giangViens);

			}
			monHoc = monHocService.insert(monHoc);
			model.addFlashAttribute("message",
					"Thêm dữ liệu thành công! Môn hoc  <p  class=\"text text-success \"> " + monHoc.getTenmon() + " "
							+ monHoc.getMabomon().getTenbomon() + "</p> đã được thêm vào cơ sở dữ liệu!");
		} catch (Exception e) {
			model.addFlashAttribute("message", "Đã không làm còn phá code em nữa!");
			e.printStackTrace();
		}

		return "redirect:/nhap-du-lieu/mon-hoc";
	}

	@RequestMapping(value = "/danh-sach-giang-vien-thuoc-bo-mon", method = RequestMethod.POST)
	public @ResponseBody String danhSachGiangVienThuocBoMon(int maBoMon) {
		List<GiangVien> giangViens = giangVienService.findAllByIdBoMon(maBoMon);
		StringBuilder builder = new StringBuilder();
		for (GiangVien giangVien : giangViens) {
			builder.append("<option value=\"" + giangVien.getMagv().intValue() + "\">" + giangVien.getHogv()
					+ giangVien.getTengv() + " - " + giangVien.getMagv().intValue() + "</option>");
		}
		return builder.toString();
	}

}
