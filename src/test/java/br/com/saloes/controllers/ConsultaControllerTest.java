package br.com.saloes.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.saloes.domain.ConsultaService;
import br.com.saloes.infra.security.UsuarioWeb;
import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.ConsultaRepository;
import br.com.saloes.repositories.UsuarioRepository;

public class ConsultaControllerTest {
	
	private MockResult result;
	private ConsultaRepository consultaRepo;
	private MockValidator validator;
	private ConsultaService consultaService;
	private ConsultaController consultaController;
	private UsuarioWeb usuarioWeb;
	private UsuarioRepository usuarioRepo;
	
	@Before
	public void inicia() {
		result = new MockResult();
		consultaRepo = Mockito.mock(ConsultaRepository.class);
		validator = new MockValidator();
		consultaService = Mockito.mock(ConsultaService.class);
		usuarioWeb = Mockito.mock(UsuarioWeb.class);
		usuarioRepo = Mockito.mock(UsuarioRepository.class);
		
		consultaController = new ConsultaController(result, consultaRepo, validator, consultaService, usuarioWeb, usuarioRepo);
	}
	
	@Test
	public void seForUmCliente_ApenasListarConsultasDeleMesmo() {
		Usuario usuarioQualquer = Mockito.mock(Usuario.class);
		
		Mockito.when(usuarioWeb.ehCliente()).thenReturn(true);
		Mockito.when(usuarioWeb.getUsuario()).thenReturn(usuarioQualquer);
		Mockito.when(usuarioQualquer.getId()).thenReturn(1l);
		Mockito.when(usuarioRepo.find(1l)).thenReturn(usuarioQualquer);
		Mockito.when(consultaRepo.findConsultasDeUmCliente(usuarioQualquer)).thenReturn(new ArrayList<Consulta>());
		Mockito.when(consultaRepo.findAll()).thenReturn(null);
		
		Assert.assertNotNull(consultaController.index());
	}
	
	@Test
	public void paraUmaNovaConsultaDeveTerAListaDeTodasAsProfissoes() {
		List<Profissao> todosFuncionarios = new ArrayList<Profissao>();
		todosFuncionarios.add(new Profissao());
		
		Mockito.when(consultaService.findAllProfissoes()).thenReturn(todosFuncionarios);
		consultaController.newConsulta();
		
		TestCase.assertNotNull(result.included("profissoesList"));
	}

	@Test
	public void umaNovaConsultaCriadaPeloAdminDeveTerAListaDeTodasAsProfissoesEClientes() {
		List<Profissao> todosFuncionarios = new ArrayList<Profissao>();
		todosFuncionarios.add(new Profissao());
		
		Mockito.when(consultaService.findAllProfissoes()).thenReturn(todosFuncionarios);
		Mockito.when(usuarioWeb.ehAdmin()).thenReturn(true);
		
		consultaController.newConsulta();
		
		TestCase.assertNotNull(result.included("profissoesList"));
		TestCase.assertNotNull(result.included("clientesList"));
	}
	
	@Test
	public void deveTerAListaDeClientesCasoNovaConsultaSejaFeitaPorAdmin() {
		List<Profissao> todosFuncionarios = new ArrayList<Profissao>();
		todosFuncionarios.add(Mockito.mock(Profissao.class));
		
		Mockito.when(consultaService.findAllProfissoes()).thenReturn(todosFuncionarios);
		Mockito.when(usuarioWeb.ehAdmin()).thenReturn(true);
		
		consultaController.newConsulta();
		
		TestCase.assertNotNull(result.included("clientesList"));
		TestCase.assertNotNull(result.included("profissoesList"));
	}
	
	@Test
	public void paraExibirUmaConsultaDeveTerFuncionarioProfissaoEClienteRelacionados() {
		result = new MockResult();

		consultaController = new ConsultaController(result, consultaRepo, validator, consultaService, usuarioWeb, usuarioRepo);
		
		Consulta consultaParaExibir = new Consulta();
		ArrayList<Consulta> unicaConsulta = new ArrayList<Consulta>();
		
		Mockito.when(consultaService.recuperaFuncionariosAssociadosAsConsultas(unicaConsulta)).thenReturn(new HashMap<Consulta, Funcionario>());
		Mockito.when(consultaService.recuperaProfissoesAssociadosAsConsultas(unicaConsulta)).thenReturn(new HashMap<Consulta, Profissao>());
		Mockito.when(consultaService.recuperaClientesAssociadosAsConsultas(unicaConsulta)).thenReturn(new HashMap<Consulta, Usuario>());

		consultaController.show(consultaParaExibir);
		
		Assert.assertNotNull(result.included("mapConsultasParaFuncionarios"));
		Assert.assertNotNull(result.included("mapConsultasParaProfissoes"));
		Assert.assertNotNull(result.included("mapConsultasParaClientes"));
	}
	
	@Test
	public void seForAdmin_deveFazerCadastradoDeConsultaComClienteInformado() {
		usuarioWeb = Mockito.mock(UsuarioWeb.class);
		Mockito.when(usuarioWeb.ehAdmin()).thenReturn(true);
		
		Usuario cliente = new Usuario();
		cliente.setId(1l);
		
		consultaController = new ConsultaController(result, consultaRepo, validator, consultaService, usuarioWeb, usuarioRepo);
		consultaController.create(new Consulta(), new Funcionario(), new Profissao(), cliente);
		
		Mockito.verify(usuarioWeb, Mockito.never()).getUsuario();
	}
	
	@Test
	public void seNaoForAdmin_deveFazerCadastradoDeConsultaComClienteLogadoNaSessao() {
		usuarioWeb = Mockito.mock(UsuarioWeb.class);
		Mockito.when(usuarioWeb.ehAdmin()).thenReturn(false);

		consultaController = new ConsultaController(result, consultaRepo, validator, consultaService, usuarioWeb, usuarioRepo);
		consultaController.create(new Consulta(), new Funcionario(), new Profissao(), null);
		
		Mockito.verify(usuarioWeb, Mockito.times(1)).getUsuario();
	}
}