package br.com.saloes.controllers;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.infra.security.NivelAutenticacaoParaAcessar;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.UsuarioRepository;

@Resource
public class UsuarioController {

	private final Result result;
	private final UsuarioRepository usuarioRepo;
	private final Validator validator;
	
	public UsuarioController(Result result, UsuarioRepository usuarioRepo, Validator validator) {
		this.result = result;
		this.usuarioRepo = usuarioRepo;
		this.validator = validator;
	}
	
	@Get
	@Path("/usuarios")
	public List<Usuario> index() {
		return usuarioRepo.findAll();
	}
	
	@Post
	@Path("/usuarios")
	public void create(Usuario usuario) {
		validator.validate(usuario);
		validator.onErrorUsePageOf(this).newUsuario();
		usuario.setTipoAutenticacao(AutenticacaoType.CLIENTE);
		usuarioRepo.create(usuario);
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/usuarios/new")
	public Usuario newUsuario() {
		return new Usuario();
	}
	
	@Put
	@Path("/usuarios")
	public void update(Usuario usuario) {
		validator.validate(usuario);
		validator.onErrorUsePageOf(this).edit(usuario);
		usuarioRepo.update(usuario);
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/usuarios/{usuario.id}/edit")
	public Usuario edit(Usuario usuario) {
		return usuarioRepo.find(usuario.getId());
	}

	@Get
	@Path("/usuarios/{usuario.id}")
	public Usuario show(Usuario usuario) {
		return usuarioRepo.find(usuario.getId());
	}

	@Delete
	@Path("/usuarios/{usuario.id}")
	public void destroy(Usuario usuario) {
		usuarioRepo.destroy(usuarioRepo.find(usuario.getId()));
		result.redirectTo(this).index();  
	}
	
	@Get
	@Path("/usuarios/createAdmin")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.TODOS)
	public void createAdmin() {
		List<Usuario> admins = usuarioRepo.findTipoDeUsuarioAdmin();
		if (admins == null || admins.size() == 0) {
			Usuario newAdmin = new Usuario();
			newAdmin.setEmail("admin@admin.com");
			newAdmin.setSenha("1234");
			newAdmin.setTipoAutenticacao(AutenticacaoType.ADMIN);
			usuarioRepo.create(newAdmin);
		}
		
		result.redirectTo(IndexController.class).index();  
	}
}
