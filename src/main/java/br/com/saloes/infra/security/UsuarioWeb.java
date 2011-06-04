package br.com.saloes.infra.security;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.saloes.models.Usuario;

@Component
@SessionScoped
public class UsuarioWeb implements Serializable {

	private static final long serialVersionUID = 7978915382111871195L;
	
	private Usuario usuario;

	public boolean isAutenticado() {
		return this.usuario != null;
	}

	public void setAutenticado(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void logout() {
		this.usuario = null;
	}

	public boolean ehAdmin() {
		return isAutenticado() && AutenticacaoType.ADMIN.equals(getUsuario().getTipoAutenticacao());
	}

	public boolean ehCliente() {
		return isAutenticado() && AutenticacaoType.CLIENTE.equals(getUsuario().getTipoAutenticacao());
	}
	
	
}