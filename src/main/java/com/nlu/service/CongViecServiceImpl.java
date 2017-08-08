package com.nlu.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlu.dao.CongViecPROC;
import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.CongViec;
import com.nlu.dao.entity.CtTaoCauHoi;
import com.nlu.dao.entity.MonHoc;
import com.nlu.repository.BoMonRepository;
import com.nlu.repository.CongViecRepository;

@Service
@Transactional
public class CongViecServiceImpl implements CongViecService {
	@Autowired
	CongViecRepository congViecRepository;
	@Autowired
	SessionFactory sf;
	@Autowired
	BoMonRepository bmR;
	@Autowired
	BoMonService boMonService;
	@Autowired
	MonHocService monHocService;

	@Override
	public Page<CongViec> list(String search, Pageable page) {
		return congViecRepository.list(search, page);
	}

	@Override
	public boolean checkMagvInBoMon(int magv, int mamabomon) {
		Session session = sf.getCurrentSession();
		Query query = session.createQuery("SELECT b FROM BoMon b WHERE b.magv = :magv").setInteger("magv", magv);
		@SuppressWarnings("unchecked")
		List<BoMon> result = query.list();
		if (result.isEmpty()) {
			return false;
		} else {
			BoMon b = result.get(0);
			if (b.getMabomon() != mamabomon) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public String addCongViec(int maMon, int maGV, int maLoaiCV, String thoiGianBatDau, String thoiGianKetThuc,
			String noiDungCV) {
		SessionImpl impl = (SessionImpl) sf.getCurrentSession();
		Connection connection = impl.connection();
		String message = "";
		try {
			CallableStatement statement = connection.prepareCall("EXEC p_themCongViec_CONG_VIEC ?,?,?,?,?,? ");
			statement.setInt(1, maMon);
			statement.setInt(2, maGV);
			statement.setInt(3, maLoaiCV);
			statement.setString(4, thoiGianBatDau);
			statement.setString(5, thoiGianKetThuc);
			statement.setNString(6, noiDungCV);
			message = (statement.executeUpdate() > 0) ? "thêm thành công" : "thêm thất bại";
			connection.close();

		} catch (SQLException e) {
			message = e.getMessage();
		}

		return message;
	}

	@Override
	public String addCongViec3(int maMon, int maGV, int maLoaiCV, String thoiGianBatDau, String thoiGianKetThuc,
			String noiDungCV, int soLuongDeToiDa) {
		SessionImpl impl = (SessionImpl) sf.getCurrentSession();
		Connection connection = impl.connection();
		String message = "";
		try {
			CallableStatement statement = connection.prepareCall("EXEC p_themCongViec2_CONG_VIEC ?,?,?,?,?,?,? ");
			statement.setInt(1, maMon);
			statement.setInt(2, maGV);
			statement.setInt(3, maLoaiCV);
			statement.setString(4, thoiGianBatDau);
			statement.setString(5, thoiGianKetThuc);
			statement.setNString(6, noiDungCV);
			statement.setInt(7, soLuongDeToiDa);
			message = (statement.executeUpdate() > 0) ? "thêm thành công" : "thêm thất bại";
			connection.close();
		} catch (SQLException e) {
			message = e.getMessage();
		}

		return message;
	}

	@Override
	public int addCongViec2(int maMon, int maGV, int maLoaiCV, String thoiGianBatDau, String thoiGianKetThuc,
			String noiDungCV) {
		SessionImpl impl = (SessionImpl) sf.getCurrentSession();
		Connection connection = impl.connection();
		int result = 0;

		try {
			CallableStatement statement = connection
					.prepareCall("EXEC p_themCongViec_CAUHOI_CONG_VIEC ?,?,?,?,?,? ");
			statement.setInt(1, maMon);
			statement.setInt(2, maGV);
			statement.setInt(3, maLoaiCV);
			statement.setString(4, thoiGianBatDau);
			statement.setString(5, thoiGianKetThuc);
			statement.setNString(6, noiDungCV);
			ResultSet rs = statement.executeQuery();
			rs.next();
			result = rs.getInt("result");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CongViec> getAllCongViec() {
		Session session = sf.getCurrentSession();
		Criteria criteria = session.createCriteria(CongViec.class);
		return criteria.list();
	}

	@Override
	public List<Integer> isTruongBoMon(int maGV) {
		List<Integer> result = new ArrayList<>();
		List<BoMon> dsMon = bmR.getListBoMonByMaGV(maGV);
		if (dsMon == null || dsMon.isEmpty()) {
			return result;
		} else {
			for (BoMon boMon : dsMon) {
				result.add(boMon.getMabomon());
			}
		}
		return result;

	}

	@Override
	public List<Integer> getMaMonHocFromTruongBoMon(int MaGV) {
		List<Integer> dsMaBoMon = isTruongBoMon(MaGV);
		List<Integer> result = new ArrayList<>();
		if (dsMaBoMon.isEmpty() || result == null) {
			return result;
		} else {
			for (Integer maBoMon : dsMaBoMon) {
				List<MonHoc> dsMonHoc = boMonService.getMonHocByMaBoMon(maBoMon);
				if (!dsMonHoc.isEmpty() || dsMonHoc != null) {
					for (MonHoc monHoc : dsMonHoc) {
						result.add(monHoc.getMamon());
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<CongViec> getListCongViecByMaGV(int maGV) {
		List<Integer> dsMaMonHoc = this.getMaMonHocFromTruongBoMon(maGV);
		List<CongViec> dsCongViec = new ArrayList<>();
		if (!dsCongViec.isEmpty() || dsMaMonHoc != null) {
			for (Integer maMonHoc : dsMaMonHoc) {

				List<CongViec> c = congViecRepository.getCongViecByMaMon(maMonHoc);
				if (c != null) {
					dsCongViec.addAll(c);
				}
			}
		}
		return dsCongViec;
	}

	@Override
	public CongViec getCongViecByMaCV(int maCV) {
		return congViecRepository.findOne(maCV);
	}

	@Override
	public int changeTrangThaiCongViec(int trangThai, int maCV) {
		boolean trangThai2 = (trangThai == 1) ? true : false;
		return congViecRepository.changeTrangThaiCongViec(trangThai2, maCV);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CtTaoCauHoi> getCtTaoCauHoiByMaCV(int maCV) {
		Session session = sf.getCurrentSession();
		Query query = session.createQuery("SELECT c FROM CtTaoCauHoi c WHERE c.ctTaoCauHoiPK.macv = :macv");
		query.setInteger("macv", maCV);
		return query.list();

	}

	@Override
	public List<CongViec> getListCongViecDuocPhanCong(int maGV) {
		return congViecRepository.getListCongViecByMaGV(maGV);
	}

	@Override
	public List<CongViec> getListCongViecByMaMon(int maMon) {
		return congViecRepository.getCongViecByMaMon(maMon);
	}

	@Override
	public List<CongViecPROC> dsCongViecKhoa(int magv) throws SQLException {
		// TODO Auto-generated method stub

		Session session = sf.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		Connection cnn = sessionImpl.connection();
		CallableStatement callableStatement = cnn.prepareCall("proc_danhsachcongviec_cuakhoa ?");
		callableStatement.setInt(1, magv);
		ResultSet rs = callableStatement.executeQuery();
		List<CongViecPROC> res = new ArrayList<>();
		CongViecPROC temp = null;
		while (rs.next()) {
			temp = new CongViecPROC();
			temp.setMacv(rs.getInt(1));
			temp.setTenmon(rs.getString(2));
			temp.setTengv(rs.getString(3));
			temp.setAnhgv(rs.getString(4));
			temp.setTenloaicv(rs.getString(5));
			temp.setTgbatdau(rs.getDate(6));
			temp.setTgketthuc(rs.getDate(7));
			temp.setTrangthaicv(rs.getBoolean(8));
			temp.setNoidungcv(rs.getString(9));
			temp.setTrangthaimon(rs.getBoolean(10));
			temp.setMaloaicv(rs.getInt(11));
			temp.setTrangThaiCauTrucDeThi(rs.getBoolean(12));
			res.add(temp);

		}
		return res;
	}

	@Override
	public List<CongViecPROC> dsCongViecBoMon(int magv) throws SQLException {
		// TODO Auto-generated method stub
		Session session = sf.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		Connection cnn = sessionImpl.connection();
		CallableStatement callableStatement = cnn.prepareCall("proc_danhsachcongviec_cuabonom ?");
		callableStatement.setInt(1, magv);
		ResultSet rs = callableStatement.executeQuery();
		List<CongViecPROC> res = new ArrayList<>();
		CongViecPROC temp = null;
		while (rs.next()) {
			temp = new CongViecPROC();
			temp.setMacv(rs.getInt(1));
			temp.setTenmon(rs.getString(2));
			temp.setTengv(rs.getString(3));
			temp.setAnhgv(rs.getString(4));
			temp.setTenloaicv(rs.getString(5));
			temp.setTgbatdau(rs.getDate(6));
			temp.setTgketthuc(rs.getDate(7));
			temp.setTrangthaicv(rs.getBoolean(8));
			temp.setNoidungcv(rs.getString(9));
			temp.setTrangthaimon(rs.getBoolean(10));
			temp.setMaloaicv(rs.getInt(11));
			temp.setTrangThaiCauTrucDeThi(rs.getBoolean(12));

			res.add(temp);

		}
		return res;
	}

	@Override
	public List<CongViec> getListCongViecByMaLoaiCV(int maLoaiCV) {
		return congViecRepository.getListCongViecByMaLoaiCV(maLoaiCV);
	}

	@Override
	public boolean tienDoCongViec(int macv) throws SQLException {
		Session session = sf.getCurrentSession();
		SessionImpl sessionImpl = (SessionImpl) session;
		Connection cnn = sessionImpl.connection();
		CallableStatement callableStatement = cnn.prepareCall("select [dbo].[FUNC_CHECK_TIENDOCONGVIEC](?) ");
		callableStatement.setInt(1, macv);
		ResultSet resultSet = callableStatement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getBoolean(1);
		}
		return false;
	}

	@Override
	public int addChiTietCauHoi(int maCV, int maChuong, int soLuong) {
		SessionImpl impl = (SessionImpl) sf.getCurrentSession();
		Connection connection = impl.connection();
		int result =0;
		try {
			CallableStatement statement = connection.prepareCall("EXEC p_them_cauhoi_congviec2 ?,?,? ");
			statement.setInt(1, maCV);
			statement.setInt(2, maChuong);
			statement.setInt(3, soLuong);
			result= statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}
