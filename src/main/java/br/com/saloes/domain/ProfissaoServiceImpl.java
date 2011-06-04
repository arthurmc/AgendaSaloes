package br.com.saloes.domain;

import java.util.List;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.repositories.FuncionarioRepository;
import br.com.saloes.repositories.ProfissaoRepository;

import com.googlecode.objectify.Key;

@Component
public class ProfissaoServiceImpl implements ProfissaoService {

	private final FuncionarioRepository funcionarioRepo;
	private final ProfissaoRepository profissaoRepo;
	
	public ProfissaoServiceImpl(FuncionarioRepository funcionarioRepo, ProfissaoRepository profissaoRepo) {
		this.funcionarioRepo = funcionarioRepo;
		this.profissaoRepo = profissaoRepo;
	}
	
	@Override
	public void deletaProfissaoDosFuncionariosQueAPossuem(Profissao paraDeletar) {
		List<Funcionario> funcionariosDaProfissao = funcionarioRepo.findByProfissao(paraDeletar);
		if (funcionariosDaProfissao != null && funcionariosDaProfissao.size() > 0) {
			for (Funcionario funcionario : funcionariosDaProfissao) {
				boolean atualiza = funcionario.getProfissao().remove(new Key<Profissao>(Profissao.class, paraDeletar.getId()));
				if (atualiza)
					funcionarioRepo.update(funcionario);
			}
		}
		
		profissaoRepo.destroy(paraDeletar);
	}
	
	@Override
	public List<Funcionario> findFuncionariosPorProfissao(Profissao paraDeletar) {
		return funcionarioRepo.findByProfissao(paraDeletar);
	}

	@Override
	public void criarNovaProfissao(Profissao profissaoParaCriar) {
		Profissao profissaoPesquisada = profissaoRepo.findByName(profissaoParaCriar.getNome());
		if (profissaoPesquisada != null)
			throw new IllegalArgumentException("Profissão já existente");
			
		profissaoRepo.create(profissaoParaCriar);
	}
}
