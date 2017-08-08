package com.nlu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nlu.dao.entity.CauTrucDeThi;
import com.nlu.dao.entity.DapAn;
import com.nlu.dao.entity.DeThi;

public interface CauTrucDeThiRepository extends JpaRepository<CauTrucDeThi, Integer> {
	@Query("select m.cauTrucDeThi from MonHoc m where m.mamon=:mamon")
	public CauTrucDeThi getCauTrucDeThiByIdMonHoc(@Param("mamon") int maMon);

	@Query("select  c  from CauTrucDeThi c where c.mactdt=:mactdt")
	public CauTrucDeThi getCauTrucDeThiByIdCtdt(@Param("mactdt") int maCtdt);

	@Query("select   ch from  CauHoi ch where mach=:mach")
	public List<DapAn> getListCauHoiByIdCauHoi(@Param("mach") int maCH);

	@Query("select d from DeThi d where d.madethi=:madethi")
	public DeThi getDeThiByIdMaDeThi(@Param("madethi") int maDeThi);

	// @Modifying
	// @Query("delete ChiTietCtdtPK where mactdt=:mactdt and machuong=:machuong
	// and madokho=:madokho")
	// public int xoaCtdt(@Param("mactdt") int maCtdt, @Param("machuong") int
	// maChuong, @Param("madokho") int maDoKho);
	
	
	
	/**
	 * lang them vao
	 */
	
	/**
	 * lấy cấu trúc đề thi bằng mã môn và mã giáo viên
	 * 
	 * @param maMon
	 * @param maGV
	 * @return
	 */
	@Query("FROM CauTrucDeThi WHERE mamon.mamon=:mamon")
	public List<CauTrucDeThi> getCauTrucDeThiByMaMonAndMaGV(@Param("mamon") int maMon);
	
	
}
