package com.alosom.ctl;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import com.alosom.ctl.model.Condo;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

@ManagedBean
public final class IndexBean {
	
	public static final String USER_KEY = "nome_usuario";
	private String usuario;
	public static final String QUERY_TITLE_KEY = "titulo_consulta";
	private String mensagem;
	public static final String QUERY_JSON_KEY = "resultado_consulta";
	private String resultado;
	
	private ArrayList<Condo> condos;

	private int selectedCondo;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public void buscaCondos() {
		try {
			JsonObject jo = ApiCall.buscaCondos(usuario);
			JsonArray json = jo.getAsJsonArray("registros");
			
			condos = new ArrayList<Condo>();
			GsonBuilder gb = new GsonBuilder();
			Gson g = gb.create();
			for (int i = 0; i < json.size(); i++) {
				Condo cd = g.fromJson(json.get(i), Condo.class);
				condos.add(cd);
			}
			selectedCondo = 0;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSelectedCondo() {
		return selectedCondo;
	}

	public void setSelectedCondo(int selectedCondo) {
		this.selectedCondo = selectedCondo;
	}
	
	public int getSelectedCondoId() {
		return condos.get(selectedCondo).getId();
	}
	
	public ArrayList<Condo> getCondos() {
		return condos;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
