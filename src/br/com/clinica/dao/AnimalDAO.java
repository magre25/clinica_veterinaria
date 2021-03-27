package br.com.clinica.dao;

import java.net.ResponseCache;
import java.net.SecureCacheResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import com.mysql.cj.exceptions.RSAException;

import br.com.clinica.model.Animal;
import br.com.clinica.model.Especie;
import br.com.clinica.model.Raca;
import br.com.clinica.model.Servico;

public class AnimalDAO {
	private ClinicaDataSource dataSource = new ClinicaDataSource();
	
	
	public void cadastrar(Animal animal) throws DataAccessException {
		
		try(Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			
			try(PreparedStatement ps = conn.prepareStatement("INSERT INTO animal (nome, sexo, codRaca, codEspecie) "
					+ "values (?, ?, ?, ?);")){
				ps.setString(1, animal.getNome());
				ps.setString(2, animal.getSexo());
				ps.setString(3, animal.getRaca().getCodRaca());
				ps.setString(4, animal.getEspecie().getCodEsp());
				ps.executeUpdate();
				
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
				// TODO: handle exception
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(e);
		}
		
	}
	
	public List<Animal> listarAnimal(){
		
		try(Connection conn = dataSource.getConnection()){
			List<Animal> lista = new ArrayList();
			PreparedStatement ps = conn.prepareStatement("select * from animal order by nome");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Animal a = new Animal();
				String codigoesp = rs.getString("codEspecie");
				a.setCodAnimal(rs.getInt("codAnimal"));
				a.setNome(rs.getString("nome"));
				a.setSexo(rs.getString("sexo"));
				//retornando valores do objeto especie que é chave primaria na table Animal
				EspecieDAO especieDAO = new EspecieDAO();
				Especie especie = especieDAO.listaCodEspecie(codigoesp);
				a.setEspecie(especie);
				lista.add(a);
			}
			
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
