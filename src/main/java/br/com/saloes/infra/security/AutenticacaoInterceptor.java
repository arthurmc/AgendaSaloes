package br.com.saloes.infra.security;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.saloes.controllers.IndexController;

@Intercepts
public class AutenticacaoInterceptor implements Interceptor {

	private UsuarioWeb usuarioWeb;
	private Result result;
	
	public AutenticacaoInterceptor(UsuarioWeb usuarioWeb, Result result) {
		this.usuarioWeb = usuarioWeb;
		this.result = result;
	}
	
	@Override
	public boolean accepts(ResourceMethod method) {
		if (method.containsAnnotation(NivelAutenticacaoParaAcessar.class)) {
			AutenticacaoType liberadoPara = method.getMethod().getAnnotation(NivelAutenticacaoParaAcessar.class).value();
			if (liberadoPara == AutenticacaoType.TODOS)
				return false;
			if (usuarioWeb.isAutenticado() && liberadoPara.ehLiberadoParaO(usuarioWeb.getUsuario()))
				return false;
		} else if (usuarioWeb.isAutenticado() && usuarioWeb.getUsuario().getTipoAutenticacao() == AutenticacaoType.ADMIN) {
			return false;
		}
		
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object controller) throws InterceptionException {
		result.redirectTo(IndexController.class).loginForm();
	}
}