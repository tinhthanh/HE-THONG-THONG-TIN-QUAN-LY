package com.nlu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nlu.dao.entity.MonHoc;

public interface MonHocService {
	public Page<MonHoc> list(String sreach, Pageable pageRequest);

	/**
	 * Lấy môn học từ mã môn học
	 * 
	 * @param maMonHoc
	 *            mã môn học
	 * @return
	 */
	public MonHoc getMonHocByMaMonHoc(int maMonHoc);

	public MonHoc findOndById(Integer mamon);

	public Page<MonHoc> listMonHocOfGiaoVien(int intValue, Pageable pageRequest);

	public Page<MonHoc> listMonHocOfBoMon(int maBoMon, Pageable pageRequest);

	public MonHoc insert(MonHoc monHoc);

	public MonHoc findOne(int maMonHoc);

	/**
	 * cập nhật tiến độ cho công việc
	 * 
	 * @param maMon
	 * @param tienDo
	 * @return
	 */
	public int capNhatTienDoCongViec(int maMon, boolean tienDo);

	public void updateTrangThai(int maMon, boolean b);

}
