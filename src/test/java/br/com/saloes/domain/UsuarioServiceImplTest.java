package br.com.saloes.domain;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.UsuarioRepository;

public class UsuarioServiceImplTest {

	private UsuarioRepository usuarioRepo;
	private UsuarioWeb usuarioWeb;
	private UsuarioService usuarioService;
	
	@Before
	public void inicia() {
		usuarioRepo = Mockito.mock(UsuarioRepository.class);
		usuarioWeb = new UsuarioWeb();
		usuarioService = new UsuarioServiceImpl(usuarioRepo, usuarioWeb);
	}
	
	@Test
	public void seEmailESenhaInformadosTiveremCadastradosUsuarioPassaNoLogin() {
		Usuario usuario = new Usuario();
		usuario.setEmail("armoucar@gmail.com");
		usuario.setEmail("1234");

		Usuario usuario2 = new Usuario();
		usuario2.setEmail("armoucar@gmail.com");
		usuario2.setEmail("1234");
		
		Mockito.when(usuarioRepo.findByEmail(usuario.getEmail())).thenReturn(usuario2);

		usuarioService.efetuaLogin(usuario);
		
		Assert.assertTrue(usuarioWeb.isAutenticado());
	}
	
	@Test
	public void seEmailESenhaInformadosNaoExistirem_NaoPassaNoLogin() {
		Usuario usuario = new Usuario();
		usuario.setEmail("armoucar@gmail.com");
		usuario.setSenha("1234");

		Mockito.when(usuarioRepo.findByEmail(usuario.getEmail())).thenReturn(null);

		usuarioService.efetuaLogin(usuario);
	}
}