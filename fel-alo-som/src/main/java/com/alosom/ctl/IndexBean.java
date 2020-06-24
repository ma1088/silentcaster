package com.alosom.ctl;

import java.io.IOException;

import javax.faces.bean.ManagedBean;

import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

@ManagedBean
public final class IndexBean {
	
	public static final String USER_KEY = "usuario";

	private String loginMsg;
	private String usuario;
	private String senha;
	private String mensagem;

	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getLoginMsg() {
		return loginMsg;
	}

	public void setLoginMsg(String loginMsg) {
		this.loginMsg = loginMsg;
	}
	
	
	public void apiCall() {
		JsonObject jo = null;
		try {
			jo = ApiCall.login(usuario, senha);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		IndexBean bean = new IndexBean();
		JsonElement je = jo.get("erro");
		bean.setLoginMsg(je.getAsString());
		
	}
	

}
