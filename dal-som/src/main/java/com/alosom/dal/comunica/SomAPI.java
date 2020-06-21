package com.alosom.dal.comunica;

import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.alosom.dal.metodos.SomMetodos;
import com.alosom.dal.modelos.ArduinoSomModel;

@Path("/som")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.APPLICATION_JSON)
public class SomAPI {
	@POST
	@Path("/r/{id}/{it}/{ml}")
	public String registrar(@PathParam("id") String id, @PathParam("it") int it, @PathParam("ml")long millis) {
		try {
			SomMetodos.insere(new ArduinoSomModel(id,
											Instant.ofEpochMilli(millis)
													.atZone(ZoneId.systemDefault())
													.toLocalDateTime(),
											it));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{r:0}";
	}
	
}
