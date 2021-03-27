package br.com.clinica.model;

public class Raca {
	private String codRaca;
	private String descRaca;
	
	
	public String getCodRaca() {
		return codRaca;
	}
	public void setCodRaca(String codRaca) {
		this.codRaca = codRaca;
	}
	public String getDescRaca() {
		return descRaca;
	}
	public void setDescRaca(String descRaca) {
		this.descRaca = descRaca;
	}
	
	@Override
	public String toString() {
		return this.getDescRaca();
	}

}
