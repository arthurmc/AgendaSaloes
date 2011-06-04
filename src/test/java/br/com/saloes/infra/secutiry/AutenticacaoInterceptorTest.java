package br.com.saloes.infra.secutiry;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.saloes.infra.security.AutenticacaoInterceptor;
import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.infra.security.NivelAutenticacaoParaAcessar;
import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Usuario;

public class AutenticacaoInterceptorTest {
	
	private UsuarioWeb usuarioWeb;
	private MockResult result;
	private AutenticacaoInterceptor autenticacaoInterceptor;
	
	@Before
	public void inicia() {
		usuarioWeb = new UsuarioWeb();
		result = new MockResult();
		autenticacaoInterceptor = new AutenticacaoInterceptor(usuarioWeb, result);
	}
	
	@Test
	public void adminDevePoderAcessarTudo() throws Exception {
		Usuario user = new Usuario();
		user.setTipoAutenticacao(AutenticacaoType.ADMIN);
		usuarioWeb.setAutenticado(user);

		ResourceMethod method = Mockito.mock(ResourceMethod.class);
		
		Method metodoInterceptado = MetodosParaMock.class.getMethod("soAdminsAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);

		Boolean naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);

		Assert.assertFalse(naoVaiDeixarAcessar);
		
		Mockito.when(method.containsAnnotation(NivelAutenticacaoParaAcessar.class)).thenReturn(true);
		
		metodoInterceptado = MetodosParaMock.class.getMethod("soClientesEAdminsAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);
		
		Assert.assertFalse(naoVaiDeixarAcessar);
		
		metodoInterceptado = MetodosParaMock.class.getMethod("todosAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);

		Assert.assertFalse(naoVaiDeixarAcessar);
	}

	@Test
	public void clienteNaoPodeAcessarRecursoDeAdminEPodeAcessarDeClienteETodos() throws Exception {
		Usuario user = new Usuario();
		user.setTipoAutenticacao(AutenticacaoType.CLIENTE);
		usuarioWeb.setAutenticado(user);

		ResourceMethod method = Mockito.mock(ResourceMethod.class);

		Method metodoInterceptado = MetodosParaMock.class.getMethod("soAdminsAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		Boolean naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);

		Assert.assertTrue(naoVaiDeixarAcessar);

		Mockito.when(method.containsAnnotation(NivelAutenticacaoParaAcessar.class)).thenReturn(true);
		
		metodoInterceptado = MetodosParaMock.class.getMethod("soClientesEAdminsAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);
		
		Assert.assertFalse(naoVaiDeixarAcessar);
		
		metodoInterceptado = MetodosParaMock.class.getMethod("todosAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);

		Assert.assertFalse(naoVaiDeixarAcessar);
	}

	@Test
	public void nivelDeAcessoTodosSoPodeAcessarMetodosComONivelDeAcessoTodos() throws Exception {
		Usuario user = new Usuario();
		user.setTipoAutenticacao(AutenticacaoType.TODOS);
		usuarioWeb.setAutenticado(user);
		
		ResourceMethod method = Mockito.mock(ResourceMethod.class);
		
		Method metodoInterceptado = MetodosParaMock.class.getMethod("soAdminsAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		Boolean naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);
		
		Assert.assertTrue(naoVaiDeixarAcessar);
		
		Mockito.when(method.containsAnnotation(NivelAutenticacaoParaAcessar.class)).thenReturn(true);
		
		metodoInterceptado = MetodosParaMock.class.getMethod("soClientesEAdminsAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);
		
		Assert.assertTrue(naoVaiDeixarAcessar);
		
		metodoInterceptado = MetodosParaMock.class.getMethod("todosAcessam");
		Mockito.when(method.getMethod()).thenReturn(metodoInterceptado);
		naoVaiDeixarAcessar = autenticacaoInterceptor.accepts(method);
		
		Assert.assertFalse(naoVaiDeixarAcessar);
	}

	class MetodosParaMock {
		
		public void soAdminsAcessam() {}
		
		@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
		public void soClientesEAdminsAcessam() {}
		
		@NivelAutenticacaoParaAcessar(AutenticacaoType.TODOS)
		public void todosAcessam() {}
		
	}
}