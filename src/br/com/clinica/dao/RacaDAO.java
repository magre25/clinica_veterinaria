package br.com.clinica.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import br.com.clinica.model.Especie;
import br.com.clinica.model.Raca;

public class RacaDAO {

	private ClinicaDataSource dataSource = new ClinicaDataSource();

	public List<Raca> listarRaca() {

		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from raca order by descricaoRaca");
			ResultSet rs = ps.executeQuery();
			
			List<Raca> lista = new ArrayList();
			
			while (rs.next()) {
				Raca raca = new Raca();
				raca.setCodRaca(rs.getString("codRaca"));
				raca.setDescRaca(rs.getString("descricaoRaca"));
				lista.add(raca);
			}

			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
