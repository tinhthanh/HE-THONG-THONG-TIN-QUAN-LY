package com.nlu.dao;

public class ChuongDAO {
	private int maChuong;
	private String tenChuong;
	private int soLuongCauHoi;
	public ChuongDAO(int maChuong, String tenChuong, int soLuongCauHoi) {
		super();
		this.maChuong = maChuong;
		this.tenChuong = tenChuong;
		this.soLuongCauHoi = soLuongCauHoi;
	}
	public int getMaChuong() {
		return maChuong;
	}
	public void setMaChuong(int maChuong) {
		this.maChuong = maChuong;
	}
	public String getTenChuong() {
		return tenChuong;
	}
	public void setTenChuong(String tenChuong) {
		this.tenChuong = tenChuong;
	}
	public int getSoLuongCauHoi() {
		return soLuongCauHoi;
	}
	public void setSoLuongCauHoi(int soLuongCauHoi) {
		this.soLuongCauHoi = soLuongCauHoi;
	}
	

}
