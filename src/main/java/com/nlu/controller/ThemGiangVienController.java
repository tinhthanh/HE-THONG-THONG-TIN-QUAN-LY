package com.nlu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlu.dao.GiangVienDao;
import com.nlu.dao.ManagerRole;
import com.nlu.dao.entity.GiangVien;
import com.nlu.dao.entity.Khoa;
import com.nlu.entities.MD5;
import com.nlu.repository.BoMonRepository;
import com.nlu.service.GiangVienService;
import com.nlu.service.KhoaService;

@Controller
@RequestMapping(value="/them-giang-vien")
public class ThemGiangVienController {
 @Autowired 
 KhoaService khoaService ;
	@Autowired
	GiangVienService giangVienService ;
	@Autowired
	BoMonRepository boMonRepository ;
	@RequestMapping(value="")
	public String index(HttpServletRequest request ,Model model   ) {
		
		GiangVien gv = ManagerSession.userAdmin(request);
		
		if(gv!=null ){
			 ManagerRole role = ManagerSession.ROLE(request);
			if(role.isTruongKhoa()) {
			Khoa khoa =	khoaService.findByMagv(gv.getMagv());
			model.addAttribute("khoa",khoa);
			return "themgiangvien/home";
			}else{
				return "login/login";
			}
		}else{
			return "login/login";
		}
		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody GiangVienDao save(@RequestBody GiangVienDao giangVien, HttpServletRequest request) {
		GiangVien session = ManagerSession.userAdmin(request);
		if (session != null) {
			
			GiangVien gv = new GiangVien() ;
			gv.setMagv(giangVien.getMagv());
			gv.setHogv(giangVien.getHogv());
			gv.setTengv(giangVien.getTengv());
			gv.setNgaysinh(giangVien.getNgaysinh());
			gv.setEmail(giangVien.getEmail());
			gv.setDiachi(giangVien.getDiachi());
			gv.setDienthoai(giangVien.getDienthoai());
			gv.setGioitinh(false);
			gv.setAnhgv(giangVien.getAnhgv());
			gv.setNgaysinh(giangVien.getNgaysinh());
			gv.setMabomon(boMonRepository.findOne(giangVien.getMabomon()));
		    gv.setMatkhau(MD5.getMD5Hash(giangVien.getMatkhau()));
		    giangVien.setMatkhau("");
		    
			GiangVien temp = giangVienService.saveJPA(gv);
			giangVien.setMagv(temp.getMagv());
			return giangVien;
		} else {
			// chua dang nhap
			return new GiangVienDao();
		}
	}
	
}
 