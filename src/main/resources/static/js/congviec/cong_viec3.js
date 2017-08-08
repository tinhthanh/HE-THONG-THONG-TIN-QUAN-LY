// xem danh sách công việc
function chi_tiet_cong_viec(e) {
	var s = e.value;
	var array = s.split("|");
	var maCV = array[0];
	var maLoaiCV = array[1];
	var form_data = {
		maCV : maCV,
		maLoaiCV : maLoaiCV
	}
	switch (maLoaiCV) {
	case '1':
		$.ajax({
			url : '/congviec/chitietcongviec',
			type : 'POST',
			dataType : 'json',
			data : form_data,
			success : function(result) {
				var json = $.parseJSON(JSON.stringify(result));
				console.log(json);
				$("#ma_cong_viec1").val(json['maCongViec']);
				$("#ma_mon1").val(json['maMon']);
				$("#ten_mon1").val(json['tenMonHoc']);
				$("#giao_vien1").val(json['tenGV']);
				$("#loai_cong_viec1").val(json['tenLoaiCongViec']);
				$("#thoi_gian_bat_dau1").val(json['thoiGianBatDau']);
				$("#thoi_gian_ket_thuc_1").val(json['thoiGianKetThuc']);
				$("#noi_dung_cong_viec1").val(json['noiDungcongViec']);
				$('#chi_tiet_cong_viec1').modal('show');
			}

		});

		break;

	case '2':

		$
				.ajax({
					url : '/congviec/chitietcongviec3',
					type : 'POST',
					dataType : 'text',
					data : form_data,
					success : function(result) {
						// var json = $.parseJSON(JSON.stringify(result));
						var json = JSON.parse(result);
						console.log(json);
						$("#ma_cong_viec2").val(maCV);
						$("#ma_mon2").val(json['maMon']);
						$("#ten_mon2").val(json['tenMonHoc']);
						$("#giao_vien2").val(json['tenGV']);
						$("#loai_cong_viec2").val(json['tenLoaiCongViec']);
						$("#thoi_gian_bat_dau2").val(json['thoiGianBatDau']);
						$("#thoi_gian_ket_thuc_2").val(json['thoiGianKetThuc']);
						$("#noi_dung_cong_viec2").val(json['noiDungcongViec']);
						$('#chi_tiet_cong_viec2').modal('show');
						$("#cau_truc_cau_de2").empty();
						for (i = 0; i < json['chuong'].length; i++) {

							$("#cau_truc_cau_de2")
									.append(
											"<label>Chương&emsp; </label><input class='form-control' type='text' readonly='readonly' value='"
													+ json['chuong'][i].tenChuong
													+ "'/><label>&emsp;Số lượng câu hỏi&nbsp; </label><input class='form-control' type='text' readonly='readonly' value='"
													+ json['chuong'][i].soLuongCauHoi
													+ "' /></br>");

						}

						$('#chi_tiet_cong_viec2').modal('show');
					}

				});

		break;

	case '3':
		$.ajax({
			url : '/congviec/chitietcongviec2',
			type : 'POST',
			dataType : 'text',
			data : form_data,
			success : function(result) {
				var json = JSON.parse(result);
				console.log(json);
				$("#ma_cong_viec3").val(json['maCongViec']);
				$("#ma_mon3").val(json['maMon']);
				$("#ten_mon3").val(json['tenMonHoc']);
				$("#giao_vien3").val(json['tenGV']);
				$("#loai_cong_viec3").val(json['tenLoaiCongViec']);
				$("#thoi_gian_bat_dau3").val(json['thoiGianBatDau']);
				$("#thoi_gian_ket_thuc_3").val(json['thoiGianKetThuc']);
				$("#noi_dung_cong_viec3").val(json['noiDungcongViec']);
				$("#so_luong_de_toi_da").val(json['soLuongDeToiDa']);
				$('#chi_tiet_cong_viec3').modal('show');
			}

		});

		break;

	default:
		break;
	}

}
