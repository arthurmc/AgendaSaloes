package br.com.saloes.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.saloes.domain.FuncionarioService;
import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.infra.security.NivelAutenticacaoParaAcessar;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.repositories.FuncionarioRepository;

@Resource
public class FuncionarioController {

	private final Result result;
	private final Validator validator;
	private final FuncionarioRepository repository;
	private final FuncionarioService funcionarioService;
	
	public FuncionarioController(Result result, FuncionarioRepository repository, Validator validator, FuncionarioService funcionarioDomain) {
		this.result = result;
		this.repository = repository;
		this.validator = validator;
		this.funcionarioService = funcionarioDomain;
	}
	
	@Get
	@Path("/funcionarios")
	public List<Funcionario> index() {
		List<Funcionario> todosFuncionarios = repository.findAll();

		return todosFuncionarios;
	}
	
	@Post
	@Path("/funcionarios")
	public void create(Funcionario funcionario, List<Profissao> profissao) {
		validator.validate(funcionario);
		validator.onErrorUsePageOf(this).newFuncionario();

		funcionarioService.cadastraNovoFuncionario(funcionario, profissao);
		
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/funcionarios/new")
	public Funcionario newFuncionario() {
		List<Profissao> profissoes = funcionarioService.listaTodasAsProfissoes();

		if (profissoes == null || profissoes.size() == 0) {
			result.redirectTo(ProfissaoController.class).index();
		}
		
		result.include("profissoesList", profissoes);
		return new Funcionario();
	}
	
	@Put
	@Path("/funcionarios")
	public void update(Funcionario funcionario, List<Profissao> profissao) {
		validator.validate(funcionario);
		validator.onErrorUsePageOf(this).edit(funcionario);

		funcionarioService.atualizarFuncionario(funcionario, profissao);
		
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/funcionarios/{funcionario.id}/edit")
	public Funcionario edit(Funcionario funcionario) {
		result.include("profissoesList", funcionarioService.listaTodasAsProfissoes());
		result.include("profissoesDoFuncionario", funcionarioService.recuperaProfissoesDoFuncionarioPeloId(funcionario.getId()));
		return repository.find(funcionario.getId());
	}

	@Get
	@Path("/funcionarios/{funcionario.id}")
	public Funcionario show(Funcionario funcionario) {
		List<Funcionario> unicoFuncionario = new ArrayList<Funcionario>();
		unicoFuncionario.add(funcionario);
		
		result.include("profissoesMap", funcionarioService.recuperaProfissoesDosFuncionariosPelosIds(unicoFuncionario));
		
		return repository.find(funcionario.getId());
	}

	@Get
	@Path("/funcionarios/profissoes/json")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public void getFuncionariosPorProfissaoSerialized(Long id) {
		List<Funcionario> funcionariosDaProfissaoEscolhida = funcionarioService.recuperaFuncionariosDeUmaDeterminadaProfissao(id);
		result.use(Results.json()).withoutRoot().from(funcionariosDaProfissaoEscolhida).serialize();
	}
	
	@Get
	@Path("/funcionarios/horarios/json")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public void getHorariosDisponiveisDoFuncionarioSerialized(Long idFuncionario, Long idProfissao, Date dataParaAConsulta) {
		result.use(Results.json()).withoutRoot().from(funcionarioService.recuperaHorariosDisponiveisParaUmFuncionario(idFuncionario, idProfissao, dataParaAConsulta)).serialize();
	}

	@Delete
	@Path("/funcionarios/{funcionario.id}")
	public void destroy(Funcionario funcionario) {
		repository.destroy(repository.find(funcionario.getId()));
		result.redirectTo(this).index();  
	}
}