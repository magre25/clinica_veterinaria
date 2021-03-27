package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.ConnectionGroup;

import br.com.clinica.dao.DataAccessException;
import br.com.clinica.model.Servico;

public class ServicoDAO {
	private ClinicaDataSource dataSource = new ClinicaDataSource();
		
		public void salvar(Servico servico) throws DataAccessException {
			try(Connection conn = dataSource.getConnection()){
				conn.setAutoCommit(false);
				
				try(PreparedStatement ps = conn.prepareStatement("INSERT INTO servicos (codServico,"
						+ " descricaoServicos, precoHora) VALUES (?, ?, ?);")){
					ps.setString(1, servico.getCodigo());
					ps.setString(2, servico.getDescricao());
					ps.setDouble(3, servico.getPreco());
					ps.execute(); 
					
					conn.commit();
					
				} catch (SQLException e) {
					// TODO: handle exception
					conn.rollback();
					throw e;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DataAccessException(e);
			}
		}
		
		public Servico listaCodServico(String codServ) {
		
			try(Connection conn = dataSource.getConnection()){
				try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM servicos WHERE codServico = ?")){
					ps.setString(1, codServ);
					ResultSet rs = ps.executeQuery();
					
					Servico servico = null;
					if(rs.next()) {
						servico = new Servico();
						servico.setCodigo(rs.getString(1));
						servico.setDescricao(rs.getString(2));
						servico.setPreco(rs.getDouble(3));
					}
					
					return servico;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
		
		public void atualizar(Servico servico) throws DataAccessException {
			
			try(Connection conn = dataSource.getConnection()){
				conn.setAutoCommit(false);
				try(PreparedStatement ps = conn.prepareStatement("UPDATE servicos SET descricaoServicos=?,"
						+ " precoHora=?"
						+ "WHERE codServico=?")){
					ps.setString(1, servico.getDescricao());
					ps.setDouble(2, servico.getPreco());
					ps.setString(3, servico.getCodigo());
					ps.executeUpdate();
					
					conn.commit();
					
				}catch (SQLException e) {
					conn.rollback();
					e.printStackTrace();
				}
			} catch (SQLException e) {
				throw new DataAccessException(e);
			}
			
		}
		
		public void excluir(Servico servico) throws DataAccessException {
			
			try(Connection conn = dataSource.getConnection()){
				conn.setAutoCommit(false);
				try(PreparedStatement ps = conn.prepareStatement("DELETE FROM servicos WHERE codServico=?")){
					ps.setString(1, servico.getCodigo());
					ps.executeUpdate();
					
					conn.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DataAccessException(e);
			}
		}
		
		public List<Servico> listarServicos(){
			try(Connection conn = dataSource.getConnection()){
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM servicos order by descricaoServicos");
				ResultSet rs = ps.executeQuery();
				
				List<Servico> lista = new ArrayList();
				while(rs.next()) {
					Servico  serv = new Servico();
					serv.setCodigo(rs.getString("codServico"));
					serv.setDescricao(rs.getString("descricaoServicos"));
					serv.setPreco(rs.getDouble("precoHora"));
					lista.add(serv);
				}
				
				return lista;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
}
