package br.com.saloes.controllers;

import org.junit.Before;
import org.mockito.Mockito;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.saloes.domain.ProfissaoService;
import br.com.saloes.repositories.ProfissaoRepository;

public class ProfissaoControllerTest {
	
	private MockResult result;
	private ProfissaoRepository profissaoRepo;
	private MockValidator validator;
	private ProfissaoService profissaoService;
	
	@SuppressWarnings("unused")
	private ProfissaoController profissaoController;
	
	@Before
	public void inicia() {
		result = new MockResult();
		profissaoRepo = Mockito.mock(ProfissaoRepository.class);
		validator = new MockValidator();
		profissaoService = Mockito.mock(ProfissaoService.class);
		
		profissaoController = new ProfissaoController(result, profissaoRepo, validator, profissaoService);
	}

}
