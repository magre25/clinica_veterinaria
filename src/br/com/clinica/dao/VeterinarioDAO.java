package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.clinica.model.Raca;
import br.com.clinica.model.Veterinario;

public class VeterinarioDAO {
	private ClinicaDataSource dataSource = new ClinicaDataSource();
	
	public List<Veterinario> listarVeterinario(){
		
		try(Connection conn = dataSource.getConnection()){
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM veterinario order by nome");
			ResultSet rs = ps.executeQuery();
			
			List<Veterinario> lista = new ArrayList();
			while(rs.next()) {
				Veterinario vet = new Veterinario();
				vet.setCRVM(rs.getString("CRMV"));
				vet.setNome(rs.getString("nome"));
				vet.setTelefone(rs.getString("telefone"));
				lista.add(vet);
			}
			
			return lista;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
