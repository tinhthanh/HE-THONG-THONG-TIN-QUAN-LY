package com.nlu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlu.dao.ManagerRole;
import com.nlu.dao.entity.GiangVien;
import com.nlu.dao.entity.MonHoc;
import com.nlu.service.GiangVienService;
import com.nlu.service.KhoaService;
import com.nlu.service.MonHocService;



@Controller
//@RequestMapping(value="/PhanCongDay")
// thay đổn tên controller [thanh]
@RequestMapping(value="/phan-cong-day") 
public class PhanCongDayController {
	//[thanh code phuong thuc nay]
	@Autowired 
	GiangVienService giangVienService ;
	@Autowired 
	KhoaService khoaService ;
	@Autowired MonHocService monHocService ;
   @RequestMapping(value="")
	public String index(HttpServletRequest  request , Model model  ) {
	    GiangVien gv = ManagerSession.userAdmin(request) ;
	     if(gv== null) {
	    	 return "login/login";
	     }else{
	    ManagerRole role =	ManagerSession.ROLE(request) ;
	     if(role.isTruongKhoa()) {
	      model.addAttribute("khoa" , khoaService.findByMagv(gv.getMagv()));
	    return "phancongday/home";
	     }else{
	    	return "login/login";
	     }
        }
   }
   @RequestMapping(value="/phan-cong" , method = RequestMethod.POST ) 
   public @ResponseBody String phanCong(int mamh , int magv) {
       System.out.println(magv + " "+mamh); 
	   GiangVien gv = giangVienService.giengVienById(magv) ;
        
        if(gv == null ) {
        	return "Mã giảng viên không tồn tại ";
        }else {
        	
//        	for(MonHoc  mh: gv.getMonHocList() ){
//        		if(mh.getMagv() == magv) {
//        			return "giảng viên đã đươc phân công dạy môn này ";
//        		}
//        	}
        	MonHoc mh = monHocService.findOne(mamh) ;
        	if(mh == null){
        		return "Mã môn học không tồn tại " ;
        	}else{
        		gv.getMonHocList().add(mh) ;
        		List<MonHoc>  list =gv.getMonHocList() ;
        	    gv.setMonHocList(list);
        	    System.out.println(list.size());
       	  	   giangVienService.saveJPA(gv) ;
        		 return "true" ;
        	}

        }
   }
   @RequestMapping(value="/delete" , method= RequestMethod.GET)
   public @ResponseBody String  delete(int magv  , int mamh ){
	  
	   GiangVien gv = giangVienService.giengVienById(magv) ;
	   if(gv == null ){
		   return "false";
	   }
	    List<MonHoc> list  = gv.getMonHocList() ;
	    System.out.println(list.size());
	     for(int i = 0 ; i
	    		 <list.size() ; i++) {
	    	 if(list.get(i).getMamon() == mamh) {
	    		 list.remove(i);
	    	 }
	     }
	     System.out.println("ngon");
	     gv.setMonHocList(list);
	     giangVienService.saveJPA(gv);
	     System.out.println("bit xe");
	   return "true";
   }
   
   // huynh tinh thanh phan công day , Trưởng khoa phân công 
   
}
