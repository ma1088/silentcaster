package com.alosom.dal.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.alosom.dal.db.ConnectionPool;

public class ArduinoMetodos {
	public static String buscaID (String codigo) {
		String sql = "select coalesce(max(\"ID_SENSOR\"),-1) ID from public.tb_sensores where \"COD_SENSOR\" = ?";
		DataSource ds = ConnectionPool.getConnection();
		try {
			Connection cn = ds.getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, codigo);
			ResultSet rs = ps.executeQuery();
			int r = -1;
			while (rs.next()) {
				r = rs.getInt("ID");
			}
			cn.close();
			
			if (r < 0) {
				return codigo;
			}else {
				return r + "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(sql);
		}
		return codigo;
	}
}
