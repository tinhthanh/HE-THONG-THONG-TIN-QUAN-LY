package com.nlu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.DeThi;

public interface DethiRepository extends JpaRepository<DeThi, Integer> {
	@Query(" select d from DeThi d ")
	public Page<DeThi> list(Pageable page);

	@Query("Select d from DeThi d  where d.mamon.mamon=:mamon and trangthai=1  ")
	public List<DeThi> getListDeThiByMaMonHocTrangThaiTrue(@Param("mamon") int maMonHoc);

}
