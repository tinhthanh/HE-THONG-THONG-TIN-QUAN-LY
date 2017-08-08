package com.nlu.project;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nlu.dao.entity.CauTrucDeThi;
import com.nlu.dao.entity.ChiTietCtdt;
import com.nlu.service.CauTrucDeThiService;
import com.nlu.service.DeThiService;

@RunWith(SpringRunner.class)
@SpringBootTest

@Transactional
public class TestCtdt {
	@Autowired
	CauTrucDeThiService cauTrucDeThiS;
	@Autowired
	DeThiService deThiS;

	@Test
	public void testGetCauTrucDeThi() {
		CauTrucDeThi cauTrucDeThi = cauTrucDeThiS.getCauTrucDeThiByIdMonHoc(2);
		for (ChiTietCtdt chiTietCtdt : cauTrucDeThi.getChiTietCtdtList()) {
			System.out.println(chiTietCtdt.getChuongMuc().getTieude());
		}
	}

	@Test
	public void testXoaCtdt() {
		System.out.println(cauTrucDeThiS.xoaCtCtdt(1, 1, 1));
	}

	@Test
	public void testGetListChuongInCtdtById() {
		try {
			System.out.println(cauTrucDeThiS.submit(1));
			;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetListDeThiByMaMonHoc() {
	//	System.out.println( deThiS.getListDeThiByMaMonHoc(1));
	}

}
