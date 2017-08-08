package com.nlu.dao;

public class Role implements Comparable<Role> {
	private int roleName;
	private String describe;

	public Role(int roleName, String describe) {
		this.roleName = roleName;
		this.describe = describe;
	}

	public int getRoleName() {
		return roleName;
	}

	public void setRoleName(int roleName) {
		this.roleName = roleName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Override
	public int compareTo(Role o) {
		return this.getRoleName() - o.getRoleName();
	}

}
