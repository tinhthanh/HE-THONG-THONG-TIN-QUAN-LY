package com.nlu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.MonHoc;

public interface BoMonRepository extends JpaRepository<BoMon, Integer> {
	@Query("select b from BoMon b  where b.tenbomon like :x ")
	public Page<BoMon> list(@Param("x") String sreach, Pageable page);

	@Query("FROM BoMon b WHERE b.magv=:magv")
	public Page<BoMon> listBoMonOfGiaoVien(@Param("magv") int maGv, Pageable pageRequest);

	/**
	 * Lấy danh sách môn học của bô môn
	 * 
	 * @param maBoMon
	 * @return
	 */
	@Query("select b.monHocList from BoMon b where b.mabomon=:mabomon")
	public List<MonHoc> listMonHocOfBoMon(@Param("mabomon") int maBoMon);

	@Query("Select b from BoMon b where b.makhoa.makhoa=:makhoa")
	public List<BoMon> listBoMonOfKhoa(@Param("makhoa") int maKhoa, Pageable pageRequest);

	/**
	 * lang them vao
	 */

	/*
	 * /** Lấy danh sách các môn học thuộc mã bộ môn
	 * 
	 * @param maBoMon mã bộ môn
	 * 
	 * @return List<MonHoc>
	 */
	@Query("SELECT monHocList FROM BoMon b WHERE b.mabomon=:MABOMON ")
	public List<MonHoc> getMonHocFromBoMon(@Param("MABOMON") int maBoMon);

	/**
	 * Lấy danh sách bộ môn mà giáo viên quản lý
	 * 
	 * @param magv
	 *            mã giáo viên
	 * @return
	 */
	@Query("FROM BoMon b WHERE b.magv=:magv")
	public List<BoMon> getListBoMonByMaGV(@Param("magv") int magv);

	/**
	 * lấy danh sách bộ môn bằng mã trưởng khoa
	 * 
	 * @param maGV
	 *            mã trưởng khoa
	 * @return
	 */
	@Query("FROM BoMon WHERE makhoa.magv=:magv ")
	public List<BoMon> getListBoMonByKhoa(@Param("magv") int maGV);

}
