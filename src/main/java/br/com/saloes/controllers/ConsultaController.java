package br.com.saloes.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.saloes.domain.ConsultaService;
import br.com.saloes.infra.security.AutenticacaoType;
import br.com.saloes.infra.security.NivelAutenticacaoParaAcessar;
import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.ConsultaRepository;
import br.com.saloes.repositories.UsuarioRepository;

@Resource
public class ConsultaController {
	
	private final Result result;
	private final ConsultaRepository consultaRepo;
	private final Validator validator;
	private final ConsultaService consultaService;
	private final UsuarioWeb usuarioWeb;
	private final UsuarioRepository usuarioRepo;
	
	public ConsultaController(Result result, ConsultaRepository consultaRepo, Validator validator, ConsultaService consultaService, UsuarioWeb usuarioWeb, UsuarioRepository usuarioRepo) {
		this.result = result;
		this.consultaRepo = consultaRepo;
		this.validator = validator;
		this.consultaService = consultaService;
		this.usuarioWeb = usuarioWeb;
		this.usuarioRepo = usuarioRepo;
	}
	
	@Get
	@Path("/consultas")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public List<Consulta> index() {
		List<Consulta> consultas = null;

		if (usuarioWeb.ehCliente()) {
			Usuario cliente = usuarioRepo.find(usuarioWeb.getUsuario().getId());
			consultas = consultaRepo.findConsultasDeUmCliente(cliente);
		} else { 
			consultas = consultaRepo.findAll();
		}

		result.include("mapConsultasParaFuncionarios", consultaService.recuperaFuncionariosAssociadosAsConsultas(consultas));
		result.include("mapConsultasParaProfissoes", consultaService.recuperaProfissoesAssociadosAsConsultas(consultas));
		
		Collections.sort(consultas);
		
		return consultas;
	}
	
	
	@Get
	@Path("/consultas/new")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public Consulta newConsulta() {
		result.include("profissoesList", consultaService.findAllProfissoes());
		
		if (usuarioWeb.ehAdmin())
			result.include("clientesList", consultaService.findAllClientes());
		
		return new Consulta();
	}
	
	@Get
	@Path("/consultas/{consulta.id}")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public Consulta show(Consulta consulta) {
		Consulta consultaSelecionada = consultaRepo.find(consulta.getId());
		
		List<Consulta> unicaConsulta = new ArrayList<Consulta>();
		unicaConsulta.add(consultaSelecionada);
		
		result.include("mapConsultasParaFuncionarios", consultaService.recuperaFuncionariosAssociadosAsConsultas(unicaConsulta));
		result.include("mapConsultasParaProfissoes", consultaService.recuperaProfissoesAssociadosAsConsultas(unicaConsulta));
		result.include("mapConsultasParaClientes", consultaService.recuperaClientesAssociadosAsConsultas(unicaConsulta));
		
		return consultaSelecionada;
	}
	
	@Post
	@Path("/consultas")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public void create(Consulta consulta, Funcionario funcionario, Profissao profissao, Usuario cliente) {
		validator.validate(consulta);
		validator.onErrorUsePageOf(this).newConsulta();
		
		if (usuarioWeb.ehAdmin() && cliente != null && cliente.getId() != null)
			consultaService.criarNovaConsulta(consulta, funcionario, profissao, cliente);
		else 
			consultaService.criarNovaConsulta(consulta, funcionario, profissao, usuarioWeb.getUsuario());
		
		result.redirectTo(this).index();
	}

	@Delete
	@Path("/consultas/{consulta.id}")
	@NivelAutenticacaoParaAcessar(AutenticacaoType.CLIENTE)
	public void destroy(Consulta consulta) {
		consultaRepo.destroy(consultaRepo.find(consulta.getId()));
		result.redirectTo(this).index();  
	}
}