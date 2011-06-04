package br.com.saloes.infra.secutiry;

import junit.framework.Assert;

import org.junit.Test;

import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.models.Usuario;

public class AutenticacaoTypeTest {

	@Test
	public void deveGarantirAOrdemDosNiveisDeAcesso() {
		Usuario user = new Usuario();

		user.setTipoAutenticacao(AutenticacaoType.ADMIN);
		Assert.assertTrue(AutenticacaoType.ADMIN.ehLiberadoParaO(user));
		Assert.assertTrue(AutenticacaoType.CLIENTE.ehLiberadoParaO(user));
		Assert.assertTrue(AutenticacaoType.TODOS.ehLiberadoParaO(user));
		
		user.setTipoAutenticacao(AutenticacaoType.CLIENTE);
		Assert.assertFalse(AutenticacaoType.ADMIN.ehLiberadoParaO(user));
		Assert.assertTrue(AutenticacaoType.CLIENTE.ehLiberadoParaO(user));
		Assert.assertTrue(AutenticacaoType.TODOS.ehLiberadoParaO(user));

		user.setTipoAutenticacao(AutenticacaoType.TODOS);
		Assert.assertFalse(AutenticacaoType.ADMIN.ehLiberadoParaO(user));
		Assert.assertFalse(AutenticacaoType.CLIENTE.ehLiberadoParaO(user));
		Assert.assertTrue(AutenticacaoType.TODOS.ehLiberadoParaO(user));
	}
}

