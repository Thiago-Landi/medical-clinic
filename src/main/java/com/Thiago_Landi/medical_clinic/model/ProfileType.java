package com.Thiago_Landi.medical_clinic.model;


public enum ProfileType {
	ADMIN(1, "ADMIN"), MEDICO(2, "MEDICO"), PACIENTE(3, "PACIENTE");
	
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
