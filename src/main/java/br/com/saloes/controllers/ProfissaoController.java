package br.com.saloes.controllers;

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
import br.com.saloes.domain.ProfissaoService;
import br.com.saloes.models.Profissao;
import br.com.saloes.repositories.ProfissaoRepository;

@Resource
public class ProfissaoController {

	private final Result result;
	private final ProfissaoRepository profissaoRepo;
	private final Validator validator;
	private final ProfissaoService profissaoService;
	
	public ProfissaoController(Result result, ProfissaoRepository repository, Validator validator, ProfissaoService profissaoService) {
		this.result = result;
		this.profissaoRepo = repository;
		this.validator = validator;
		this.profissaoService = profissaoService;
	}
	
	@Get
	@Path("/profissoes")
	public List<Profissao> index() {
		return profissaoRepo.findAll();
	}
	
	@Post
	@Path("/profissoes")
	public void create(Profissao profissao) {
		validator.validate(profissao);
		validator.onErrorUsePageOf(this).newProfissao();
		
		profissaoService.criarNovaProfissao(profissao);
		
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/profissoes/new")
	public Profissao newProfissao() {
		return new Profissao();
	}
	
	@Put
	@Path("/profissoes")
	public void update(Profissao profissao) {
		validator.validate(profissao);
		validator.onErrorUsePageOf(this).edit(profissao);
		profissaoRepo.update(profissao);
		result.redirectTo(this).index();
	}
	
	@Get
	@Path("/profissoes/{profissao.id}/edit")
	public Profissao edit(Profissao profissao) {
		return profissaoRepo.find(profissao.getId());
	}

	@Get
	@Path("/profissoes/{profissao.id}")
	public Profissao show(Profissao profissao) {
		return profissaoRepo.find(profissao.getId());
	}
	
	@Get
	@Path("/profissoes/json")
	public void listAllJSON(Profissao profissao) {
		List<Profissao> todasAsProfissoes = profissaoRepo.findAll();
		result.use(Results.json()).withoutRoot().from(todasAsProfissoes).serialize();
	}

	@Delete
	@Path("/profissoes/{profissao.id}")
	public void destroy(Profissao profissao) {
		Profissao paraDeletar = profissaoRepo.find(profissao.getId());
		profissaoService.deletaProfissaoDosFuncionariosQueAPossuem(paraDeletar);
		result.redirectTo(this).index();  
	}
}