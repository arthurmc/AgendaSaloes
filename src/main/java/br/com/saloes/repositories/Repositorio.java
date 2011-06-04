package br.com.saloes.repositories;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.models.Usuario;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

class Repositorio<T> {
	
	protected final Objectify objectify;
	
	static {
		ObjectifyService.register(Usuario.class);
		ObjectifyService.register(Funcionario.class);
		ObjectifyService.register(Consulta.class);
		ObjectifyService.register(Profissao.class);
	}
	
	public Repositorio(Objectify objectify) {
		this.objectify = objectify;
	}
	
	public void create(T entity) {
		objectify.put(entity);
	}
	
	public void update(T entity) {
		objectify.put(entity);
	}
	
	public void destroy(T entity) {
		objectify.delete(entity);
	}
	
	public T find(Long id) {
		return objectify.get(getParameterizedClass(), id);
	}
	
	public List<T> findAll() {
		return objectify.query(getParameterizedClass()).list();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getParameterizedClass() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
}
