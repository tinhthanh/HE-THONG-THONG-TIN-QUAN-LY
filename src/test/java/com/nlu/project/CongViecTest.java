package com.nlu.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nlu.service.BoMonService;
import com.nlu.service.CauTrucDeThiService;
import com.nlu.service.MonHocService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CongViecTest {
	@Autowired
	BoMonService b;
	@Autowired
	MonHocService mhs;
	@Autowired
	CauTrucDeThiService  c;

	@Test
	public void testGetBoMonByTruongKhoa() {
		System.err.println(b.getListMonHocByKhoa(3));
	}

	@Test
	public void testCapNhatTrangthai() {
			System.err.println(mhs.capNhatTienDoCongViec(1, true));
	}
	@Test
	public void testabc(){
		System.err.println(c.capNhatTienDoCauTrucDeThi(2	, true));
	}
}
