package br.com.saloes.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.repositories.FuncionarioRepository;
import br.com.saloes.repositories.ProfissaoRepository;

import com.googlecode.objectify.Key;

public class ProfissaoServiceImplTest {

	private FuncionarioRepository funcionarioRepo;
	private ProfissaoRepository profissaoRepo;
	private ProfissaoServiceImpl profissaoService;
	
	@Before
	public void inicia() {
		funcionarioRepo = mock(FuncionarioRepository.class);
		profissaoRepo = mock(ProfissaoRepository.class);
		profissaoService = new ProfissaoServiceImpl(funcionarioRepo, profissaoRepo);
	}
	
	@Test
	public void deveDeletarAProfissaoEscolhidaDeTodosOsFuncionariosQueAPossuem() {
		Profissao manicure = mock(Profissao.class);
		when(manicure.getId()).thenReturn(1l);
		
		Key<Profissao> manicureKey = new Key<Profissao>(Profissao.class, 1l);
		Key<Profissao> pedicureKey = new Key<Profissao>(Profissao.class, 2l);
		Key<Profissao> depiladoraKey = new Key<Profissao>(Profissao.class, 3l);
		
		Funcionario abreu = new Funcionario();
		abreu.setNome("Abreu");
		abreu.setId(1l);
		List<Key<Profissao>> profissoesAbreu = new ArrayList<Key<Profissao>>();
		profissoesAbreu.add(depiladoraKey);
		profissoesAbreu.add(pedicureKey);
		abreu.setProfissao(profissoesAbreu);
		
		Funcionario creudete = new Funcionario();
		creudete.setNome("Creudete");
		creudete.setId(2l);
		List<Key<Profissao>> profissoesCreudete = new ArrayList<Key<Profissao>>();
		profissoesCreudete.add(manicureKey);
		profissoesCreudete.add(pedicureKey);
		creudete.setProfissao(profissoesCreudete);
		
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		funcionarios.add(abreu);
		funcionarios.add(creudete);
		
		when(funcionarioRepo.findByProfissao(any(Profissao.class))).thenReturn(funcionarios);

		profissaoService.deletaProfissaoDosFuncionariosQueAPossuem(manicure);
		
		assertEquals(2, abreu.getProfissao().size());
		assertEquals(1, creudete.getProfissao().size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoDeveDeixarAdicionarUmaProfissaoComOMesmoNome() {
		Profissao manicure = new Profissao();
		manicure.setNome("manicure");
		
		when(profissaoRepo.findByName(manicure.getNome())).thenReturn(manicure);
		
		profissaoService.criarNovaProfissao(manicure);
	}
}