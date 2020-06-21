package com.alosom.dal.modelos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArduinoSomModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5138426541758232557L;
	
	public ArduinoSomModel(String idSensor, LocalDateTime dtRegistro, int intensidade) {
		this.idSensor = idSensor;
		this.dtRegistro = dtRegistro;
		this.intensidade = intensidade;
	}
	public String getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(String idSensor) {
		this.idSensor = idSensor;
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

	private String idSensor;
	private LocalDateTime dtRegistro;
	private int intensidade;

}
