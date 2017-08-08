package com.nlu.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlu.dao.CongViecPROC;
import com.nlu.dao.ManagerRole;
import com.nlu.dao.entity.GiangVien;
import com.nlu.service.CongViecService;
import com.nlu.service.MonHocService;

@Controller
@RequestMapping(value="/cong-viec-proc")
public class CongViecProcController {
	@Autowired
 CongViecService congviecService ;
	@Autowired
	MonHocService  monHocService ;
	@RequestMapping(value="")
	 public String index (HttpServletRequest request , Model   model  , @RequestParam(name = "page", defaultValue = "0") int p) {
		GiangVien gv =  ManagerSession.userAdmin(request) ;
		if(gv==null) {
			return "login/login" ;
		}else {
			try {
			ManagerRole role  =ManagerSession.ROLE(request) ;
			if(role.isTruongKhoa()) {				
					  List<CongViecPROC> congViecPROC = congviecService.dsCongViecKhoa(gv.getMagv()) ;
					
					  Pageable pageable = new PageRequest(p, 2);
					  int start = pageable.getOffset();
					  int end = (start + pageable.getPageSize()) > congViecPROC.size() ? congViecPROC.size() : (start + pageable.getPageSize());
					  
					 
              Page<CongViecPROC> pagescover = new PageImpl<CongViecPROC>(  congViecPROC.subList(start, end) ,pageable, congViecPROC.size());
					  int pagesCount = pagescover.getTotalPages();

						int[] pages = new int[pagesCount];
						for (int i = 0; i < pagesCount; i++)
							pages[i] = i;
						model.addAttribute("page", pages);
						model.addAttribute("list", pagescover);
						for (CongViecPROC i : pagescover) {
							System.err.println("Công việc "+i.getMacv()+"trang thai "+i.isTrangthaicv()+" "+i.isTrangThaiCauTrucDeThi());
						}
						
						model.addAttribute("pageIndex", p);
						 return "congviecproc/home";
				
			  }else
		     	  if(role.isTruongBoMon()) {
		     		 List<CongViecPROC> congViecPROC = congviecService.dsCongViecBoMon(gv.getMagv()) ;
						
					  Pageable pageable = new PageRequest(p, 2);
					  int start = pageable.getOffset();
					  int end = (start + pageable.getPageSize()) > congViecPROC.size() ? congViecPROC.size() : (start + pageable.getPageSize());
					  
					 
             Page<CongViecPROC> pagescover = new PageImpl<CongViecPROC>(  congViecPROC.subList(start, end) ,pageable, congViecPROC.size());
					  int pagesCount = pagescover.getTotalPages();

						int[] pages = new int[pagesCount];
						for (int i = 0; i < pagesCount; i++)
							pages[i] = i;
						model.addAttribute("page", pages);
						model.addAttribute("list", pagescover);
						
						for (CongViecPROC i : pagescover) {
							System.err.println("Công việc "+i.getMaloaicv()+"trang thai "+i.isTrangthaicv()+" "+i.isTrangThaiCauTrucDeThi());
						}
						
						model.addAttribute("pageIndex", p);
						 return "congviecproc/home";
				 }else{
						return "login/login" ;
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "login/login";
			}
			
		}
		
//		 return "congviecproc/home";
	 }
	
	@RequestMapping(value="/tenmon", method= RequestMethod.POST)
	public @ResponseBody String tenmonByMamon( int mamh ){
	   return  	monHocService.findOndById(mamh).getTenmon();
	}
	
}
