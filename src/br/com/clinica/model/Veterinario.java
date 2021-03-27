package br.com.clinica.model;

public class Veterinario {
	private String CRVM;
	private String nome;
	private String telefone;
	
	public String getCRVM() {
		return CRVM;
	}
	public void setCRVM(String cRVM) {
		CRVM = cRVM;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
	
}
