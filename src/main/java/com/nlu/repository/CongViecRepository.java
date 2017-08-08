package com.nlu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.CongViec;

public interface CongViecRepository extends JpaRepository<CongViec, Integer> {
	@Query(" select c from CongViec c where c.noidungcv like :x ")
	public Page<CongViec> list(@Param("x") String search, Pageable page);

	/**
	 * lang them vao
	 */
	/**
	 * Lấy danh sách công việc từ mã môn nhận vào
	 * 
	 * @param maMon
	 * @return
	 */
	@Query("SELECT c FROM CongViec c WHERE c.mamon = :mamon")
	public List<CongViec> getCongViecByMaMon(@Param("mamon") int maMon);

	/**
	 * thay đổi trạng thái cho công việc
	 * 
	 * @param trangThai
	 *            trạng thái
	 * @param maCV
	 *            mã công việc
	 * @return số dòng thay đổi dữ liệu
	 */
	@Modifying
	@Query("UPDATE CongViec SET trangthai=:trangthai WHERE macv = :macv")
	public int changeTrangThaiCongViec(@Param("trangthai") boolean trangThai, @Param("macv") int maCV);

	@Query("FROM CongViec WHERE magv.magv=:magv")
	public List<CongViec> getListCongViecByMaGV(@Param("magv") int magv);

	@Query("FROM CongViec WHERE maloaicv.maloaicv=:maloaicv")
	public List<CongViec> getListCongViecByMaLoaiCV(@Param("maloaicv") int maLoaiCV);

}
