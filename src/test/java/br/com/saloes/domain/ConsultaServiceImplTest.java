package br.com.saloes.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.ConsultaRepository;
import br.com.saloes.repositories.FuncionarioRepository;
import br.com.saloes.repositories.ProfissaoRepository;
import br.com.saloes.repositories.UsuarioRepository;

import com.googlecode.objectify.Key;

public class ConsultaServiceImplTest {

	private FuncionarioRepository funcionarioRepo;
	private ProfissaoRepository profissaoRepo;
	private ConsultaRepository consultaRepo;
	private UsuarioRepository usuarioRepo;
	private ConsultaService consultaService;

	@Before
	public void inicia() {
		funcionarioRepo = Mockito.mock(FuncionarioRepository.class);
		profissaoRepo = Mockito.mock(ProfissaoRepository.class);
		consultaRepo = Mockito.mock(ConsultaRepository.class);
		usuarioRepo = Mockito.mock(UsuarioRepository.class);
		consultaService = new ConsultaServiceImpl(consultaRepo, profissaoRepo, funcionarioRepo, usuarioRepo);
	}
	
	@Test
	public void deveAssociarConsultasASeusFuncionarios() {
		Consulta consultaCleide = new Consulta();
		Consulta consultaClaudia = new Consulta();
		
		Funcionario cleide = new Funcionario();
		cleide.setNome("Cleide");
		
		Funcionario claudia = new Funcionario();
		cleide.setNome("Claudia");
		
		Key<Funcionario> keyCleide = new Key<Funcionario>(Funcionario.class, 1L);
		consultaCleide.setFuncionario(keyCleide);
		
		Key<Funcionario> keyClaudia = new Key<Funcionario>(Funcionario.class, 2L);
		consultaClaudia.setFuncionario(keyClaudia);
		
		List<Consulta> todasConsultas = new ArrayList<Consulta>();
		todasConsultas.add(consultaCleide);
		todasConsultas.add(consultaClaudia);
		
		Mockito.when(funcionarioRepo.find(keyCleide.getId())).thenReturn(cleide);
		Mockito.when(funcionarioRepo.find(keyClaudia.getId())).thenReturn(claudia);
		
		Map<Consulta, Funcionario> mapConsultaFuncionario = consultaService.recuperaFuncionariosAssociadosAsConsultas(todasConsultas);
		
		Assert.assertNotNull(mapConsultaFuncionario);
		Assert.assertEquals(mapConsultaFuncionario.get(consultaCleide), cleide);
		Assert.assertEquals(mapConsultaFuncionario.get(consultaClaudia), claudia);
	}
	
	@Test
	public void deveAssociarConsultasASuasProfissoes() {
		Consulta consultaCleideManicure = new Consulta();
		Consulta consultaClaudiaPedicure = new Consulta();
		
		Profissao manicure = new Profissao();
		manicure.setNome("MANICURE");
		
		Profissao pedicure = new Profissao();
		pedicure.setNome("PEDICURE");
		
		Key<Profissao> keyManicure = new Key<Profissao>(Profissao.class, 1L);
		consultaCleideManicure.setProfissao(keyManicure);
		
		Key<Profissao> keyPedicure = new Key<Profissao>(Profissao.class, 2L);
		consultaClaudiaPedicure.setProfissao(keyPedicure);
		
		List<Consulta> todasConsultas = new ArrayList<Consulta>();
		todasConsultas.add(consultaCleideManicure);
		todasConsultas.add(consultaClaudiaPedicure);
		
		Mockito.when(profissaoRepo.find(keyManicure.getId())).thenReturn(manicure);
		Mockito.when(profissaoRepo.find(keyPedicure.getId())).thenReturn(pedicure);
		
		Map<Consulta, Profissao> mapConsultaFuncionario = consultaService.recuperaProfissoesAssociadosAsConsultas(todasConsultas);
		
		Assert.assertNotNull(mapConsultaFuncionario);
		Assert.assertEquals(mapConsultaFuncionario.get(consultaCleideManicure), manicure);
		Assert.assertEquals(mapConsultaFuncionario.get(consultaClaudiaPedicure), pedicure);
	}
	
	@Test
	public void deveAssociarConsultasASeusClientes() {
		Consulta consultaMaria = new Consulta();
		Consulta consultaJose = new Consulta();
		
		Usuario maria = new Usuario();
		maria.setNome("Maria");

		Usuario jose = new Usuario();
		jose.setNome("Jose");

		Key<Usuario> keyMaria = new Key<Usuario>(Usuario.class, 1L);
		consultaMaria.setUsuario(keyMaria);
		
		Key<Usuario> keyJose = new Key<Usuario>(Usuario.class, 2L);
		consultaJose.setUsuario(keyJose);
		
		List<Consulta> todasConsultas = new ArrayList<Consulta>();
		todasConsultas.add(consultaMaria);
		todasConsultas.add(consultaJose);
		
		Mockito.when(usuarioRepo.find(keyMaria.getId())).thenReturn(maria);
		Mockito.when(usuarioRepo.find(keyJose.getId())).thenReturn(jose);
		
		Map<Consulta, Usuario> mapConsultaFuncionario = consultaService.recuperaClientesAssociadosAsConsultas(todasConsultas);
		
		Assert.assertNotNull(mapConsultaFuncionario);
		Assert.assertEquals(mapConsultaFuncionario.get(consultaMaria), maria);
		Assert.assertEquals(mapConsultaFuncionario.get(consultaJose), jose);
	}
}