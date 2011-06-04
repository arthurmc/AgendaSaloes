package br.com.saloes.models;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import com.googlecode.objectify.Key;

public class ConsultaTest {

	@Test(expected=IllegalStateException.class)
	public void deveLancarUmaExcecaoSeInformarProfissaoDaConsultaJaMarcadaDiferenteDaProfissaoDaConsulta() {
		Calendar horaDaConsulta = Calendar.getInstance();
		horaDaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaConsulta.set(Calendar.MINUTE, 30);

		Profissao manicure = new Profissao();
		manicure.setId(3l);
		manicure.setTempoQueConsome("40");
		
		Profissao pedicure = new Profissao();
		pedicure.setTempoQueConsome("40");
		pedicure.setId(2l);
		
		Consulta c = new Consulta();
		c.setHora(horaDaConsulta.getTime());
		c.setProfissao(new Key<Profissao>(Profissao.class, manicure.getId()));		
		
		Calendar horaDaNovaConsulta = Calendar.getInstance();
		horaDaNovaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaNovaConsulta.set(Calendar.MINUTE, 35);
		
		Profissao depiladora = new Profissao();
		depiladora.setTempoQueConsome("30");
		
		Assert.assertTrue(c.temConflitoComNovoHorario(pedicure, horaDaNovaConsulta, depiladora));
	}
	
	@Test
	public void haLimiteDeCincoMinutosParaNaoConflitagemDeHorarios() {
		Calendar horaDaConsulta = Calendar.getInstance();
		horaDaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaConsulta.set(Calendar.MINUTE, 30);

		Profissao manicure = new Profissao();
		manicure.setId(3l);
		manicure.setTempoQueConsome("40");
		
		Consulta c = new Consulta();
		c.setHora(horaDaConsulta.getTime());
		c.setProfissao(new Key<Profissao>(Profissao.class, manicure.getId()));
		
		Calendar horaDaNovaConsulta = Calendar.getInstance();
		horaDaNovaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaNovaConsulta.set(Calendar.MINUTE, 5);
		
		Profissao depiladora = new Profissao();
		depiladora.setTempoQueConsome("30");
		
		Assert.assertFalse(c.temConflitoComNovoHorario(manicure, horaDaNovaConsulta, depiladora));
	}
	
	@Test
	public void conflitoDeHorariosAoPassarDe5minutosDeTolerancia() {
		Calendar horaDaConsulta = Calendar.getInstance();
		horaDaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaConsulta.set(Calendar.MINUTE, 30);

		Profissao manicure = new Profissao();
		manicure.setId(3l);
		manicure.setTempoQueConsome("40");
		
		Consulta c = new Consulta();
		c.setHora(horaDaConsulta.getTime());
		c.setProfissao(new Key<Profissao>(Profissao.class, manicure.getId()));
		
		Calendar horaDaNovaConsulta = Calendar.getInstance();
		horaDaNovaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaNovaConsulta.set(Calendar.MINUTE, 6);
		
		Profissao servicoDaProximaConsulta = new Profissao();
		servicoDaProximaConsulta.setTempoQueConsome("30");
		
		Assert.assertTrue(c.temConflitoComNovoHorario(manicure, horaDaNovaConsulta, servicoDaProximaConsulta));
	}
	
	@Test
	public void deveTerConflitoSeNovoHorarioForDuranteOTempoDaConsulta() {
		Calendar horaDaConsulta = Calendar.getInstance();
		horaDaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaConsulta.set(Calendar.MINUTE, 30);

		Profissao manicure = new Profissao();
		manicure.setId(3l);
		manicure.setTempoQueConsome("40");
		
		Consulta c = new Consulta();
		c.setHora(horaDaConsulta.getTime());
		c.setProfissao(new Key<Profissao>(Profissao.class, manicure.getId()));
		
		Calendar horaDaNovaConsulta = Calendar.getInstance();
		horaDaNovaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaNovaConsulta.set(Calendar.MINUTE, 40);
		
		Profissao depiladora = new Profissao();
		depiladora.setTempoQueConsome("30");
		
		Assert.assertTrue(c.temConflitoComNovoHorario(manicure, horaDaNovaConsulta, depiladora));
	}

	@Test
	public void deveTerConflitoSeNovoHorarioForDuranteOTempoDaConsulta2() {
		Calendar horaDaConsulta = Calendar.getInstance();
		horaDaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaConsulta.set(Calendar.MINUTE, 30);

		Profissao manicure = new Profissao();
		manicure.setId(3l);
		manicure.setTempoQueConsome("40");
		
		Consulta c = new Consulta();
		c.setHora(horaDaConsulta.getTime());
		c.setProfissao(new Key<Profissao>(Profissao.class, manicure.getId()));
		
		Calendar horaDaNovaConsulta = Calendar.getInstance();
		horaDaNovaConsulta.set(Calendar.HOUR_OF_DAY, 9);
		horaDaNovaConsulta.set(Calendar.MINUTE, 4);
		
		Profissao depiladora = new Profissao();
		depiladora.setTempoQueConsome("30");
		
		Assert.assertTrue(c.temConflitoComNovoHorario(manicure, horaDaNovaConsulta, depiladora));
	}

	@Test
	public void naoDeveTerConflitoSeNovoHorarioForDepoisDoTempoDaConsulta() {
		Calendar horaDaConsulta = Calendar.getInstance();
		horaDaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaConsulta.set(Calendar.MINUTE, 30);
		
		Profissao manicure = new Profissao();
		manicure.setId(3l);
		manicure.setTempoQueConsome("40");
		
		Consulta c = new Consulta();
		c.setHora(horaDaConsulta.getTime());
		c.setProfissao(new Key<Profissao>(Profissao.class, manicure.getId()));
		
		Calendar horaDaNovaConsulta = Calendar.getInstance();
		horaDaNovaConsulta.set(Calendar.HOUR_OF_DAY, 9);
		horaDaNovaConsulta.set(Calendar.MINUTE, 5);
		
		Profissao depiladora = new Profissao();
		depiladora.setTempoQueConsome("30");
		
		Assert.assertFalse(c.temConflitoComNovoHorario(manicure, horaDaNovaConsulta, depiladora));
	}
	
	@Test
	public void naoDeveTerConflitoSeNovoHorarioForDepoisDoTempoDaConsulta2() {
		Calendar horaDaConsulta = Calendar.getInstance();
		horaDaConsulta.set(Calendar.HOUR_OF_DAY, 8);
		horaDaConsulta.set(Calendar.MINUTE, 30);
		
		Profissao manicure = new Profissao();
		manicure.setId(3l);
		manicure.setTempoQueConsome("40");
		
		Consulta c = new Consulta();
		c.setHora(horaDaConsulta.getTime());
		c.setProfissao(new Key<Profissao>(Profissao.class, manicure.getId()));
		
		Calendar horaDaNovaConsulta = Calendar.getInstance();
		horaDaNovaConsulta.set(Calendar.HOUR_OF_DAY, 9);
		horaDaNovaConsulta.set(Calendar.MINUTE, 20);
		
		Profissao depiladora = new Profissao();
		depiladora.setTempoQueConsome("30");
		
		Assert.assertFalse(c.temConflitoComNovoHorario(manicure, horaDaNovaConsulta, depiladora));
	}
	
	@Test
	public void deveCompararDuasDatasEDescobrirQualEhMenor() throws ParseException {
		SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
		
		Consulta consulta1 = new Consulta();
		consulta1.setDia(sdfData.parse("08/05/2011"));
		consulta1.setHora(sdfHora.parse("12:00"));
		
		Consulta consulta2 = new Consulta();
		consulta2.setDia(sdfData.parse("08/05/2011"));
		consulta2.setHora(sdfHora.parse("12:30"));
		
		assertEquals(-1, consulta1.compareTo(consulta2));
		
		consulta1.setHora(sdfHora.parse("12:00"));
		consulta2.setHora(sdfHora.parse("12:00"));
		
		assertEquals(0, consulta1.compareTo(consulta2));
		
		consulta1.setHora(sdfHora.parse("12:30"));
		consulta2.setHora(sdfHora.parse("12:00"));
		
		assertEquals(1, consulta1.compareTo(consulta2));
		
		consulta1.setDia(sdfData.parse("07/05/2011"));
		consulta1.setHora(sdfHora.parse("12:00"));
		consulta2.setDia(sdfData.parse("08/05/2011"));
		consulta2.setHora(sdfHora.parse("12:00"));
		
		assertEquals(-1, consulta1.compareTo(consulta2));
		
		consulta1.setDia(sdfData.parse("09/05/2011"));
		consulta2.setDia(sdfData.parse("08/05/2011"));
		
		assertEquals(1, consulta1.compareTo(consulta2));
	}
}