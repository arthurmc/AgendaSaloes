package br.com.saloes.repositories;

import java.util.List;

import br.com.saloes.models.Profissao;

public interface ProfissaoRepository {
	/*
	 * Delete the methods you don't want to expose
	 */
	 
	void create(Profissao entity);
	
	void update(Profissao entity);
	
	void destroy(Profissao entity);
	
	Profissao find(Long id);
	
	List<Profissao> findAll();

	Profissao findByName(String nomeDaProfissao);

}
