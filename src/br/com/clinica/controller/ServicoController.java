package br.com.clinica.controller;

import br.com.clinica.dao.DataAccessException;
import br.com.clinica.dao.ServicoDAO;
import br.com.clinica.model.Servico;

public class ServicoController {
	
	private ServicoDAO dao;
	
	// metodo que recebe os dados(objeto) verifica e realiza a operacao de salvar(DAO)
	public boolean cadastrar(Servico servico) throws DataAccessException {
		dao = new ServicoDAO();
		if(servico != null) {
			dao.salvar(servico);
			return true;
		}else {
			return false; 
		}
	}

}
