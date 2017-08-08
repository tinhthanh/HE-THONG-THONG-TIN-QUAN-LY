package com.nlu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlu.dao.entity.ChuongMuc;
import com.nlu.repository.ChuongMucRepository;

@Service
@Transactional
public class ChuongMucServiceImpl implements ChuongMucService {
	@Autowired
	ChuongMucRepository chuongMucRepository;

	@Override
	public Page<ChuongMuc> list(String tieude, int ch, Pageable page) {
		// TODO Auto-generated method stub
		return chuongMucRepository.list(tieude, ch, page);

	}

	@Override
	public void insert(ChuongMuc chuongMuc) {
		chuongMucRepository.save(chuongMuc);
	}

	@Override
	public boolean xoaChuongMuc(int maChuong) {
		try {
			chuongMucRepository.delete(maChuong);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDeCuong(int maMonHoc) {
		int res = chuongMucRepository.deleteDeCuong(maMonHoc);
		if (res <= 0) {
			return false;
		}
		return true;
	}

	@Override
	public ChuongMuc findOne(int maChuong) {
		// TODO Auto-generated method stub
		return chuongMucRepository.findOne(maChuong);
	}

	

}
