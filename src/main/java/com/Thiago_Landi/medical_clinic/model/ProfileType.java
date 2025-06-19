package com.Thiago_Landi.medical_clinic.model;


public enum ProfileType {
	ADMIN(1, "ADMIN"), DOCTOR(2, "DOCTOR"), PATIENT(3, "PATIENT");
	
	private long cod;
	private String desc;
	
	private ProfileType(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}
	
	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}
