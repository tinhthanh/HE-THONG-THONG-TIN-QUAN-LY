package com.nlu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.ChuongMuc;

public interface ChuongMucRepository extends JpaRepository<ChuongMuc, Integer> {

	@Query("select c from  ChuongMuc c where c.mota like :x AND c.mamon.mamon =:ch")
	// tim kiem theo tieude
	public Page<ChuongMuc> list(@Param("x") String search, @Param("ch") int ch, Pageable page);

	@Modifying
	@Query("DELETE FROM ChuongMuc c where  c.mamon.mamon=:mamon ")
	public int deleteDeCuong(@Param("mamon") int maMonHoc);

}
