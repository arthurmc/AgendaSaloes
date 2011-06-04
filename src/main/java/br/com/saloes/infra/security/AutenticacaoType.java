package br.com.saloes.infra.security;

import br.com.saloes.models.Usuario;

public enum AutenticacaoType {
	
	TODOS, CLIENTE, ADMIN;

	public boolean ehLiberadoParaO(Usuario usuario) {
		return this.ordinal() <= usuario.getTipoAutenticacao().ordinal();
	}
	
	public String getLabel() {
		return this.name();
	}
	
	public int getNivelAutenticacao() {
		return this.ordinal();
	}
	
}
