package br.com.saloes.repositories;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.saloes.TestBase;
import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class ConsultaRepositoryImplTest extends TestBase {

	private ConsultaRepositoryImpl consultaRepo;
	private FuncionarioRepositoryImpl funcionarioRepo;
	private Objectify ofy;
	
	@Before
	public void inicia() {
		ofy = fact.begin();
		consultaRepo = new ConsultaRepositoryImpl(ofy);
		funcionarioRepo = new FuncionarioRepositoryImpl(ofy);
	}
	
    @Test
    public void deveRecuperarAsConsultasDeUmFuncionarioDeUmCertoDia() {
    	Funcionario fatima = new Funcionario();
    	fatima.setNome("fatima");
    	funcionarioRepo.create(fatima);
    	
    	Consulta consultaDaFatima = new Consulta();
    	Calendar diaConsulta1 = Calendar.getInstance();
    	diaConsulta1.set(2011, 4, 17);
    	
    	consultaDaFatima.setFuncionario(new Key<Funcionario>(Funcionario.class, fatima.getId()));
    	consultaDaFatima.setDia(diaConsulta1.getTime());
    	consultaRepo.create(consultaDaFatima);
    	
    	diaConsulta1.set(2011, 4, 17);
    	List<Consulta> consultas = consultaRepo.findConsultasDeUmFuncionarioDeUmDeterminadoDia(fatima, diaConsulta1.getTime());
    	Assert.assertNotNull(consultas);
    	Assert.assertEquals(consultas.size(), 1);
	}
}