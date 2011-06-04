package br.com.saloes.domain;

import java.util.logging.Logger;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.controllers.IndexController;
import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.UsuarioRepository;

@Component
public class UsuarioServiceImpl implements UsuarioService {

	private static Logger LOG = Logger.getLogger(IndexController.class.getSimpleName());
	
	private final UsuarioRepository usuarioRepo;
	private final UsuarioWeb usuarioWeb;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepo, UsuarioWeb usuarioWeb) {
		this.usuarioRepo = usuarioRepo;
		this.usuarioWeb = usuarioWeb;
	}
	
	@Override
	public void efetuaLogin(Usuario usuario) {
		Usuario existe = usuarioRepo.findByEmail(usuario.getEmail());

		if (usuario.equals(existe)) {
			LOG.info("autenticado true");
			usuarioWeb.setAutenticado(existe);
		}
	}


}
