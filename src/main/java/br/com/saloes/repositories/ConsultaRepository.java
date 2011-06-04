package br.com.saloes.repositories;

import java.util.Date;
import java.util.List;

import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Usuario;

public interface ConsultaRepository {
	/*
	 * Delete the methods you don't want to expose
	 */
	 
	void create(Consulta entity);
	
	void update(Consulta entity);
	
	void destroy(Consulta entity);
	
	Consulta find(Long id);
	
	List<Consulta> findAll();
	
	List<Consulta> findConsultasDeUmFuncionario(Funcionario funcionarioSelecionado);

	List<Consulta> findConsultasDeUmFuncionarioDeUmDeterminadoDia(Funcionario funcionarioSelecionado, Date dia);

	List<Consulta> findConsultasDeUmCliente(Usuario usuario);

}
