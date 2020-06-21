package com.alosom.dal.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.alosom.dal.db.ConnectionPool;
import com.alosom.dal.modelos.CondModel;
import com.alosom.dal.modelos.SomModel;

public class AloSomMetodos {
	public static List<SomModel> busca(LocalDate dtFiltro, int idCondominio) throws SQLException {
		String sql = "select \"DATA_DISP\", \"INTENSIDADE\", \"AREA\", \"ANDAR\", \"UNIDADE\", \"DATA_SERV\" "
				+ "from public.tb_monit_sensores a "
				+ "join public.tb_sensores b "
			    + "on a.\"ID_SENSOR\" = b.\"ID_SENSOR\"::text "
				+ "where DATE_TRUNC('hour',\"DATA_DISP\") = ? "
				+ "and b.\"ID_CONDOMINIO\" = ? "
				+ "order by \"DATA_DISP\", \"INTENSIDADE\";";
		List<SomModel> itens = new ArrayList<SomModel>();
		java.sql.Date sqlDtFiltro = java.sql.Date.valueOf(dtFiltro);
		
		try {
			DataSource ds = ConnectionPool.getConnection();
			Connection cn = ds.getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setDate(1, sqlDtFiltro);
			ps.setInt(2, idCondominio);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int intensidade = rs.getInt("intensidade");
				ZoneOffset offset = ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now());
				LocalDateTime dtRegistro = LocalDateTime.ofEpochSecond(rs.getDate("data_disp").getTime(), 0, offset);
				LocalDateTime dtServidor = LocalDateTime.ofEpochSecond(rs.getDate("data_serv").getTime(), 0, offset);
				String area = rs.getString("area");
				int andar = rs.getInt("andar");
				String unidade = rs.getString("unidade");
				itens.add(new SomModel(dtRegistro, dtServidor, intensidade, area, andar, unidade));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(sql);
			
		}
		
		return itens;
	}
	
	public static ArrayList<CondModel> buscaCondoUsuario (String usuario) {
		String sql = "select \"ID_CONDOMINIO\",\"NOME\" "
					+ "from tb_condominios a join tb_usuarios_condominios b "
					+ "on a.\"ID_CONDOMINIO\" = b.\"id_condominio\" "
					+ "join tb_usuarios c on b.\"id_usuario\" = c.\"id_usuario\" "
					+ "where c.usuario = ?;";
		ArrayList<CondModel> cdms = new ArrayList<CondModel>();

		try {
			DataSource ds = ConnectionPool.getConnection();
			Connection cn = ds.getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, usuario);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				cdms.add(new CondModel(rs.getInt("ID_CONDOMINIO"), rs.getString("NOME")));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return cdms;
	}
	
	public static boolean login (String usuario, String senha) {
		String sql = "select count(*) CT "
					+ "from tb_usuarios "
					+ "where usuario = ? and senha = ?;";
		int ct = 0;

		try {
			DataSource ds = ConnectionPool.getConnection();
			Connection cn = ds.getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setString(1, usuario);
			ps.setString(2, senha);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ct = rs.getInt("CT");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return (ct > 0);
	}

}
