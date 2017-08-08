package com.nlu.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nlu.dao.CongViecPROC;
import com.nlu.dao.entity.CongViec;
import com.nlu.dao.entity.CtTaoCauHoi;

public interface CongViecService {
	public Page<CongViec> list(String search, Pageable page);

	/**
	 * kiểm tra giáo viên có quản lý bộ môn đó hay không
	 * 
	 * @param magv
	 *            mã giáo viên
	 * @param mamabomon
	 *            mã bộ
	 * @return true giáo viên quản lý môn học
	 */
	public boolean checkMagvInBoMon(int magv, int mamabomon);

	/**
	 * thêm công việc [mã công việc là 1]
	 * 
	 * @param maMon
	 *            mã môn
	 * @param maLoaiGV
	 *            mã giáo viên
	 * @param maLoaiCV
	 *            mã loại công việc
	 * @param thoiGianBatDau
	 *            thời gian bắt đầu
	 * @param thoiGianKetThuc
	 *            thời gian kết thúc
	 * @param noiDungCV
	 *            nội dung công việc
	 * @return thông báo kết quả
	 */

	public String addCongViec(int maMon, int maGV, int maLoaiCV, String thoiGianBatDau, String thoiGianKetThuc,
			String noiDungCV);

	/**
	 * thêm công việc (tạo đề thi[mã công việc là 3])
	 * 
	 * @param maMon
	 * @param maGV
	 * @param maLoaiCV
	 * @param thoiGianBatDau
	 * @param thoiGianKetThuc
	 * @param noiDungCV
	 * @param soLuongDeToiDa
	 * @return
	 */
	public String addCongViec3(int maMon, int maGV, int maLoaiCV, String thoiGianBatDau, String thoiGianKetThuc,
			String noiDungCV, int soLuongDeToiDa);

	
	/// có chỉnh sửa
	
	/**
	 * thêm công việc [tạo câu hỏi mã công việc là 3]
	 * 
	 * @param maMon
	 * @param maGV
	 * @param maLoaiCV
	 * @param thoiGianBatDau
	 * @param thoiGianKetThuc
	 * @param noiDungCV
	 * @param chuong
	 * @param soLuongCauHoiToiDa
	 * @return
	 */
	public int addCongViec2(int maMon, int maGV, int maLoaiCV, String thoiGianBatDau, String thoiGianKetThuc,
			String noiDungCV);
	
	public int addChiTietCauHoi(int maCV,int maChuong,int soLuong);
	
	
	
	
	

	public List<CongViec> getAllCongViec();

	/**
	 * kiểm tra một tài khoản có phải là trưởng bộ môn không
	 * 
	 * @param maGV
	 * @return trả về danh sách mã bộ môn mà tài khoản là trưởng bộ môn
	 */
	public List<Integer> isTruongBoMon(int maGV);

	/**
	 * lấy danh sách mã môn học mà giáo viên đó làm trưởng bộ môn
	 * 
	 * @param MaGV
	 * @return danh sách mã môn học
	 */
	public List<Integer> getMaMonHocFromTruongBoMon(int MaGV);

	/**
	 * lấy danh sách công việc [trưởng bộ môn] trưởng bộ môn có thể quản lý
	 * 
	 * @param MaMon
	 *            mã môn
	 * @param maGV
	 *            mã giáo viên (trưởng bộ môn)
	 * @return ds công việc
	 */
	public List<CongViec> getListCongViecByMaGV(int maGV);

	/**
	 * lấy ra công việc dựa vào mã công việc
	 * 
	 * @param maCV
	 *            mã công việc
	 * @return
	 */
	public CongViec getCongViecByMaCV(int maCV);

	/**
	 * thay đổi trạng thái công việc
	 * 
	 * @param trangThai
	 *            trạng thái mới
	 * @param maCV
	 *            mã công việc
	 * @return số record bị thay đổi trạng thái
	 */
	public int changeTrangThaiCongViec(int trangThai, int maCV);

	/**
	 * lấy ra
	 * 
	 * @param maCV
	 * @return
	 */
	public List<CtTaoCauHoi> getCtTaoCauHoiByMaCV(int maCV);

	/**
	 * lấy danh sách công việc được phân công
	 * 
	 * @param maGV
	 *            mã giáo viên
	 * @return danh sách công việc mà giáo viên đó được phân công
	 */
	public List<CongViec> getListCongViecDuocPhanCong(int maGV);

	public List<CongViec> getListCongViecByMaMon(int maMon);

	// thanh viết
	// tra vê danh sách CongViecProc cua 1 khoa , hoac 1 bo môn tùy vào mã giảng
	// viên truyền vào có quyền gì
	public List<CongViecPROC> dsCongViecKhoa(int magv) throws SQLException;

	public List<CongViecPROC> dsCongViecBoMon(int magv) throws SQLException;

	public List<CongViec> getListCongViecByMaLoaiCV(int maLoaiCV);

	public boolean tienDoCongViec(int macv) throws SQLException;

}
