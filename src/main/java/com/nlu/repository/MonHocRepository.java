package com.nlu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.MonHoc;

public interface MonHocRepository extends JpaRepository<MonHoc, Integer> {

	@Query("select m from MonHoc m where m.tenmon like :x")
	public Page<MonHoc> list(@Param("x") String sreach, Pageable page);

	/*
	 * @Query("select  m from MonHoc m where m.magv=:magv") public Page<MonHoc>
	 * listMonHocOfGiaoVien(@Param("magv") int maGv, Pageable pageRequest);
	 */

	@Query("select m from  MonHoc m where m.mabomon.mabomon=:mabomon ")
	public List<MonHoc> listMonHocOfBoMon(@Param("mabomon") int maBoMon, Pageable pageRequest);

	@Modifying
	@Query("update MonHoc set  trangthai=:trangthai where mamon=:mamon ")
	public void updateTrangThai(@Param("mamon") int maMon, @Param("trangthai") boolean trangThai);

}
