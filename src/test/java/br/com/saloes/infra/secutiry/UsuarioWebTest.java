package br.com.saloes.infra.secutiry;

import junit.framework.Assert;

import org.junit.Test;

import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Usuario;

public class UsuarioWebTest {

	@Test
	public void seNaoEstiverAutenticadoNaoDeveTerNivelDeAcesso() {
		UsuarioWeb userWeb = new UsuarioWeb();
		
		Assert.assertFalse(userWeb.ehAdmin());
		Assert.assertFalse(userWeb.ehCliente());
	}
	
	@Test
	public void deveAssegurarAPermissaoSetada() {
		Usuario usuario = new Usuario();
		usuario.setTipoAutenticacao(AutenticacaoType.ADMIN);
		
		UsuarioWeb userWeb = new UsuarioWeb();
		userWeb.setAutenticado(usuario);
		
		Assert.assertTrue(userWeb.ehAdmin());
		Assert.assertFalse(userWeb.ehCliente());

		usuario.setTipoAutenticacao(AutenticacaoType.CLIENTE);
		Assert.assertFalse(userWeb.ehAdmin());
		Assert.assertTrue(userWeb.ehCliente());
	}
}