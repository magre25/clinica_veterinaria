package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sql.ConnectionPoolDataSource;
import javax.swing.JComboBox;

import br.com.clinica.model.Especie;

public class EspecieDAO {
	private ClinicaDataSource dataSource = new ClinicaDataSource();
	
	
	public List<Especie> listarEspecie(){
		
		try(Connection conn = dataSource.getConnection()){
			List<Especie> lista = new ArrayList();
			PreparedStatement ps = conn.prepareStatement("select * from especie");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Especie esp = new Especie();
				esp.setCodEsp(rs.getString("codEspecie"));
				esp.setDescEsp(rs.getString("descricaoEspecie"));
				lista.add(esp);
			}
			
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Especie listaCodEspecie(String codEsp) {
		try(Connection conn = dataSource.getConnection()){
			PreparedStatement ps = conn.prepareStatement("select * from especie where codEspecie = ?");
			ps.setString(1, codEsp);
			ResultSet rs = ps.executeQuery();
			
			Especie esp = null;
			while(rs.next()) {
				esp = new Especie();
				esp.setCodEsp(rs.getString("codEspecie"));
				esp.setDescEsp(rs.getString("descricaoEspecie"));
			}
			
			return esp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
	
}
