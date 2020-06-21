package com.alosom.dal.modelos;

public class CondModel {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	private int id;
	private String nome;
	public CondModel(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
}
