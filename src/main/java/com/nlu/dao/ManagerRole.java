package com.nlu.dao;

import java.util.Collections;
import java.util.List;

public class ManagerRole {

	private List<Role> list;
	private Role currentRole;
	private boolean truongKhoa, truongBoMon, giaoVien;

	public boolean isTruongKhoa() {
		return truongKhoa;
	}

	public void setTruongKhoa(boolean truongKhoa) {
		this.truongKhoa = truongKhoa;
	}

	public boolean isTruongBoMon() {
		return truongBoMon;
	}

	public void setTruongBoMon(boolean truongBoMon) {
		this.truongBoMon = truongBoMon;
	}

	public boolean isGiaoVien() {
		return giaoVien;
	}

	public void setGiaoVien(boolean giaoVien) {
		this.giaoVien = giaoVien;
	}

	public ManagerRole(List<Role> list) {
		this.list = list;
		if (!list.isEmpty()) {
			this.truongKhoa = isTK();
			this.truongBoMon = isTBM();
			this.giaoVien = isGV();
			Collections.sort(list);
			currentRole = list.get(0);

		}
	}

	public Role getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(Role currentRole) {
		this.currentRole = currentRole;
	}

	public boolean changeCurrentRole(int roleName) {
		for (Role role : list) {
			if (role.getRoleName() == roleName) {
				this.currentRole = role;
				return true;
			}
		}
		return false;
	}

	public boolean isTBM() {
		for (Role role : list) {
			if (role.getRoleName() == 2) {
				return true;
			}
		}
		return false;
	}

	public List<Role> getList() {
		return list;
	}

	public void setList(List<Role> list) {
		this.list = list;
	}

	public boolean isGV() {
		for (Role role : list) {
			if (role.getRoleName() == 3) {
				return true;
			}
		}
		return false;
	}

	public boolean isTK() {
		for (Role role : list) {
			if (role.getRoleName() == 1) {
				return true;
			}
		}
		return false;
	}
}
