package com.nlu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.GiangVien;
import com.nlu.dao.entity.MonHoc;
public interface GiangVienRepository extends JpaRepository<GiangVien, Integer> {

	@Query("select gv from GiangVien gv where gv.tengv like :x")
	public Page<GiangVien> list(@Param("x") String sreach , Pageable page);	
	
	@Query("select gv from GiangVien gv where gv.magv =:x")
	 public GiangVien giangVienById(@Param("x") Integer id) ;
	@Query("select  gv.monHocList from GiangVien gv where gv.magv =:x")
	public List<MonHoc> listMonHocOfGiaoVien(@Param("x") int maGv);
	@Query("select gv from GiangVien gv where gv.mabomon.mabomon=:mabomon ")
	public List<GiangVien> findAllByIdBoMon(@Param("mabomon")int maBoMon);
}
