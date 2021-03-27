package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.clinica.model.Consulta;

public class ConsultaDAO {
	
	private ClinicaDataSource dataSource = new ClinicaDataSource();
	
	public void salvar(Consulta consulta) throws DataAccessException {
		
		// calculando o valor total do servico 
		double totalCobrado = consulta.getQntd() * consulta.getServico().getPreco();
		consulta.setValorTotal(totalCobrado);
		
		try(Connection conn = dataSource.getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement ps = conn.prepareStatement("INSERT INTO consulta (CRMV, codAnimal, "
					+ "codServico, total) VALUES (?, ?, ?, ?);")){
				ps.setString(1, consulta.getVeterinario().getCRVM());
				ps.setInt(2, consulta.getAnimal().getCodAnimal());
				ps.setString(3, consulta.getServico().getCodigo());
				ps.setDouble(4, consulta.getValorTotal());
				ps.executeUpdate();
				
				conn.commit();
				
			}catch (SQLException e) {
				conn.rollback();
				e.printStackTrace();
				// TODO: handle exception
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(e);
		}
	}
	
	public double calcularPreco(Consulta consulta) {
		return consulta.getQntd() * consulta.getServico().getPreco();
	}
}
