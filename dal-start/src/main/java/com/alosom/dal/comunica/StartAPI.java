package com.alosom.dal.comunica;

import java.time.Instant;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alosom.dal.metodos.ArduinoMetodos;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

@Path("/start")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class StartAPI {

	@GET
	@Path("s/{1}")
	public String setArduino(@PathParam("1") String hashcode) {
		JsonObject jo = new JsonObject();
		jo.addProperty("h", Instant.now().toEpochMilli());
		jo.addProperty("d", ArduinoMetodos.buscaID(hashcode));
		return jo.toString();
	}
}
