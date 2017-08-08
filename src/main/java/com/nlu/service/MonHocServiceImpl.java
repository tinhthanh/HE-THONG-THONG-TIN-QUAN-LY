package com.nlu.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nlu.dao.entity.MonHoc;
import com.nlu.repository.BoMonRepository;
import com.nlu.repository.GiangVienRepository;
import com.nlu.repository.MonHocRepository;

@Service
@Transactional
public class MonHocServiceImpl implements MonHocService {
	@Autowired
	MonHocRepository monHocRepository;
	@Autowired
	BoMonRepository boMonRepository;
	@Autowired
	GiangVienRepository giangVienRepository;

	@Autowired
	SessionFactory sf;

	@Override
	public Page<MonHoc> list(String sreach, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return monHocRepository.list(sreach, pageRequest);
	}

	@Override
	public MonHoc getMonHocByMaMonHoc(int maMonHoc) {
		Session session = sf.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<MonHoc> listMonHoc = session.createCriteria(MonHoc.class).add(Restrictions.eq("mamon", maMonHoc)).list();
		return listMonHoc.get(0);
	}

	@Override
	public MonHoc findOndById(Integer mamon) {
		// TODO Auto-generated method stub
		return monHocRepository.findOne(mamon);
	}

	@Override
	public Page<MonHoc> listMonHocOfGiaoVien(int maGv, Pageable pageRequest) {
		List<MonHoc> list = giangVienRepository.listMonHocOfGiaoVien(maGv);
		return new PageImpl<>(list, pageRequest, list.size());

	}

	@Override
	public Page<MonHoc> listMonHocOfBoMon(int maBoMon, Pageable pageRequest) {

		List<MonHoc> list = (List<MonHoc>) monHocRepository.listMonHocOfBoMon(maBoMon, pageRequest);
		return new PageImpl<>(list, pageRequest, list.size());
		// return
		// monHocRepository.listMonHocOfBoMon(boMonRepository.listMonHocOfBoMon(maBoMon),
		// pageRequest);
	}

	@Override
	public MonHoc insert(MonHoc monHoc) {
		try {
			return monHocRepository.saveAndFlush(monHoc);
		} catch (Exception e) {
			e.printStackTrace();
			return monHoc;
		}
	}

	@Override
	public MonHoc findOne(int maMonHoc) {
		return monHocRepository.findOne(maMonHoc);
	}

	@Override
	public int capNhatTienDoCongViec(int maMon, boolean tienDo) {
		Session session = sf.getCurrentSession();
		Query query = session.createQuery("UPDATE MonHoc SET trangthai=:trangthai WHERE mamon=:mamon");
		query.setBoolean("trangthai", tienDo);
		query.setInteger("mamon", maMon);
		return query.executeUpdate();

	}

	@Override
	public void updateTrangThai(int maMon, boolean trangThai) {
		monHocRepository.updateTrangThai(maMon, trangThai);

	}

}
