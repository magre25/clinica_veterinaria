package br.com.clinica.model;

public class Animal {
	private int codAnimal;
	private String nome;
	private String sexo;
	
	private Especie especie;
	private Raca raca;
	
	public int getCodAnimal() {
		return codAnimal;
	}
	public void setCodAnimal(int codAnimal) {
		this.codAnimal = codAnimal;
	}
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public Especie getEspecie() {
		return especie;
	}
	public void setEspecie(Especie especie) {
		this.especie = especie;
	}
	public Raca getRaca() {
		return raca;
	}
	public void setRaca(Raca raca) {
		this.raca = raca;
	}
	
	
	@Override
	public String toString() {
		return this.getNome();
	}
	
}
