package com.nlu.dao;

import java.util.Date;
import java.util.List;

public class CongViecDAO {
	private int maMon;
	private String tenMonHoc;
	private int maGiaoVien;
	private String tenGV;
	private String tenLoaiCongViec;
	private int maLoaiCongViec;
	private int maCongViec;
	private boolean trangThai;
	private String noiDungcongViec;
	private Date thoiGianBatDau;
	private Date thoiGianKetThuc;
	private List<ChuongDAO> chuong;
	
	// chung lam meo g 
	private int soLuongDeToiDa;
	
	private boolean trangThaiCuaNguoiNhanViec;
	
	
	
	
	
	
	

	public boolean isTrangThaiCuaNguoiNhanViec() {
		return trangThaiCuaNguoiNhanViec;
	}

	public void setTrangThaiCuaNguoiNhanViec(boolean trangThaiCuaNguoiNhanViec) {
		this.trangThaiCuaNguoiNhanViec = trangThaiCuaNguoiNhanViec;
	}

	public int getSoLuongDeToiDa() {
		return soLuongDeToiDa;
	}

	public void setSoLuongDeToiDa(int soLuongDeToiDa) {
		this.soLuongDeToiDa = soLuongDeToiDa;
	}
	
	public CongViecDAO(int maMon, String tenMonHoc, int maGiaoVien, String tenGV, String tenLoaiCongViec,
			int maLoaiCongViec, int maCongViec, boolean trangThai) {
		super();
		this.maMon = maMon;
		this.tenMonHoc = tenMonHoc;
		this.maGiaoVien = maGiaoVien;
		this.tenGV = tenGV;
		this.tenLoaiCongViec = tenLoaiCongViec;
		this.maLoaiCongViec = maLoaiCongViec;
		this.maCongViec = maCongViec;
		this.trangThai = trangThai;
		
	}
	
	
	public CongViecDAO(int maMon, String tenMonHoc, int maGiaoVien, String tenGV, String tenLoaiCongViec,
			int maLoaiCongViec, int maCongViec, boolean trangThai,boolean trangThaiCuaNguoiNhanViec ) {
		super();
		this.maMon = maMon;
		this.tenMonHoc = tenMonHoc;
		this.maGiaoVien = maGiaoVien;
		this.tenGV = tenGV;
		this.tenLoaiCongViec = tenLoaiCongViec;
		this.maLoaiCongViec = maLoaiCongViec;
		this.maCongViec = maCongViec;
		this.trangThai = trangThai;
		this.trangThaiCuaNguoiNhanViec=trangThaiCuaNguoiNhanViec;
		
	}

	public CongViecDAO(int maMon, String tenMonHoc, int maGiaoVien, String tenGV, String tenLoaiCongViec,
			int maLoaiCongViec, int maCongViec, boolean trangThai, String noiDungcongViec, Date thoiGianBatDau,
			Date thoiGianKetThuc, int soLuongDeToiDa) {
		super();
		this.maMon = maMon;
		this.tenMonHoc = tenMonHoc;
		this.maGiaoVien = maGiaoVien;
		this.tenGV = tenGV;
		this.tenLoaiCongViec = tenLoaiCongViec;
		this.maLoaiCongViec = maLoaiCongViec;
		this.maCongViec = maCongViec;
		this.trangThai = trangThai;
		this.noiDungcongViec = noiDungcongViec;
		this.thoiGianBatDau = thoiGianBatDau;
		this.thoiGianKetThuc = thoiGianKetThuc;
		this.soLuongDeToiDa = soLuongDeToiDa;
	}

	public CongViecDAO(int maMon, String tenMonHoc, int maGiaoVien, String tenGV, String tenLoaiCongViec,
			int maLoaiCongViec, int maCongViec, boolean trangThai, String noiDungcongViec, Date thoiGianBatDau,
			Date thoiGianKetThuc, List<ChuongDAO> chuong) {
		super();
		this.maMon = maMon;
		this.tenMonHoc = tenMonHoc;
		this.maGiaoVien = maGiaoVien;
		this.tenGV = tenGV;
		this.tenLoaiCongViec = tenLoaiCongViec;
		this.maLoaiCongViec = maLoaiCongViec;
		this.maCongViec = maCongViec;
		this.trangThai = trangThai;
		this.noiDungcongViec = noiDungcongViec;
		this.thoiGianBatDau = thoiGianBatDau;
		this.thoiGianKetThuc = thoiGianKetThuc;
		this.chuong = chuong;
	}

	public List<ChuongDAO> getChuong() {
		return chuong;
	}

	public void setChuong(List<ChuongDAO> chuong) {
		this.chuong = chuong;
	}

	public CongViecDAO(String tenMonHoc, int maGiaoVien, String tenGV, String tenLoaiCongViec, int maLoaiCongViec,
			int maCongViec, boolean trangThai) {
		super();
		this.tenMonHoc = tenMonHoc;
		this.maGiaoVien = maGiaoVien;
		this.tenGV = tenGV;
		this.tenLoaiCongViec = tenLoaiCongViec;
		this.setMaLoaiCongViec(maLoaiCongViec);
		this.maCongViec = maCongViec;
		this.trangThai = trangThai;
	}

	public CongViecDAO(int maMon, String tenMonHoc, int maGiaoVien, String tenGV, String tenLoaiCongViec,
			int maLoaiCongViec, int maCongViec, boolean trangThai, String noiDungcongViec, Date thoiGianBatDau,
			Date thoiGianKetThuc) {
		super();
		this.maMon = maMon;
		this.tenMonHoc = tenMonHoc;
		this.maGiaoVien = maGiaoVien;
		this.tenGV = tenGV;
		this.tenLoaiCongViec = tenLoaiCongViec;
		this.maLoaiCongViec = maLoaiCongViec;
		this.maCongViec = maCongViec;
		this.trangThai = trangThai;
		this.noiDungcongViec = noiDungcongViec;
		this.thoiGianBatDau = thoiGianBatDau;
		this.thoiGianKetThuc = thoiGianKetThuc;
	}

	public int getMaMon() {
		return maMon;
	}

	public void setMaMon(int maMon) {
		this.maMon = maMon;
	}

	public CongViecDAO() {
	}

	public String getNoiDungcongViec() {
		return noiDungcongViec;
	}

	public void setNoiDungcongViec(String noiDungcongViec) {
		this.noiDungcongViec = noiDungcongViec;
	}

	public Date getThoiGianBatDau() {
		return thoiGianBatDau;
	}

	public void setThoiGianBatDau(Date thoiGianBatDau) {
		this.thoiGianBatDau = thoiGianBatDau;
	}

	public Date getThoiGianKetThuc() {
		return thoiGianKetThuc;
	}

	public void setThoiGianKetThuc(Date thoiGianKetThuc) {
		this.thoiGianKetThuc = thoiGianKetThuc;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public int getMaGiaoVien() {
		return maGiaoVien;
	}

	public void setMaGiaoVien(int maGiaoVien) {
		this.maGiaoVien = maGiaoVien;
	}

	public String getTenGV() {
		return tenGV;
	}

	public void setTenGV(String tenGV) {
		this.tenGV = tenGV;
	}

	public String getTenLoaiCongViec() {
		return tenLoaiCongViec;
	}

	public void setTenLoaiCongViec(String tenLoaiCongViec) {
		this.tenLoaiCongViec = tenLoaiCongViec;
	}

	public int getMaCongViec() {
		return maCongViec;
	}

	public void setMaCongViec(int maCongViec) {
		this.maCongViec = maCongViec;
	}

	public boolean getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public int getMaLoaiCongViec() {
		return maLoaiCongViec;
	}

	public void setMaLoaiCongViec(int maLoaiCongViec) {
		this.maLoaiCongViec = maLoaiCongViec;
	}

}
