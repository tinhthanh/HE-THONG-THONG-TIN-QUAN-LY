package com.nlu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nlu.dao.entity.CauHoi;

@Transactional(readOnly = true)
public interface CauHoiRepository extends JpaRepository<CauHoi, Integer> {
	@Query("select c from CauHoi c where c.noidung like :x")
	public Page<CauHoi> list(@Param("x") String searchNoidung, Pageable page);

	@Query("select  c from  CauHoi c where c.mamon.mamon=:mamon and c.machuong.machuong=:machuong")
	public List<CauHoi> getListCauHoi(@Param("mamon") int maMonHoc, @Param("machuong") int maChuong);
	
	
	
	// thành viết cho phần xét duyệt phân công tạo câu hỏi 
	// danh sach cac cau hỏi chua duoc duyet cua mot giảng viên theo mã giảng viên 
	@Query("select  ch from CauHoi ch where ch.mamon.mamon =:mamon AND ch.magv =:magv AND trangthai=false")
	public List<CauHoi> listPhanCongTaoByGv(@Param("mamon") int mamon , @Param("magv") int magv );

	// thanh || diem so cau hỏi  trong chuong của giang viên tao
	@Query("  select COUNT(ch.machuong.machuong) FROM CauHoi ch WHERE ch.mamon.mamon = :mamh AND ch.machuong.machuong = :machuong AND ch.magv = :magv AND ch.trangthai = false ")
	public int diemSoCauHoiTrongChuong(@Param("mamh")int mamh,@Param("machuong") int mach , @Param("magv") int  magv);
}
