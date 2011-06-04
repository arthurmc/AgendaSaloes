package br.com.saloes.repositories;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;

import com.googlecode.objectify.Objectify;

@Component
public class FuncionarioRepositoryImpl extends Repositorio<Funcionario> implements FuncionarioRepository {

	public FuncionarioRepositoryImpl(Objectify objectify) {
		super(objectify);
	}

	@Override
	public List<Funcionario> findByProfissao(Profissao profissao) {
		return objectify.query(Funcionario.class).filter("profissao", profissao).list();
	}
}
