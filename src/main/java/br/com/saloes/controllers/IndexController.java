package br.com.saloes.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.saloes.domain.UsuarioService;
import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.infra.security.NivelAutenticacaoParaAcessar;
import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Usuario;

@Resource
public class IndexController {
	
	private final Result result;
	private final UsuarioService usuarioService;
	private final UsuarioWeb usuarioWeb;
	
	public IndexController(Result result, UsuarioService usuarioService, UsuarioWeb usuarioWeb) {
		this.result = result;
		this.usuarioService = usuarioService;
		this.usuarioWeb = usuarioWeb;
	}
	
	@Get
	@Path("/")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public void index() {}
	
	@Get
	@Path("/login")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.TODOS)
	public void loginForm() {}
	
	@Post
	@Path("/login")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.TODOS)
	public void login(Usuario usuario) throws IllegalAccessException {
		usuarioService.efetuaLogin(usuario);
		
		if (!usuarioWeb.isAutenticado()) {
			throw new IllegalAccessException("Login e/ou senha são inválidos.");
		}

		if (AutenticacaoType.CLIENTE.equals(usuarioWeb.getUsuario().getTipoAutenticacao()))
			result.redirectTo(ConsultaController.class).index();
		else
			result.redirectTo(IndexController.class).index();
		
	}
	
	@Get
	@Path("/logout")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public void logout() {
		usuarioWeb.setAutenticado(null);
		result.redirectTo(IndexController.class).loginForm();
	}
}