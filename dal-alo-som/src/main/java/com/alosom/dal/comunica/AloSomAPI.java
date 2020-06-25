package com.alosom.dal.comunica;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alosom.dal.metodos.AloSomMetodos;
import com.alosom.dal.modelos.CondModel;
import com.alosom.dal.modelos.SomModel;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

@Path("/alosom")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class AloSomAPI {

@POST
@Path("/login")
public String login(@FormParam("usuario") String usuario, @FormParam("senha") String senha) {
	boolean l = AloSomMetodos.login(usuario, senha);
	
	JsonObject mainjo = new JsonObject();
	if (!l) {
		mainjo.addProperty("erro", "Falha no Login");
		mainjo.addProperty("msg", "Usuário ou senha inválidos. Tente novamente.");
	} else { //caminho feliz!
		mainjo.addProperty("ok", "Bem-vindo de volta");
		mainjo.addProperty("msg", "Aguarde enquanto buscamos algumas informações.");
	}
	return mainjo.toString();
}

@GET
@Path("/buscacondo/{usuario}")
public String buscaCondo(@PathParam("usuario") String usuario) {
	//TODO desencriptar senha
	//se efetuou login, retornará a lista de condomínios que o usuário pode consultar
	ArrayList<CondModel> ar = AloSomMetodos.buscaCondoUsuario(usuario);
	JsonObject mainjo = new JsonObject();
    //caminho feliz!
	JsonArray ja = new JsonArray();
	for (CondModel cm : ar) {
		JsonObject jo = new JsonObject();
		jo.addProperty("id", cm.getId());
		jo.addProperty("nome", cm.getNome());
		ja.add(jo);
	}
	mainjo.add("registros", ja);
	return mainjo.toString();
}

@GET
@Path("/consulta/{sec}/{id}")
public String consultar(@PathParam("sec") long sec, @PathParam("id") int idCondominio) {
	try {
		ArrayList<SomModel> ar = (ArrayList<SomModel>) AloSomMetodos.busca(Instant.ofEpochSecond(sec)
																				.atZone(ZoneId.of("Z"))
																				.toLocalDateTime(), 
																		idCondominio);
		JsonObject mainjo = new JsonObject();
		JsonArray ja = new JsonArray();
		for (SomModel sm : ar) {
			JsonObject jo = new JsonObject();
			jo.addProperty("area", sm.getArea());
			jo.addProperty("andar", sm.getAndar());
			jo.addProperty("unidade", sm.getUnidade());
			jo.addProperty("intensidade", sm.getIntensidade());
			jo.addProperty("data", sm.getDtRegistro().toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now())));
			jo.addProperty("erro", "0");
			ja.add(jo);
		}
		mainjo.add("registros", ja);
		return mainjo.toString();
	} catch (SQLException e) {
		Gson gson = new Gson();
		ArrayList<String> rt = new ArrayList<String>();
		rt.add("erro" + e.getErrorCode());
		e.printStackTrace();
		return gson.toJson(rt);
	}
}


}
