package com.nlu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.MonHoc;

public interface BoMonService {
	public Page<BoMon> list(String sreach, Pageable pageRequest);

	/**
	 * lấy danh sách môn học từ mã bộ môn
	 * 
	 * @param mabomon
	 *            mã bộ môn
	 * @return List<MonHoc>result
	 */
	public List<BoMon> getListBoMonByMaGV(int magv);

	/**
	 * Lấy bộ môn dựa vào mã bộ môn
	 * 
	 * @param maBoMon
	 *            mã bộ môn
	 * @return
	 */
	public BoMon getBoMonbyMaBoMom(int maBoMon);
	
	
	/**
	 * lấy ra danh sách môn học từ mã bộ môn nhận vào
	 * @param maBoMon
	 * @return danh sách môn học
	 */
	public List<MonHoc>getMonHocByMaBoMon(int maBoMon);

	public Page<BoMon> listBoMonOfGiaoVien(int intValue, Pageable pageRequest);

	public Page<BoMon> listBoMonOfKhoa(int maKhoa, Pageable pageRequest);
	
	
	/**
	 * lang
	 */
	public  List<BoMon>getListBoMonByKhoa(int maGV);
	/**
	 * lấy tất cả môn học của một khoa
	 * @param maGV
	 * @return
	 */
	public  List<Integer>getListMonHocByKhoa(int maGV);

	public BoMon insert(BoMon boMon);

	public List<BoMon> findAll();

	public BoMon findOne(int maBoMon);
}
