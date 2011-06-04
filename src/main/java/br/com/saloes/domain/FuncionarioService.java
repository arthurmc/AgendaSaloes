package br.com.saloes.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;

public interface FuncionarioService {

	List<Profissao> listaTodasAsProfissoes();

	void cadastraNovoFuncionario(Funcionario funcionario, List<Profissao> profissao);

	Map<Funcionario, List<Profissao>> recuperaProfissoesDosFuncionariosPelosIds(List<Funcionario> funcionarioList);

	List<Profissao> recuperaProfissoesDoFuncionarioPeloId(Long id);
	
	List<Funcionario> recuperaFuncionariosDeUmaDeterminadaProfissao(Long id);

	void atualizarFuncionario(Funcionario funcionario, List<Profissao> profissoesSelecionadas);

	List<String> recuperaHorariosDisponiveisParaUmFuncionario(Long idFuncionario, Long idProfissao, Date dataParaAConsulta);
}