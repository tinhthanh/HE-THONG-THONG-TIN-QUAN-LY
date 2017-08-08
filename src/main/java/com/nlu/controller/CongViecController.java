package com.nlu.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.nlu.dao.Chuong;
import com.nlu.dao.ChuongDAO;
import com.nlu.dao.CongViecDAO;
import com.nlu.dao.ManagerRole;
import com.nlu.dao.entity.BoMon;
import com.nlu.dao.entity.CauTrucDeThi;
import com.nlu.dao.entity.ChuongMuc;
import com.nlu.dao.entity.CongViec;
import com.nlu.dao.entity.CtTaoCauHoi;
import com.nlu.dao.entity.GiangVien;
import com.nlu.dao.entity.MonHoc;
import com.nlu.service.BoMonService;
import com.nlu.service.CauTrucDeThiService;
import com.nlu.service.CongViecService;
import com.nlu.service.LoaiCongViecService;
import com.nlu.service.MonHocService;

@Controller
@RequestMapping(value = "/congviec")
public class CongViecController {
	@Autowired
	CongViecService congViecService;
	@Autowired
	BoMonService boMonService;
	@Autowired
	MonHocService monHocService;
	@Autowired
	LoaiCongViecService loaiCongViecService;
	@Autowired
	CauTrucDeThiService cauTrucDeService;

	@GetMapping("")
	public String Index(HttpServletRequest request, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "Search", defaultValue = "") String Search, HttpSession session, Model model) {
		GiangVien gv = ManagerSession.userAdmin(request);
		if (gv == null) {
			return "login";
		} else {
			Page<CongViec> pageProduct = congViecService.list("%" + Search + "%", new PageRequest(p, 2));
			int pagesCount = pageProduct.getTotalPages();

			int[] pages = new int[pagesCount];
			for (int i = 0; i < pagesCount; i++)
				pages[i] = i;
			model.addAttribute("page", pages);
			model.addAttribute("list", pageProduct);
			model.addAttribute("pageIndex", p);
			model.addAttribute("Search", Search);
			return "congviec/congviec";
		}
	}

	@RequestMapping(value = "/phancong")
	public String phanCong(HttpServletRequest request, Model model) {
		GiangVien gv = ManagerSession.userAdmin(request);
		if (gv == null) {
			return "login";
		} else {
			model.addAttribute("listBoMon", boMonService.getListBoMonByMaGV(gv.getMagv()));
			model.addAttribute("listLoaiCongViec", loaiCongViecService.getAll());
		}

		return "congviec/cong_viec";
	}

	/**
	 * ajax lấy danh sách môn học từ mã bộ môn
	 * 
	 * @param maBoMon
	 *            mã bộ môn
	 * @return
	 * @throws SQLException
	 */
	@PostMapping(value = "/LayMonHoc")
	@ResponseBody
	public List<com.nlu.dao.MonHoc> getMonHoc(@RequestParam("ma_bo_mon") String maBoMon,
			@RequestParam("ma_cong_viec") int maLoaiCongViec) throws SQLException {
		BoMon boMon = boMonService.getBoMonbyMaBoMom(Integer.parseInt(maBoMon));
		List<MonHoc> listMonHoc = boMon.getMonHocList();

		System.err.println(listMonHoc.size());
		List<CongViec> listCongViecByMaLoaiCv = congViecService.getListCongViecByMaLoaiCV(maLoaiCongViec);
		if (!listMonHoc.isEmpty()) {
			if (maLoaiCongViec != 2) {
				for (int i = 0; i < listMonHoc.size(); i++) {
					for (CongViec congViec : listCongViecByMaLoaiCv) {
						if (listMonHoc.get(i).getMamon() == congViec.getMamon() && !congViec.getTrangthai()) {
							System.err.println(listMonHoc.get(i).getTenmon());
							listMonHoc.remove(i);
						}
					}
				}
			}
		}
		List<com.nlu.dao.MonHoc> listResult = new ArrayList<>();

		if (!listMonHoc.isEmpty()) {
			for (MonHoc monHoc : listMonHoc) {
				// if (!monHoc.getTrangthai()) {
				listResult.add(new com.nlu.dao.MonHoc(monHoc.getMamon(), monHoc.getTenmon()));
				// }
			}
		}
		return listResult;
	}

	/**
	 * ajax lấy giảng viên phụ trách môn học từ mã môn học nhận vào
	 * 
	 * @param maMonHoc
	 * @return
	 */
	@PostMapping(value = "/GiangVienPhuTrach")
	@ResponseBody
	public List<com.nlu.dao.GiangVien> getListGiangVien(@RequestParam("ma_mon_hoc") String maMonHoc) {
		List<com.nlu.dao.GiangVien> result = new ArrayList<>();
		MonHoc m = monHocService.getMonHocByMaMonHoc(Integer.parseInt(maMonHoc));
		List<GiangVien> listGiangVien = m.getGiangVienList();
		for (GiangVien giangVien : listGiangVien) {
			result.add(new com.nlu.dao.GiangVien(giangVien.getMagv(), giangVien.getHogv(), giangVien.getTengv()));
		}

		return result;
	}

	@RequestMapping(value = "/layChuongCuaMon", method = RequestMethod.POST)
	@ResponseBody
	public List<Chuong> LayChuongCuaMonHoc(@RequestParam("ma_mon_hoc") int maMonHoc) {
		MonHoc m = monHocService.getMonHocByMaMonHoc(maMonHoc);
		List<ChuongMuc> listChuongMuc = m.getChuongMucList();
		List<Chuong> result = new ArrayList<>();
		for (ChuongMuc chuongMuc : listChuongMuc) {
			result.add(new Chuong(chuongMuc.getMachuong(), chuongMuc.getTieude()));
		}
		return result;
	}

	@RequestMapping(value = "/congviecthuong", method = RequestMethod.POST)
	@ResponseBody
	public String taoDeCuong(@RequestParam(value = "ds_giang_vien[]") String[] dsGiangVien, WebRequest wr,
			HttpServletRequest request) throws ParseException {

		String maMonHoc = wr.getParameter("ma_mon_hoc");
		String thoiGianBatDau = wr.getParameter("thoi_gian_bat_dau");
		String thoiGianKetThuc = wr.getParameter("thoi_gian_ket_thuc");
		String noiDungCongViec = wr.getParameter("noi_dung_cong_viec");
		String message = "";
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date thoiGianBatdau2 = null;
		Date thoiGianKetThuc2 = null;
		try {
			thoiGianBatdau2 = formatter.parse(thoiGianBatDau);
			thoiGianKetThuc2 = formatter.parse(thoiGianKetThuc);

		} catch (Exception e) {
		}
		if (thoiGianBatdau2.after(thoiGianKetThuc2)) {
			message = "Thời gian bắt đầu phải trước thời gian kết thúc";
			return message;
		} else {

			for (int i = 0; i < dsGiangVien.length; i++) {
				message = congViecService.addCongViec(Integer.parseInt(maMonHoc), Integer.parseInt(dsGiangVien[i]), 1,
						thoiGianBatDau, thoiGianKetThuc, noiDungCongViec);

			}

			return message;
		}
	}

	@PostMapping(value = "/taodecuong")
	@ResponseBody
	public String taoDeCuong(@RequestParam(value = "ds_giang_vien[]") String[] dsGiangVien, WebRequest wr) {

		String maMonHoc = wr.getParameter("ma_mon_hoc");
		String thoiGianBatDau = wr.getParameter("thoi_gian_bat_dau");
		String thoiGianKetThuc = wr.getParameter("thoi_gian_ket_thuc");
		String noiDungCongViec = wr.getParameter("noi_dung_cong_viec");
		String message = "";
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date thoiGianBatdau2 = null;
		Date thoiGianKetThuc2 = null;
		String soLuongDeToiDa = wr.getParameter("so_luong_de_toi_da");
		try {
			thoiGianBatdau2 = formatter.parse(thoiGianBatDau);
			thoiGianKetThuc2 = formatter.parse(thoiGianKetThuc);

		} catch (Exception e) {
		}
		if (thoiGianBatdau2.after(thoiGianKetThuc2)) {
			message = "Thời gian bắt đầu phải trước thời gian kết thúc";
			return message;
		} else {

			for (int i = 0; i < dsGiangVien.length; i++) {
				message = congViecService.addCongViec3(Integer.parseInt(maMonHoc), Integer.parseInt(dsGiangVien[i]), 3,
						thoiGianBatDau, thoiGianKetThuc, noiDungCongViec, Integer.parseInt(soLuongDeToiDa));

			}

			return message;
		}

	}

	@RequestMapping(value = "/taocauhoi")
	@ResponseBody
	public String taoCauHoi(WebRequest wr, @RequestParam(value = "ds_giang_vien[]") String[] dsGiangVien,
			@RequestParam("chuong_cua_mon[]") String[] dsChuong) {
		String maMonHoc = wr.getParameter("ma_mon_hoc");
		String thoiGianBatDau = wr.getParameter("thoi_gian_bat_dau");
		String thoiGianKetThuc = wr.getParameter("thoi_gian_ket_thuc");
		String noiDungCongViec = wr.getParameter("noi_dung_cong_viec");
		String message = "";
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date thoiGianBatdau2 = null;
		Date thoiGianKetThuc2 = null;
		String soLuongCauHoiToiDa = wr.getParameter("so_luong_cau_hoi");
		try {
			thoiGianBatdau2 = formatter.parse(thoiGianBatDau);
			thoiGianKetThuc2 = formatter.parse(thoiGianKetThuc);

		} catch (Exception e) {
		}
		if (thoiGianBatdau2.after(thoiGianKetThuc2)) {
			message = "Thời gian bắt đầu phải trước thời gian kết thúc";
			return message;
		} else {
			int maCV = 0;
			int result = 0;
			System.err.println(dsGiangVien.length);
			for (int i = 0; i < dsGiangVien.length; i++) {
				maCV = congViecService.addCongViec2(Integer.parseInt(maMonHoc), Integer.parseInt(dsGiangVien[i]), 2,
						thoiGianBatDau, thoiGianKetThuc, noiDungCongViec);
				for (int j = 0; j < dsChuong.length; j++) {
					result = congViecService.addChiTietCauHoi(maCV, Integer.parseInt(dsChuong[j]),
							Integer.parseInt(soLuongCauHoiToiDa));
				}

			}

			return (result > 0) ? "Thêm thành công" : "Thêm thất bại";
		}
	}

	/**
	 * xem công danh sách công việc của bộ môn
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/danhsachcongviec")
	public String danhSachCongviec(HttpServletRequest request, Model model) {
		GiangVien gv = ManagerSession.userAdmin(request);
		if (gv == null) {
			return "login";
		} else {
			int maGv = gv.getMagv();
			// kiểm tra tài khoản là trưởng bộ môn
			List<Integer> dsBoMon = congViecService.isTruongBoMon(maGv);
			if (dsBoMon.isEmpty() || dsBoMon == null) {
				// không phải là trưởng bộ môn của bất kì trang nào thì điều
				// hướng ra ngoài
				return "login";
			} else {
				// danh sách công việc mà tài khoản đó quản lý (trưởng bộ môn)
				List<CongViec> dsCongViec = congViecService.getListCongViecByMaGV(maGv);
				List<CongViecDAO> dsCongViecDao = new ArrayList<>();
				boolean trangThaiCuaNguoiNhanViec = false;
				for (CongViec congViec : dsCongViec) {
					CongViecDAO cvd = new CongViecDAO(
							monHocService.getMonHocByMaMonHoc(congViec.getMamon()).getTenmon(),
							congViec.getMagv().getMagv(),
							congViec.getMagv().getHogv() + " " + congViec.getMagv().getTengv(),
							congViec.getMaloaicv().getTenloaicv(), congViec.getMaloaicv().getMaloaicv(),
							congViec.getMacv(), congViec.getTrangthai());

					switch (congViec.getMaloaicv().getMaloaicv()) {
					case 1:
						// công việc tạo đề cương
						trangThaiCuaNguoiNhanViec = monHocService.getMonHocByMaMonHoc(congViec.getMamon())
								.getTrangthai();
						break;

					case 2:
						// công việc tạo câu hỏi

						// ??
						break;

					case 3:
						// tạo cấu trúc đề

						trangThaiCuaNguoiNhanViec = cauTrucDeService.getCauTrucDeThiByIdMonHoc(congViec.getMamon())
								.getTrangthai();
						break;

					default:
						break;
					}

					// set lại trạng thái công việc của người nhận việc
					cvd.setTrangThaiCuaNguoiNhanViec(trangThaiCuaNguoiNhanViec);

					dsCongViecDao.add(cvd);

				}
				model.addAttribute("dsCongViec", dsCongViecDao);
				return "/congviec/cong_viec_pc";
			}

		}
	}

	/**
	 * xem danh sách công việc của trưởng khoa
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/danhsachcongviec2")
	public String danhSachCongviec2(HttpServletRequest request, Model model) {
		GiangVien gv = ManagerSession.userAdmin(request);
		if (gv == null) {
			return "login";
		} else {
			int maGv = gv.getMagv();
			// kiểm tra tài khoản là trưởng khoa
			ManagerRole m = ManagerSession.ROLE(request);
			if (m.getCurrentRole().getRoleName() == 1) {
				List<Integer> dsMonHoc = boMonService.getListMonHocByKhoa(maGv);
				List<CongViec> listCongviec = new ArrayList<>();

				List<CongViecDAO> dsCongViecDao = new ArrayList<>();
				if (!dsMonHoc.isEmpty()) {
					for (Integer integer : dsMonHoc) {

						List<CongViec> dsCongViec = congViecService.getListCongViecByMaMon(integer);
						if (!dsCongViec.isEmpty()) {
							listCongviec.addAll(dsCongViec);
						}
					}
					for (CongViec congViec : listCongviec) {
						dsCongViecDao
								.add(new CongViecDAO(monHocService.getMonHocByMaMonHoc(congViec.getMamon()).getTenmon(),
										congViec.getMagv().getMagv(),
										congViec.getMagv().getHogv() + " " + congViec.getMagv().getTengv(),
										congViec.getMaloaicv().getTenloaicv(), congViec.getMaloaicv().getMaloaicv(),
										congViec.getMacv(), congViec.getTrangthai()));
					}
					model.addAttribute("dsCongViec", dsCongViecDao);
				}
			}
		}
		return "/congviec/cong_viec_pc";
	}

	/**
	 * chi tiết công việc của công việc thường
	 * 
	 * @param maCongViec
	 * @param maLoaiCV
	 * @return
	 */
	@RequestMapping(value = "/chitietcongviec", method = RequestMethod.POST)
	@ResponseBody
	public CongViecDAO chiTietCongViec(@RequestParam("maCV") int maCongViec, @RequestParam("maLoaiCV") int maLoaiCV) {
		CongViec c = congViecService.getCongViecByMaCV(maCongViec);
		CongViecDAO cD = new CongViecDAO(c.getMamon(), monHocService.getMonHocByMaMonHoc(c.getMamon()).getTenmon(),
				c.getMagv().getMagv(), c.getMagv().getHogv() + " " + c.getMagv().getTengv(),
				c.getMaloaicv().getTenloaicv(), c.getMaloaicv().getMaloaicv(), c.getMacv(), c.getTrangthai(),
				c.getNoidungcv(), c.getTgbabtdau(), c.getTgketthuc());
		return cD;
	}

	/**
	 * chi tiết công việc của tạo đề cương
	 * 
	 * @param maCongViec
	 * @param maLoaiCV
	 * @return
	 */
	@RequestMapping(value = "/chitietcongviec2", method = RequestMethod.POST)
	@ResponseBody
	public CongViecDAO chiTietCongViec2(@RequestParam("maCV") int maCongViec, @RequestParam("maLoaiCV") int maLoaiCV) {
		List<CtTaoCauHoi> ct = congViecService.getCtTaoCauHoiByMaCV(maCongViec);
		CongViec congViec = congViecService.getCongViecByMaCV(maCongViec);
		List<ChuongDAO> chuong = new ArrayList<>();
		if (!ct.isEmpty() || ct != null) {
			for (CtTaoCauHoi c : ct) {
				chuong.add(new ChuongDAO(c.getChuongMuc().getMachuong(), c.getChuongMuc().getTieude(), c.getSl()));
			}
		}
		CongViecDAO cvd = new CongViecDAO(congViec.getMamon(),
				monHocService.getMonHocByMaMonHoc(congViec.getMamon()).getTenmon(), congViec.getMagv().getMagv(),
				congViec.getMagv().getHogv() + " " + congViec.getMagv().getTengv(),
				congViec.getMaloaicv().getTenloaicv(), congViec.getMaloaicv().getMaloaicv(), congViec.getMacv(),
				congViec.getTrangthai(), congViec.getNoidungcv(), congViec.getTgbabtdau(), congViec.getTgketthuc(),
				chuong);
		
		return cvd;
	}

	/**
	 * thai đổi trạng thái công việc
	 */
	@RequestMapping(value = "thaidoitrangthai", method = RequestMethod.POST)
	@ResponseBody
	public String thayDoiTrangThaiCongViec(@RequestParam("trang_thai") int trangThai, @RequestParam("maCV") int maCV) {
		if (congViecService.changeTrangThaiCongViec(trangThai, maCV) > 0) {
			return "Thành công";
		} else {
			return "Thất bại";
		}

	}

	@RequestMapping(value = "chitietcongviec3", method = RequestMethod.POST)
	@ResponseBody
	public CongViecDAO chiTietCongViec3(@RequestParam("maCV") int maCongViec, @RequestParam("maLoaiCV") int maLoaiCV) {
		CongViec congViec = congViecService.getCongViecByMaCV(maCongViec);
		int maMon = congViec.getMamon();
		int maGV = congViec.getMagv().getMagv();
		List<CauTrucDeThi> cauTrucDe = cauTrucDeService.getCauTrucDeThiByMaMonAndMaGV(maMon, maGV);
		CauTrucDeThi c = cauTrucDe.get(0);
		int soLuongDeToiDa = c.getSldetoida();

		CongViecDAO cvd = new CongViecDAO(congViec.getMamon(),
				monHocService.getMonHocByMaMonHoc(congViec.getMamon()).getTenmon(), congViec.getMagv().getMagv(),
				congViec.getMagv().getHogv() + " " + congViec.getMagv().getTengv(),
				congViec.getMaloaicv().getTenloaicv(), congViec.getMaloaicv().getMaloaicv(), congViec.getMacv(),
				congViec.getTrangthai(), congViec.getNoidungcv(), congViec.getTgbabtdau(), congViec.getTgketthuc(),
				soLuongDeToiDa);
		return cvd;

	}
	/*
	 * user thường
	 */

	@RequestMapping(value = "congviecduocphancong")
	public String congViecduocPhanCong(HttpServletRequest request, Model model) {
		GiangVien gv = ManagerSession.userAdmin(request);
		if (gv == null) {
			return "login";
		} else {
			List<CongViec> dsCongViec = congViecService.getListCongViecDuocPhanCong(gv.getMagv());

			List<CongViecDAO> dsCongViecDao = new ArrayList<>();
			model.addAttribute("tenGV", gv.getHogv() + " " + gv.getTengv());
			for (CongViec congViec : dsCongViec) {
				CongViecDAO tmp = new CongViecDAO(monHocService.getMonHocByMaMonHoc(congViec.getMamon()).getTenmon(),
						congViec.getMagv().getMagv(),
						congViec.getMagv().getHogv() + " " + congViec.getMagv().getTengv(),
						congViec.getMaloaicv().getTenloaicv(), congViec.getMaloaicv().getMaloaicv(), congViec.getMacv(),
						congViec.getTrangthai());
				tmp.setMaMon(congViec.getMamon().intValue());
				dsCongViecDao.add(tmp);
			}
			model.addAttribute("dsCongViec", dsCongViecDao);
			return "/congviec/cong_viec_duoc_phan_cong";
		}
	}

	@PostMapping(value = "thaydoitiendocongviec")
	@ResponseBody
	public String thayDoiTienDoCongViec(@RequestParam("maCV") int maCV, @RequestParam("trang_thai") int trangThai) {
		System.err.println(maCV);
		System.err.println(trangThai);
		CongViec c = congViecService.getCongViecByMaCV(maCV);
		System.err.println(c);
		int maLoaiCongViec = c.getMaloaicv().getMaloaicv();
		int maMon = c.getMamon();

		int ketQuaCapNhat = 0;
		boolean trangThaiMoi = (trangThai == 1) ? true : false;
		switch (maLoaiCongViec) {
		case 1:
			// tạo đề cương
			ketQuaCapNhat = monHocService.capNhatTienDoCongViec(maMon, trangThaiMoi);
			break;
		case 2:
			// tạo câu hỏi
			// ????
			return maCV + "";
		case 3:
			// tạo cấu trúc đề
			ketQuaCapNhat = cauTrucDeService.capNhatTienDoCauTrucDeThi(maMon, trangThaiMoi);

			break;

		default:
			break;
		}

		return (ketQuaCapNhat > 0) ? "Cập nhât thành công" : "Cập nhật thất bại";

	}

	@RequestMapping(value = "/tien-do-cong-viec", method = RequestMethod.POST)
	@ResponseBody
	public boolean tienDoCongViec(int macv) throws SQLException {
		boolean trangThai = congViecService.tienDoCongViec(macv);
		return trangThai;
	}

}
