package br.com.saloes.domain;

import java.util.List;

import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;

public interface ProfissaoService {

	void deletaProfissaoDosFuncionariosQueAPossuem(Profissao paraDeletar);

	List<Funcionario> findFuncionariosPorProfissao(Profissao paraDeletar);

	void criarNovaProfissao(Profissao profissaoParaCriar);

}
