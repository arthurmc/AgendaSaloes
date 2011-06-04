package br.com.saloes.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.repositories.ConsultaRepository;
import br.com.saloes.repositories.FuncionarioRepository;
import br.com.saloes.repositories.ProfissaoRepository;

import com.googlecode.objectify.Key;


public class FuncionarioServiceImplTest {

	private FuncionarioRepository funcionarioRepo;
	private ProfissaoRepository profissaoRepo;
	private ConsultaRepository consultaRepo;
	private FuncionarioService funcionarioService;

	@Before
	public void inicia() {
		funcionarioRepo = Mockito.mock(FuncionarioRepository.class);
		profissaoRepo = Mockito.mock(ProfissaoRepository.class);
		consultaRepo = Mockito.mock(ConsultaRepository.class);
		funcionarioService = new FuncionarioServiceImpl(funcionarioRepo, profissaoRepo, consultaRepo);
	}
	
	@Test(expected=IllegalStateException.class)
	public void naoDeveDeixarCadastrarUmFuncionarioSemPeloMenosUmaProfissao() {
		Funcionario funci = Mockito.mock(Funcionario.class);
		funci.setNome("Paulo");
		
		
		funcionarioService.cadastraNovoFuncionario(funci, null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void naoDeveDeixarCadastrarUmFuncionarioSemNome() {
		Funcionario funci = Mockito.mock(Funcionario.class);
		
		List<Profissao> profissoes = new ArrayList<Profissao>();
		profissoes.add(Mockito.mock(Profissao.class));
		
		funcionarioService.cadastraNovoFuncionario(funci, profissoes);
	}

	@Test(expected=NullPointerException.class)
	public void naoDeveDeixarAtualizarUmFuncionarioSemPeloMenosUmaProfissao() {
		Funcionario funci = Mockito.mock(Funcionario.class);
		funci.setNome("Paulo");
		
		funcionarioService.atualizarFuncionario(funci, null);
	}

	@Test(expected=IllegalStateException.class)
	public void naoDeveDeixarAtualizarUmFuncionarioSemNome() {
		Funcionario funci = Mockito.mock(Funcionario.class);
		
		List<Profissao> profissoes = new ArrayList<Profissao>();
		profissoes.add(Mockito.mock(Profissao.class));
		
		funcionarioService.atualizarFuncionario(funci, profissoes);
	}
	
	@Test
	public void deveRetornarAQuantidadeProfissoesDeDoisFuncionario() {
		List<Funcionario> listaFuncionarios = new ArrayList<Funcionario>();

		Funcionario clotilde = new Funcionario();
		Funcionario claudia = new Funcionario();
		
		Key<Profissao> keyManicure = new Key<Profissao>(Profissao.class, 1);
		Profissao manicure = new Profissao();
		manicure.setNome("MANICURE");
		
		Key<Profissao> keyCabeleleiro = new Key<Profissao>(Profissao.class, 2);
		Profissao cabeleleiro = new Profissao();
		cabeleleiro.setNome("CABELELEIRO");

		Key<Profissao> keyDepiladora = new Key<Profissao>(Profissao.class, 3);
		Profissao depiladora = new Profissao();
		depiladora.setNome("DEPILADORA");
		
		List<Key<Profissao>> profissoesClotilde = new ArrayList<Key<Profissao>>();
		profissoesClotilde.add(keyCabeleleiro);
		profissoesClotilde.add(keyManicure);
		clotilde.setProfissao(profissoesClotilde);
		
		List<Key<Profissao>> profissoesClaudia = new ArrayList<Key<Profissao>>();
		profissoesClaudia.add(keyDepiladora);
		profissoesClaudia.add(keyManicure);
		claudia.setProfissao(profissoesClaudia);
		
		listaFuncionarios.add(claudia);
		listaFuncionarios.add(clotilde);
		
		Mockito.when(funcionarioRepo.find(claudia.getId())).thenReturn(claudia);
		Mockito.when(funcionarioRepo.find(clotilde.getId())).thenReturn(clotilde);
		Mockito.when(profissaoRepo.find(keyManicure.getId())).thenReturn(manicure);
		Mockito.when(profissaoRepo.find(keyCabeleleiro.getId())).thenReturn(cabeleleiro);
		Mockito.when(profissaoRepo.find(keyDepiladora.getId())).thenReturn(depiladora);
		
		Map<Funcionario, List<Profissao>> funcionariosESuasProfissoes = funcionarioService.recuperaProfissoesDosFuncionariosPelosIds(listaFuncionarios);
		
		Assert.assertEquals(2, funcionariosESuasProfissoes.get(claudia).size());
		Assert.assertEquals(2, funcionariosESuasProfissoes.get(clotilde).size());
	}
	
	@Test
	public void deveRetornarAsProfissoesDeUmFuncionario() {
		Funcionario matilde = new Funcionario();
		matilde.setId(2l);
		
		Key<Profissao> keyManicure = new Key<Profissao>(Profissao.class, 1);
		Profissao manicure = new Profissao();
		manicure.setNome("MANICURE");
		
		Key<Profissao> keyCabeleleiro = new Key<Profissao>(Profissao.class, 2);
		Profissao cabeleleiro = new Profissao();
		cabeleleiro.setNome("CABELELEIRO");
				
		List<Key<Profissao>> profissoesMatilde = new ArrayList<Key<Profissao>>();
		profissoesMatilde.add(keyCabeleleiro);
		profissoesMatilde.add(keyManicure);
		matilde.setProfissao(profissoesMatilde);
		
		Mockito.when(funcionarioRepo.find(matilde.getId())).thenReturn(matilde);
		Mockito.when(profissaoRepo.find(keyManicure.getId())).thenReturn(manicure);
		Mockito.when(profissaoRepo.find(keyCabeleleiro.getId())).thenReturn(cabeleleiro);
		
		List<Profissao> funcionariosESuasProfissoes = funcionarioService.recuperaProfissoesDoFuncionarioPeloId(matilde.getId());
		
		Assert.assertEquals(2, funcionariosESuasProfissoes.size());
	}
	
	@Test
	public void retornarTodosHorariosCasoNaoExistaAgendamentosParaODiaSelecionado() {
		Funcionario clotilde = new Funcionario();
		clotilde.setNome("clotilde");
		
		Profissao manicure = new Profissao();
		manicure.setId(7l);
		manicure.setNome("Manicure");
		
		Calendar inicioJornada = Calendar.getInstance();
		inicioJornada.set(Calendar.HOUR_OF_DAY, 8);
		inicioJornada.set(Calendar.MINUTE, 0);
		inicioJornada.set(Calendar.SECOND, 0);

		Calendar fimJornada = Calendar.getInstance();
		fimJornada.set(Calendar.HOUR_OF_DAY, 18);
		fimJornada.set(Calendar.MINUTE, 0);
		fimJornada.set(Calendar.SECOND, 0);
		
		Calendar inicioJornadaTest = Calendar.getInstance();
		inicioJornadaTest.set(Calendar.HOUR_OF_DAY, 8);
		inicioJornadaTest.set(Calendar.MINUTE, 0);
		inicioJornadaTest.set(Calendar.SECOND, 0);

		Calendar fimJornadaTest = Calendar.getInstance();
		fimJornadaTest.set(Calendar.HOUR_OF_DAY, 18);
		fimJornadaTest.set(Calendar.MINUTE, 0);
		fimJornadaTest.set(Calendar.SECOND, 0);
		
		clotilde.setInicioJornadaTrabalho(inicioJornada.getTime());
		clotilde.setFimJornadaTrabalho(fimJornada.getTime());
		
		Mockito.when(funcionarioRepo.find(clotilde.getId())).thenReturn(clotilde);
		
		Calendar diaDaConsulta = Calendar.getInstance();
		diaDaConsulta.set(2011, 4, 11);
		
		int quantidadeDeHorarios = 0;
		
		for (;inicioJornadaTest.getTimeInMillis() < fimJornadaTest.getTimeInMillis(); inicioJornadaTest.add(Calendar.MINUTE, 30)) {
			quantidadeDeHorarios++;
		}
		
		List<String> horariosDisponiveis = funcionarioService.recuperaHorariosDisponiveisParaUmFuncionario(clotilde.getId(), manicure.getId(), diaDaConsulta.getTime());
		
		Assert.assertNotNull(horariosDisponiveis);
		Assert.assertEquals(quantidadeDeHorarios, horariosDisponiveis.size());
	}
	
	@Test
	public void retornarHorariosDisponiveisParaODiaSelecionado_JaExistem3ConsultasAgendadas() {
		Funcionario clotilde = new Funcionario();
		clotilde.setNome("clotilde");
		
		Profissao manicure = new Profissao();
		manicure.setId(0l);
		manicure.setNome("Manicure");
		manicure.setTempoQueConsome("30");
		
		Calendar inicioJornada = Calendar.getInstance();
		inicioJornada.set(Calendar.HOUR_OF_DAY, 8);
		inicioJornada.set(Calendar.MINUTE, 0);
		inicioJornada.set(Calendar.SECOND, 0);
		
		Calendar fimJornada = Calendar.getInstance();
		fimJornada.set(Calendar.HOUR_OF_DAY, 18);
		fimJornada.set(Calendar.MINUTE, 0);
		fimJornada.set(Calendar.SECOND, 0);
		
		clotilde.setInicioJornadaTrabalho(inicioJornada.getTime());
		clotilde.setFimJornadaTrabalho(fimJornada.getTime());
		
		Calendar diaDaConsulta = Calendar.getInstance();
		diaDaConsulta.set(2011, 4, 11);
		
		Consulta consulta = new Consulta();
		Consulta consulta2 = new Consulta();
		Consulta consulta3 = new Consulta();
		
		Calendar horaConsulta = Calendar.getInstance();
		horaConsulta.set(Calendar.HOUR_OF_DAY, 9);
		horaConsulta.set(Calendar.MINUTE, 0);
		horaConsulta.set(Calendar.SECOND, 0);
		consulta.setHora(horaConsulta.getTime());
		consulta.setProfissao(new Key<Profissao>(Profissao.class, 1l));
		
		horaConsulta = Calendar.getInstance();
		horaConsulta.set(Calendar.HOUR_OF_DAY, 11);
		horaConsulta.set(Calendar.MINUTE, 0);
		horaConsulta.set(Calendar.SECOND, 0);
		consulta2.setHora(horaConsulta.getTime());
		consulta2.setProfissao(new Key<Profissao>(Profissao.class, 2l));
		
		horaConsulta = Calendar.getInstance();
		horaConsulta.set(Calendar.HOUR_OF_DAY, 14);
		horaConsulta.set(Calendar.MINUTE, 30);
		horaConsulta.set(Calendar.SECOND, 0);
		consulta3.setHora(horaConsulta.getTime());
		consulta3.setProfissao(new Key<Profissao>(Profissao.class, 3l));
		
		List<Consulta> consultas = new ArrayList<Consulta>();
		consultas.add(consulta);
		consultas.add(consulta2);
		consultas.add(consulta3);
		
		Calendar inicioJornadaTest = Calendar.getInstance();
		inicioJornadaTest.set(Calendar.HOUR_OF_DAY, inicioJornada.get(Calendar.HOUR_OF_DAY));
		inicioJornadaTest.set(Calendar.MINUTE, inicioJornada.get(Calendar.MINUTE));
		inicioJornadaTest.set(Calendar.SECOND, inicioJornada.get(Calendar.SECOND));

		Calendar fimJornadaTest = Calendar.getInstance();
		fimJornadaTest.set(Calendar.HOUR_OF_DAY, 18);
		fimJornadaTest.set(Calendar.MINUTE, 0);
		fimJornadaTest.set(Calendar.SECOND, 0);
		
		int quantidadeDeHorariosQuePodemSerAgendadosEmUmDia = 0;
		
		for (;inicioJornadaTest.getTimeInMillis() < fimJornadaTest.getTimeInMillis(); inicioJornadaTest.add(Calendar.MINUTE, 30)) {
			quantidadeDeHorariosQuePodemSerAgendadosEmUmDia++;
		}		
		
		Date diaDaConsultaDate = diaDaConsulta.getTime();
		
		Mockito.when(funcionarioRepo.find(clotilde.getId())).thenReturn(clotilde);
		Mockito.when(consultaRepo.findConsultasDeUmFuncionarioDeUmDeterminadoDia(clotilde, diaDaConsultaDate)).thenReturn(consultas);
		
		Profissao profissaoConsulta1 = new Profissao();
		profissaoConsulta1.setId(1l);
		profissaoConsulta1.setTempoQueConsome("30");
		
		Profissao profissaoConsulta2 = new Profissao();
		profissaoConsulta2.setId(2l);
		profissaoConsulta2.setTempoQueConsome("30");
		
		Profissao profissaoConsulta3 = new Profissao();
		profissaoConsulta3.setId(3l);
		profissaoConsulta3.setTempoQueConsome("30");
		
		Mockito.when(profissaoRepo.find(0l)).thenReturn(manicure);
		Mockito.when(profissaoRepo.find(1l)).thenReturn(profissaoConsulta1);
		Mockito.when(profissaoRepo.find(2l)).thenReturn(profissaoConsulta2);
		Mockito.when(profissaoRepo.find(3l)).thenReturn(profissaoConsulta3);

		List<String> horariosDisponiveis = funcionarioService.recuperaHorariosDisponiveisParaUmFuncionario(clotilde.getId(), manicure.getId(), diaDaConsultaDate);
		
		Assert.assertNotNull(horariosDisponiveis);
		Assert.assertEquals(quantidadeDeHorariosQuePodemSerAgendadosEmUmDia - consultas.size(), horariosDisponiveis.size());
	}
}