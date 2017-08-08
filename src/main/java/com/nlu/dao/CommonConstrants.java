package com.nlu.dao;

public enum CommonConstrants {
	// them cau hoi
	// public static final String CAU_HOI = "CAU_HOI";
	//
	// public static final String CHUONG = "CHUONG";
	//
	// public static String USER_SESSION = "USER_SESSION";
	//
	// public static String MODEL_GV = "GV";
	//
	// public static String DE_THI = "DE_THI";
	// public static String ROLE = "ROLE";
	CAU_HOI("CAU_HOI"), CHUONG("CHUONG"), USER_SESSION("USER_SESSION"), MODEL_GV("GV"), DE_THI("DE_THI"), ROLE("ROLE");

	private final String value;

	public String getValue() {
		return value;
	}

	CommonConstrants(String value) {
		this.value = value;
	}
}
