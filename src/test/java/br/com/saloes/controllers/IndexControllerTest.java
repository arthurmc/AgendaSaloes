package br.com.saloes.controllers;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.vraptor.Result;
import br.com.saloes.domain.UsuarioService;
import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Usuario;

public class IndexControllerTest {

	private Result result;
	private UsuarioService usuarioService;
	private UsuarioWeb usuarioWeb;
	private IndexController indexController;
	
	@Before
	public void inicia() {
		result = Mockito.mock(Result.class);
		usuarioService = Mockito.mock(UsuarioService.class);
		usuarioWeb = Mockito.mock(UsuarioWeb.class);
		indexController = new IndexController(result, usuarioService, usuarioWeb);
	}
	
	@Test(expected=IllegalAccessException.class)
	public void seAutenticacaoFalharDeveLancarExcecao() throws IllegalAccessException {
		Mockito.when(usuarioWeb.isAutenticado()).thenReturn(false);
		Usuario usuario = Mockito.mock(Usuario.class);
		IndexController indexControllerMock = Mockito.mock(IndexController.class);
		
		Mockito.when(result.on(IllegalAccessException.class)).thenReturn(result);
		Mockito.when(result.redirectTo(indexController)).thenReturn(indexControllerMock);
		
		indexController.login(usuario);
	}
	
	@Test
	public void testaLogout() {
		UsuarioWeb userWeb = new UsuarioWeb();
		userWeb.setAutenticado(new Usuario());

		Mockito.when(result.redirectTo(IndexController.class)).thenReturn(indexController);
		
		Assert.assertTrue(userWeb.isAutenticado());
		
		indexController = new IndexController(result, usuarioService, userWeb);
		indexController.logout();
		
		Assert.assertFalse(userWeb.isAutenticado());
	}
}