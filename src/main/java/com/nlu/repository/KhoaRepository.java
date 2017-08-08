package com.nlu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.Khoa;

public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
	@Query("select k from Khoa k where k.tenkhoa like :x")
	public Page<Khoa> list(@Param("x") String sreach, Pageable page);

	@Query("select  k from Khoa k where k.magv=:magv")
	public Khoa findByMaGv(@Param("magv") int maGv);

	@Query("Select k.boMonList from Khoa k where k.makhoa=:makhoa")
	public List<BoMon> getListBoMonOfKhoa(@Param("makhoa") int maKhoa);
}
