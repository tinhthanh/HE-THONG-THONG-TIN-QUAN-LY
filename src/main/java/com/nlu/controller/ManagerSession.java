package com.nlu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.nlu.dao.CauHoiDao;
import com.nlu.dao.CommonConstrants;
import com.nlu.dao.DeThiDao;
import com.nlu.dao.ManagerRole;
import com.nlu.dao.entity.GiangVien;

public class ManagerSession {
	/*
	 * public static GiangVien userAdmin(HttpServletRequest request) { GiangVien
	 * res = null; HttpSession session = request.getSession(); res = (GiangVien)
	 * session.getAttribute(CommonConstrants.USER_SESSION); return res; }
	 * 
	 * public static void userAdmin(GiangVien user, HttpServletRequest request)
	 * { HttpSession session = request.getSession();
	 * session.setAttribute(CommonConstrants.USER_SESSION, user); }
	 * 
	 * // THEM CAU HOI public static void ADD_CAU_HOI(CauHoiDao cauhoi,
	 * HttpServletRequest request) { HttpSession session = request.getSession();
	 * session.setAttribute(CommonConstrants.CAU_HOI, cauhoi); }
	 * 
	 * public static CauHoiDao CAU_HOI(HttpServletRequest request) { CauHoiDao
	 * res = null; HttpSession session = request.getSession(); res = (CauHoiDao)
	 * session.getAttribute(CommonConstrants.CAU_HOI); return res; }
	 * 
	 * // THEM CAU HOI public static void ADD_DE_THI(DeThiDao deThi,
	 * HttpServletRequest request) { HttpSession session = request.getSession();
	 * session.setAttribute(CommonConstrants.DE_THI, deThi); }
	 * 
	 * public static DeThiDao DE_THI(HttpServletRequest request) { DeThiDao res
	 * = null; HttpSession session = request.getSession(); res = (DeThiDao)
	 * session.getAttribute(CommonConstrants.DE_THI); return res; }
	 * 
	 * // THEM ROLE public static void ADD_ROLE(ManagerRole managerRole,
	 * HttpServletRequest request) { HttpSession httpSession =
	 * request.getSession(); httpSession.setAttribute(CommonConstrants.ROLE,
	 * managerRole); }
	 * 
	 * public static ManagerRole ROLE(HttpServletRequest request) { ManagerRole
	 * managerRole = null; HttpSession httpSession = request.getSession();
	 * managerRole = (ManagerRole)
	 * httpSession.getAttribute(CommonConstrants.ROLE); return managerRole; }
	 */

	public static GiangVien userAdmin(HttpServletRequest request) {
		GiangVien res = null;
		HttpSession session = request.getSession();
		res = (GiangVien) session.getAttribute(CommonConstrants.USER_SESSION.getValue());
		return res;
	}

	public static void userAdmin(GiangVien user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(CommonConstrants.USER_SESSION.getValue(), user);
	}

	// THEM CAU HOI
	public static void ADD_CAU_HOI(CauHoiDao cauhoi, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(CommonConstrants.CAU_HOI.getValue(), cauhoi);
	}

	public static CauHoiDao CAU_HOI(HttpServletRequest request) {
		CauHoiDao res = null;
		HttpSession session = request.getSession();
		res = (CauHoiDao) session.getAttribute(CommonConstrants.CAU_HOI.getValue());
		return res;
	}

	// THEM CAU HOI
	public static void ADD_DE_THI(DeThiDao deThi, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(CommonConstrants.DE_THI.getValue(), deThi);
	}

	public static DeThiDao DE_THI(HttpServletRequest request) {
		DeThiDao res = null;
		HttpSession session = request.getSession();
		res = (DeThiDao) session.getAttribute(CommonConstrants.DE_THI.getValue());
		return res;
	}

	// THEM ROLE
	public static void ADD_ROLE(ManagerRole managerRole, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute(CommonConstrants.ROLE.getValue(), managerRole);
	}

	public static ManagerRole ROLE(HttpServletRequest request) {
		ManagerRole managerRole = null;
		HttpSession httpSession = request.getSession();
		managerRole = (ManagerRole) httpSession.getAttribute(CommonConstrants.ROLE.getValue());
		return managerRole;
	}

}
