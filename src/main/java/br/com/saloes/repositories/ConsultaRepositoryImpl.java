package br.com.saloes.repositories;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Usuario;

import com.googlecode.objectify.Objectify;

@Component
public class ConsultaRepositoryImpl extends Repositorio<Consulta> implements ConsultaRepository {

	public ConsultaRepositoryImpl(Objectify objectify) {
		super(objectify);
	}

	@Override
	public List<Consulta> findConsultasDeUmFuncionario(Funcionario funcionarioSelecionado) {
		return objectify.query(Consulta.class).filter("funcionario", funcionarioSelecionado).list();
	}

	@Override
	public List<Consulta> findConsultasDeUmFuncionarioDeUmDeterminadoDia(Funcionario funcionarioSelecionado, Date dia) {
		return objectify.query(Consulta.class).filter("funcionario", funcionarioSelecionado).filter("dia", dia).list();
	}

	@Override
	public List<Consulta> findConsultasDeUmCliente(Usuario usuario) {
		return objectify.query(Consulta.class).filter("usuario", usuario).list();
	}
}
