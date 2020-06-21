package com.alosom.dal.modelos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SomModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5138426541758232557L;
	
	public SomModel(LocalDateTime dtRegistro, LocalDateTime dtServidor, int intensidade, String area,
			int andar, String unidade) {
		this.dtRegistro = dtRegistro;
		this.dtServidor = dtServidor;
		this.intensidade = intensidade;
		this.area = area;
		this.andar = andar;
		this.unidade = unidade;
	}

	public LocalDateTime getDtRegistro() {
		return dtRegistro;
	}
	public void setDtRegistro(LocalDateTime dtRegistro) {
		this.dtRegistro = dtRegistro;
	}
	public int getIntensidade() {
		return intensidade;
	}
	public void setIntensidade(int intensidade) {
		this.intensidade = intensidade;
	}
	public LocalDateTime getDtServidor() {
		return dtServidor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getAndar() {
		return andar;
	}
	public void setAndar(int andar) {
		this.andar = andar;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public void setDtServidor(LocalDateTime dtServidor) {
		this.dtServidor = dtServidor;
	}

	private LocalDateTime dtRegistro;
	private LocalDateTime dtServidor;
	private int intensidade;
	private String area;
	private int andar;
	private String unidade;

}
