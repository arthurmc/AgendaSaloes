package br.com.saloes.domain;

import java.util.List;
import java.util.Map;

import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.models.Usuario;

public interface ConsultaService {

	List<Profissao> findAllProfissoes();

	void criarNovaConsulta(Consulta consulta, Funcionario funcionario, Profissao profissao, Usuario usuario);

	List<Usuario> findAllClientes();
	
	Map<Consulta, Funcionario> recuperaFuncionariosAssociadosAsConsultas(List<Consulta> todasConsultas);

	Map<Consulta, Profissao> recuperaProfissoesAssociadosAsConsultas(List<Consulta> todasConsultas);

	Map<Consulta, Usuario> recuperaClientesAssociadosAsConsultas(List<Consulta> todasConsultas);
}
