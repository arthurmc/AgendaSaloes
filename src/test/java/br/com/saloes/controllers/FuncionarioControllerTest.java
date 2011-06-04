package br.com.saloes.controllers;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.saloes.domain.FuncionarioService;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.repositories.FuncionarioRepository;

public class FuncionarioControllerTest {

	private MockResult result;
	private MockValidator validator;
	private FuncionarioRepository repository;
	private FuncionarioService funcionarioService;
	private FuncionarioController funcionarioController;
	
	@Before
	public void init() {
		result = new MockResult();
		validator = new MockValidator();
		repository = Mockito.mock(FuncionarioRepository.class);
		funcionarioService = Mockito.mock(FuncionarioService.class);
		
		funcionarioController = new FuncionarioController(result, repository, validator, funcionarioService);
	}
	
	@Test
	public void seNaoTiverFuncionariosNaoDeveVincularProfissoes() {
		result = new MockResult();
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		Mockito.when(repository.findAll()).thenReturn(funcionarios);
		funcionarioController = new FuncionarioController(result, repository, validator, funcionarioService);
		funcionarioController.index();
		
		Assert.assertNull(result.included("profissoesMap"));
	}
	
	@Test
	public void paraCadastrarUmNovoFuncionarioDeveSeTerAoMenosUmaProfissaoCadastrada() {
		Result result = Mockito.mock(Result.class);
		ProfissaoController profissaoController = Mockito.mock(ProfissaoController.class);
		
		List<Profissao> profissoes = new ArrayList<Profissao>();
		Mockito.when(funcionarioService.listaTodasAsProfissoes()).thenReturn(profissoes);
		Mockito.when(result.redirectTo(ProfissaoController.class)).thenReturn(profissaoController);
		Mockito.when(profissaoController.index()).thenReturn(null);
		
		funcionarioController = new FuncionarioController(result, repository, validator, funcionarioService);
		funcionarioController.newFuncionario();
	}
}