package com.alosom.ctl.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class RelatorioSom {
	private String area;
	private String andar;
	private String unidade;
	private String intensidade;
	private String data;
	private String erro;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAndar() {
		return andar;
	}
	public void setAndar(String andar) {
		this.andar = andar;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getIntensidade() {
		return intensidade;
	}
	public void setIntensidade(String intensidade) {
		this.intensidade = intensidade;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDataLegivel() {
		Instant instant = Instant.ofEpochSecond(Long.parseLong(data));
		LocalDateTime ldt = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return ldt.getDayOfMonth() + "/" + ldt.getMonthValue() + "/" + ldt.getYear() + " " + ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond();
	}
	public String getErro() {
		return erro;
	}
	public void setErro(String erro) {
		this.erro = erro;
	}
}
