package br.com.saloes.controllers;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.UsuarioRepository;

public class UsuarioControllerTest {

	private MockResult result;
	private UsuarioRepository usuarioRepo;
	private Validator validator;
	private UsuarioController usuarioController;

	@Before
	public void inicia() {
		result = new MockResult();
		usuarioRepo = Mockito.mock(UsuarioRepository.class);
		validator = new MockValidator();
		
		usuarioController = new UsuarioController(result, usuarioRepo, validator);
	}
	
	@Test
	public void soEhPossivelCriarUsuariosDoTipoCliente() {
		Usuario user = new Usuario();
		
		usuarioController.create(user);
		
		Assert.assertEquals(AutenticacaoType.CLIENTE, user.getTipoAutenticacao());
	}
}