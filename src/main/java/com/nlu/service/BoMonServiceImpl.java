
package com.nlu.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.MonHoc;
import com.nlu.repository.BoMonRepository;
import com.nlu.repository.KhoaRepository;

@Service
@Transactional
public class BoMonServiceImpl implements BoMonService {
	@Autowired
	BoMonRepository boMonRespository;
	@Autowired
	SessionFactory sf;

	@Autowired
	KhoaRepository khoaResponsitory;

	@Override
	public Page<BoMon> list(String sreach, Pageable pageRequest) {
		return boMonRespository.list(sreach, pageRequest);
	}

	@Override
	public List<BoMon> getListBoMonByMaGV(int magv) {
		return boMonRespository.getListBoMonByMaGV(magv);
	}

	@Override
	public BoMon getBoMonbyMaBoMom(int maBoMon) {
		Session session = sf.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<BoMon> listMonHoc = session.createCriteria(BoMon.class).add(Restrictions.eq("mabomon", maBoMon)).list();
		return listMonHoc.get(0);
	}

	@Override
	public Page<BoMon> listBoMonOfGiaoVien(int maGv, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return boMonRespository.listBoMonOfGiaoVien(maGv, pageRequest);
	}

	@Override
	public Page<BoMon> listBoMonOfKhoa(int maKhoa, Pageable pageRequest) {
		List<BoMon> list = boMonRespository.listBoMonOfKhoa(maKhoa, pageRequest);

		return new PageImpl<>(list, pageRequest, list.size());
	}

	/**
	 * Lang them vao
	 */

	@Override
	public List<MonHoc> getMonHocByMaBoMon(int maBoMon) {
		return boMonRespository.getMonHocFromBoMon(maBoMon);
	}

	@Override
	public List<BoMon> getListBoMonByKhoa(int maGV) {
		return boMonRespository.getListBoMonByKhoa(maGV);
	}

	@Override
	public List<Integer> getListMonHocByKhoa(int maGV) {
		List<Integer> result = new ArrayList<>();
		List<BoMon> b = getListBoMonByKhoa(maGV);
		for (BoMon boMon : b) {
			result.add(boMon.getMabomon());
		}

		return result;
	}

	@Override
	public BoMon insert(BoMon boMon) {
		return boMonRespository.save(boMon);
	}

	@Override
	public List<BoMon> findAll() {
		return boMonRespository.findAll();
	}

	@Override
	public BoMon findOne(int maBoMon) {
		return boMonRespository.findOne(maBoMon);
	}

}
