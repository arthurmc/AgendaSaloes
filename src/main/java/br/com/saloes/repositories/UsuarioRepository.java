package br.com.saloes.repositories;

import java.util.List;

import br.com.saloes.models.Usuario;

public interface UsuarioRepository {
	/*
	 * Delete the methods you don't want to expose
	 */
	 
	void create(Usuario entity);
	
	void update(Usuario entity);
	
	void destroy(Usuario entity);
	
	Usuario find(Long id);
	
	List<Usuario> findAll();

	Usuario findByEmail(String nome);
	
	List<Usuario> findTipoDeUsuarioCliente();

	List<Usuario> findTipoDeUsuarioAdmin();

}
