package br.com.saloes.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.repositories.ConsultaRepository;
import br.com.saloes.repositories.FuncionarioRepository;
import br.com.saloes.repositories.ProfissaoRepository;

import com.googlecode.objectify.Key;

@Component
public class FuncionarioServiceImpl implements FuncionarioService {

	private final FuncionarioRepository funcionarioRepo;
	private final ProfissaoRepository profissaoRepo;
	private final ConsultaRepository consultaRepo;

	public FuncionarioServiceImpl(FuncionarioRepository repository, ProfissaoRepository profissaoRepo, ConsultaRepository consultaRepo) {
		this.funcionarioRepo = repository;
		this.profissaoRepo = profissaoRepo;
		this.consultaRepo = consultaRepo;
	}

	@Override
	public List<Profissao> listaTodasAsProfissoes() {
		return profissaoRepo.findAll();
	}

	@Override
	public void cadastraNovoFuncionario(Funcionario funcionario, List<Profissao> profissoesSelecionadas) {
		if (profissoesSelecionadas == null || profissoesSelecionadas.size() == 0)
			throw new IllegalStateException("É necessário informar ao menos uma profissão para o novo funcionário");

		if (funcionario == null)
			throw new NullPointerException("Funcionário não informado");
		
		if (funcionario.getNome() == null || "".equals(funcionario.getNome()))
			throw new IllegalStateException("Deve ser informado um nome para o novo funcionário");

		List<Key<Profissao>> keysProfissao = new ArrayList<Key<Profissao>>();
		for (Profissao profissao : profissoesSelecionadas) {
			keysProfissao.add(new Key<Profissao>(Profissao.class, profissao.getId()));
		}
		
		funcionario.setProfissao(keysProfissao);
		funcionarioRepo.create(funcionario);
	}

	@Override
	public void atualizarFuncionario(Funcionario funcionario, List<Profissao> profissoesSelecionadas) {
		if (profissoesSelecionadas == null || profissoesSelecionadas.size() == 0)
			throw new NullPointerException("É necessário informar ao menos uma profissão o funcionário");
		
		if (funcionario == null)
			throw new NullPointerException("Funcionário não informado");
		
		if (funcionario.getNome() == null || "".equals(funcionario.getNome()))
			throw new IllegalStateException("Deve ser informado um nome para o novo funcionário");
		
		List<Key<Profissao>> keysProfissao = new ArrayList<Key<Profissao>>();
		for (Profissao profissao : profissoesSelecionadas) {
			keysProfissao.add(new Key<Profissao>(Profissao.class, profissao.getId()));
		}
		
		funcionario.setProfissao(keysProfissao);
		funcionarioRepo.update(funcionario);
	}

	@Override
	public Map<Funcionario, List<Profissao>> recuperaProfissoesDosFuncionariosPelosIds(List<Funcionario> funcionarioList) {
		Map<Funcionario, List<Profissao>> profissoesDosFuncionarios = new HashMap<Funcionario, List<Profissao>>();
		
		if (funcionarioList == null || funcionarioList.size() == 0) {
			throw new NullPointerException("É necessário informar um funcionario para saber suas profissões.");
		}
		
		for (Funcionario funcionario : funcionarioList) {
			funcionario = funcionarioRepo.find(funcionario.getId());
			List<Profissao> profissoes = new ArrayList<Profissao>();

			if (funcionario.getProfissao() == null) {
				continue;
			}
			
			for (Key<Profissao> keyProfissao : funcionario.getProfissao()) {
				profissoes.add(profissaoRepo.find(keyProfissao.getId()));
			}
			
			profissoesDosFuncionarios.put(funcionario, profissoes);
		}
		return profissoesDosFuncionarios;
	}

	@Override
	public List<Profissao> recuperaProfissoesDoFuncionarioPeloId(Long id) {
		if (id == null) {
			throw new NullPointerException("É necessário informar um funcionario para saber suas profissões.");
		}
		
		Funcionario funcionario = funcionarioRepo.find(id);
		List<Profissao> profissoes = new ArrayList<Profissao>();
			
		if (funcionario.getProfissao() == null) {
			return new ArrayList<Profissao>();
		}
			
		for (Key<Profissao> keyProfissao : funcionario.getProfissao()) {
			profissoes.add(profissaoRepo.find(keyProfissao.getId()));
		}

		return profissoes;
	}

	@Override
	public List<Funcionario> recuperaFuncionariosDeUmaDeterminadaProfissao(Long id) {
		Profissao profissaoEscolhida = profissaoRepo.find(id);
		return funcionarioRepo.findByProfissao(profissaoEscolhida);
	}

	@Override
	public List<String> recuperaHorariosDisponiveisParaUmFuncionario(Long idFuncionario, Long idProfissao, Date dataParaAConsulta) {
		Funcionario funcionarioSelecionado = funcionarioRepo.find(idFuncionario);
		List<Consulta> consultasJaAgendadas = consultaRepo.findConsultasDeUmFuncionarioDeUmDeterminadoDia(funcionarioSelecionado, dataParaAConsulta);
		if (consultasJaAgendadas == null || consultasJaAgendadas.size() == 0) {
			return todosOsHorariosEstaoDisponiveis(funcionarioSelecionado);
		}
		
		Profissao profissaoSelecionada = profissaoRepo.find(idProfissao);
		return horariosLivres(funcionarioSelecionado, profissaoSelecionada, consultasJaAgendadas);
	}
	
	private List<String> horariosLivres(Funcionario funcionario, Profissao profissao, List<Consulta> consultasJaAgendadas) {
		Calendar inicioJornada = Calendar.getInstance();
		inicioJornada.setTimeInMillis(funcionario.getInicioJornadaTrabalho().getTime());

		Calendar fimJornada = Calendar.getInstance();
		fimJornada.setTimeInMillis(funcionario.getFimJornadaTrabalho().getTime());
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		List<String> horariosLivres = new ArrayList<String>();

		boolean temConflito = false;
		for (;inicioJornada.getTimeInMillis() < fimJornada.getTimeInMillis(); inicioJornada.add(Calendar.MINUTE, 30)) {
			Calendar horarioAtualDeVerificacao = Calendar.getInstance();
			horarioAtualDeVerificacao.setTimeInMillis(inicioJornada.getTimeInMillis());
			for (Consulta consultaJaAgendada : consultasJaAgendadas) {
				Profissao profissaoDaConsultaAgendada = profissaoRepo.find(consultaJaAgendada.getProfissao().getId());
				if (consultaJaAgendada.temConflitoComNovoHorario(profissaoDaConsultaAgendada, horarioAtualDeVerificacao, profissao)) {
					temConflito = true;
					break;
				}
			}
			if (!temConflito) {
				horariosLivres.add(sdf.format(inicioJornada.getTime()));
			}
			temConflito = false;
		}
		
		return horariosLivres;
	}

	private List<String> todosOsHorariosEstaoDisponiveis(Funcionario funcionario) {
		Calendar inicioJornada = Calendar.getInstance();
		inicioJornada.setTimeInMillis(funcionario.getInicioJornadaTrabalho().getTime());

		Calendar fimJornada = Calendar.getInstance();
		fimJornada.setTimeInMillis(funcionario.getFimJornadaTrabalho().getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		List<String> todosHorariosLivres = new ArrayList<String>();

		for (;inicioJornada.getTimeInMillis() < fimJornada.getTimeInMillis(); inicioJornada.add(Calendar.MINUTE, 30)) {
			todosHorariosLivres.add(sdf.format(inicioJornada.getTime()));
		}
		
		return todosHorariosLivres;
	}
}