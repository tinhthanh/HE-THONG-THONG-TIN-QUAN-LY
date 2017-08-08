package com.nlu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nlu.dao.CauHoiDao;
import com.nlu.dao.DapAnDao;
import com.nlu.dao.entity.CauHoi;

public interface CauHoiService {
	public Page<CauHoi> list(String searchnoidung , Pageable page);
	
	public boolean  themCauHoi(CauHoiDao cauhoi); 
	
	 public boolean themDapAn(DapAnDao dapan);
	 public boolean xoa(int id );
	 public CauHoi findOne(int id ) ;

	public boolean save(CauHoi cauhoi);

	public List<CauHoi> getListCauHoi(int maMonHoc, int maChuong);
	
	// tra ve danh sach cau hỏi của giang vien duoc phan cong tao cau hoi  
	public List<CauHoi>  listPhanCongTaoByGv(int mamon  , int magv );
	
	// thanh viết , điếm sô câu hỏi trong chương 
	// nhan vao ma chuong va ma monhoc
	public int diemSoCauHoiTrongChuong(int mamh , int mach ,int magv);

}
