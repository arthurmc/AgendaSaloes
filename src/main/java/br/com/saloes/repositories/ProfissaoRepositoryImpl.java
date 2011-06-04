package br.com.saloes.repositories;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.models.Profissao;

import com.googlecode.objectify.Objectify;

@Component
public class ProfissaoRepositoryImpl extends Repositorio<Profissao> implements ProfissaoRepository {

	public ProfissaoRepositoryImpl(Objectify entityManager) {
		super(entityManager);
	}

	@Override
	public Profissao findByName(String nomeDaProfissao) {
		return objectify.query(Profissao.class).filter("nome", nomeDaProfissao).get();
	}
}
