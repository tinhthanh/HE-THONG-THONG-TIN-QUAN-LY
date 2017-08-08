package com.nlu.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nlu.dao.entity.ChuongMuc;

public interface ChuongMucService {
	public Page<ChuongMuc> list(String tieude, int ch, Pageable page);

	public void insert(ChuongMuc chuongMuc);

	public boolean xoaChuongMuc(int maChuong);

	public boolean deleteDeCuong(int maMonHoc);

	public ChuongMuc findOne(int maChuong);

}
