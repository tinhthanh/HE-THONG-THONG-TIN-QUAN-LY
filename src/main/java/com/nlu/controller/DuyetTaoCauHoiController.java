package com.nlu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nlu.dao.entity.CauHoi;
import com.nlu.dao.entity.CongViec;
import com.nlu.dao.entity.GiangVien;
import com.nlu.service.CauHoiService;
import com.nlu.service.CongViecService;

@Controller
@RequestMapping(value="/duyet-cau-hoi")
public class DuyetTaoCauHoiController {
	@Autowired 
	CauHoiService cauhoiService ;
	@Autowired
	CongViecService congViecService ;
	@RequestMapping(value="{macv}")
	
	public  String index(@PathVariable("macv") int macv ,  Model model ,HttpServletRequest  request )  {
		 GiangVien gv = ManagerSession.userAdmin(request) ;
		 if(gv==null) {
			
			 return  "login/login" ;
		 }else{
			 CongViec cv = congViecService.getCongViecByMaCV(macv);
			 if(cv !=null && cv.getMaloaicv().getMaloaicv()==2 && cv.getTrangthai() == false){
				List<CauHoi> list = cauhoiService.listPhanCongTaoByGv(cv.getMamon(), cv.getMagv().getMagv());
				model.addAttribute("list", list);
				model.addAttribute("cv" , cv);
			System.out.println(list.size());
			 }else{
				return "redirect:/";
			 }
		return "duyetcauhoi/home" ;
	}
	}
	
	@RequestMapping(value="/duyet/{macv}") 
    public String duyet(@PathVariable("macv") int macv , HttpServletRequest   request){
		GiangVien gv = ManagerSession.userAdmin(request) ;
		 if(gv==null) {
			 return  "login/login" ;
		 }else{
			 CongViec cv = congViecService.getCongViecByMaCV(macv) ;
			  cv.setTrangthai(true); 
			  List<CauHoi> list = cauhoiService.listPhanCongTaoByGv(cv.getMamon(), cv.getMagv().getMagv());
			  for(CauHoi ch  :   list) {
				  ch.setTrangthai(true);
				  cauhoiService.save(ch);
			  }
			 return  "redirect:/congviec/danhsachcongviec" ;
		 }
		 
	}

}
