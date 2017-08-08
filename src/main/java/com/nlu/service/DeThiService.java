package com.nlu.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nlu.dao.entity.DeThi;

public interface DeThiService {
	public Page<DeThi> list(Pageable page);

	public List<DeThi> getListDeThiByMaMonHocTrangThaiTrue(int maMonHoc);
}
