package br.com.saloes.models;

import junit.framework.Assert;

import org.junit.Test;

public class UsuarioTest {
	
	@Test
	public void seEmailESenhaDeUsuarioSaoIguaisEntaoElesSaoIguais() {
		Usuario usuario = new Usuario();
		usuario.setEmail("Arthur Moura Carvalho ** ~ .;");
		usuario.setSenha("qwe123!@#");

		Usuario usuario2 = new Usuario();
		usuario2.setEmail("Arthur Moura Carvalho ** ~ .;");
		usuario2.setSenha("qwe123!@#");
		
		Assert.assertTrue(usuario.equals(usuario2));
	}
	
	@Test
	public void seSenhasDeUsuariosSaoDiferentesEntaoElesSaoDiferentes() {
		Usuario usuario = new Usuario();
		usuario.setEmail("armoucar@gmail.com");
		usuario.setSenha("qwe123!@#");
		
		Usuario usuario2 = new Usuario();
		usuario2.setEmail("armoucar@gmail.com");
		usuario2.setSenha("qwe123!@#.");
		
		Assert.assertFalse(usuario.equals(usuario2));
	}
	
	@Test
	public void seEmailsDeUsuariosSaoDiferentesEntaoElesSaoDiferentes() {
		Usuario usuario = new Usuario();
		usuario.setEmail("armoucar@gmail.com");
		usuario.setSenha("qwe123!@#");

		Usuario usuario2 = new Usuario();
		usuario2.setEmail("armocar@gmail.com.br");
		usuario2.setSenha("qwe123!@#");
		
		Assert.assertFalse(usuario.equals(usuario2));
	}
}