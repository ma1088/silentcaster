package com.alosom.dal.metodos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.alosom.dal.db.ConnectionPool;
import com.alosom.dal.modelos.ArduinoSomModel;

public class SomMetodos {
	public static void insere(ArduinoSomModel sm) throws SQLException {
		DataSource ds = ConnectionPool.getConnection();
		Connection cn = ds.getConnection();
		PreparedStatement ps = cn.prepareStatement("insert into public.tb_monit_sensores "
												 + "(\"ID_SENSOR\", \"INTENSIDADE\", \"DATA_DISP\")"
													+ " values (?,?,?)");
		ps.setString(1, sm.getIdSensor());
		ps.setInt(2, sm.getIntensidade());
		ps.setDate(3, Date.valueOf(sm.getDtRegistro().toLocalDate()));
		ps.execute();
	}
	
}
