package br.com.saloes.repositories;

import java.util.List;

import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;

public interface FuncionarioRepository {
	/*
	 * Delete the methods you don't want to expose
	 */
	 
	void create(Funcionario entity);
	
	void update(Funcionario entity);
	
	void destroy(Funcionario entity);
	
	Funcionario find(Long id);
	
	List<Funcionario> findAll();

	List<Funcionario> findByProfissao(Profissao profissao);

}
