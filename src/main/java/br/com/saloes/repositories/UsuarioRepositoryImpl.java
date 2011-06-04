package br.com.saloes.repositories;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.models.Usuario;

import com.googlecode.objectify.Objectify;

@Component
public class UsuarioRepositoryImpl extends Repositorio<Usuario>
    implements UsuarioRepository {

	public UsuarioRepositoryImpl(Objectify objectify) {
		super(objectify);
	}

	@Override
	public Usuario findByEmail(String email) {
		return objectify.query(Usuario.class).filter("email", email).get();
	}

	@Override
	public List<Usuario> findTipoDeUsuarioCliente() {
		return objectify.query(Usuario.class).filter("tipoAutenticacao", AutenticacaoType.CLIENTE.name()).list();
	}
	
	@Override
	public List<Usuario> findTipoDeUsuarioAdmin() {
		return objectify.query(Usuario.class).filter("tipoAutenticacao", AutenticacaoType.ADMIN.name()).list();
	}
}
