package br.com.saloes.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.ioc.Component;
import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.models.Usuario;
import br.com.saloes.repositories.ConsultaRepository;
import br.com.saloes.repositories.FuncionarioRepository;
import br.com.saloes.repositories.ProfissaoRepository;
import br.com.saloes.repositories.UsuarioRepository;

import com.googlecode.objectify.Key;

@Component
public class ConsultaServiceImpl implements ConsultaService {

	private final ConsultaRepository consultaRepo;
	private final ProfissaoRepository profissaoRepo;
	private final FuncionarioRepository funcionarioRepo;
	private final UsuarioRepository usuarioRepo;

	public ConsultaServiceImpl(ConsultaRepository consultaRepo, ProfissaoRepository profissaoRepo, FuncionarioRepository funcionarioRepo, UsuarioRepository usuarioRepo) {
		this.consultaRepo = consultaRepo;
		this.profissaoRepo = profissaoRepo;
		this.funcionarioRepo = funcionarioRepo;
		this.usuarioRepo = usuarioRepo;
	}

	@Override
	public List<Profissao> findAllProfissoes() {
		return profissaoRepo.findAll();
	}

	@Override
	public void criarNovaConsulta(Consulta consulta, Funcionario funcionario, Profissao servicoEscolhido, Usuario usuarioEscolhido) {
		if (funcionario == null)
			throw new NullPointerException("Funcionário não informado");
		
		funcionario = funcionarioRepo.find(funcionario.getId());
		
		if (funcionario == null)
			throw new NullPointerException("Funcionário não cadastrado");
		
		if (servicoEscolhido == null)
			throw new NullPointerException("Profissão não informada");
		
		servicoEscolhido = profissaoRepo.find(servicoEscolhido.getId());
		
		if (servicoEscolhido == null)
			throw new NullPointerException("Profissão não cadastrada");
		
		if (usuarioEscolhido == null)
			throw new NullPointerException("Não foi informado o cliente para a consulta");
		
		usuarioEscolhido  = usuarioRepo.find(usuarioEscolhido.getId());
		
		if (usuarioEscolhido == null)
			throw new NullPointerException("Cliente não cadastrado");
		
		consulta.setFuncionario(new Key<Funcionario>(Funcionario.class, funcionario.getId()));
		consulta.setProfissao(new Key<Profissao>(Profissao.class, servicoEscolhido.getId()));
		consulta.setUsuario(new Key<Usuario>(Usuario.class, usuarioEscolhido.getId()));
		
		consultaRepo.create(consulta);
	}

	@Override
	public Map<Consulta, Funcionario> recuperaFuncionariosAssociadosAsConsultas(List<Consulta> todasConsultas) {
		Map<Consulta, Funcionario> consultasDosFuncionarios = new HashMap<Consulta, Funcionario>();
		
		for (Consulta consulta : todasConsultas) {
			if (consulta.getFuncionario() == null)
				continue;
			
			Funcionario func = funcionarioRepo.find(consulta.getFuncionario().getId());
			consultasDosFuncionarios.put(consulta, func);
		}
		
 		return consultasDosFuncionarios;
	}

	@Override
	public Map<Consulta, Profissao> recuperaProfissoesAssociadosAsConsultas(List<Consulta> consultas) {
		Map<Consulta, Profissao> consultasDosFuncionarios = new HashMap<Consulta, Profissao>();
		
		for (Consulta consulta : consultas) {
			if (consulta.getProfissao() == null)
				continue;
			
			Profissao profissao = profissaoRepo.find(consulta.getProfissao().getId());
			consultasDosFuncionarios.put(consulta, profissao);
		}
		
		return consultasDosFuncionarios;
	}

	@Override
	public List<Usuario> findAllClientes() {
		return usuarioRepo.findTipoDeUsuarioCliente();
	}

	@Override
	public Map<Consulta, Usuario> recuperaClientesAssociadosAsConsultas(List<Consulta> consultas) {
		Map<Consulta, Usuario> consultasDosClientes = new HashMap<Consulta, Usuario>();
		
		for (Consulta consulta : consultas) {
			if (consulta.getUsuario() == null)
				continue;
			
			Usuario cliente = usuarioRepo.find(consulta.getUsuario().getId());
			consultasDosClientes.put(consulta, cliente);
		}
		
		return consultasDosClientes;
	}
}