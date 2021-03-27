package br.com.clinica.controller;

import java.util.List;

import javax.swing.JComboBox;

import br.com.clinica.dao.AnimalDAO;
import br.com.clinica.dao.ConsultaDAO;
import br.com.clinica.dao.DataAccessException;
import br.com.clinica.dao.ServicoDAO;
import br.com.clinica.dao.VeterinarioDAO;
import br.com.clinica.model.Animal;
import br.com.clinica.model.Consulta;
import br.com.clinica.model.Servico;
import br.com.clinica.model.Veterinario;

public class ConsultaController {
	
	ConsultaDAO dao = new ConsultaDAO();
	
	
	public boolean cadastrar(Consulta consulta) throws DataAccessException {
		ConsultaDAO dao = new ConsultaDAO();
		if(consulta != null) {
			dao.salvar(consulta);
			return true;
		}else {
			return false;
		}
		
	}
	
	public void carregarAnimal(JComboBox<Animal> cbxAnimal) {
		AnimalDAO dao = new AnimalDAO();
		List<Animal> listaAnimal = dao.listarAnimal();
		cbxAnimal.removeAllItems();
		
		for (Animal a : listaAnimal) {
			cbxAnimal.addItem(a);
		}
		
	}
	
	public void carregarVeterinario(JComboBox<Veterinario> cbxVeterinario) {
		VeterinarioDAO dao = new VeterinarioDAO();
		List<Veterinario> listaVeterinario = dao.listarVeterinario();
		cbxVeterinario.removeAllItems();
		
		for (Veterinario v : listaVeterinario) {
			cbxVeterinario.addItem(v);
		}
	}
	
	public void carregarServico(JComboBox<Servico> cbxServico) {
		ServicoDAO dao = new ServicoDAO();
		List<Servico> listaServico = dao.listarServicos();
		cbxServico.removeAllItems();
		
		for (Servico s : listaServico) {
			cbxServico.addItem(s);
		}
	}
}
 