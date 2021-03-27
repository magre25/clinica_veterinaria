package br.com.clinica.model;

public class Especie {
	private String codEsp;
	private String descEsp;
	
	
	public String getCodEsp() {
		return codEsp;
	}
	public void setCodEsp(String codEsp) {
		this.codEsp = codEsp;
	}
	public String getDescEsp() {
		return descEsp;
	}
	public void setDescEsp(String descEsp) {
		this.descEsp = descEsp;
	}
	
	@Override
	public String toString() {
		return this.getDescEsp();
	}
	
	
	

}
