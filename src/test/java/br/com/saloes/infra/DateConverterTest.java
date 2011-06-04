package br.com.saloes.infra;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DateConverterTest {

	private DateConverter dateConverter;
	
	@Before
	public void inicia() {
		dateConverter = new DateConverter();
	}
	
	@Test
	public void deveRetornarUmaDataAPartirDeUmaString() {
		
		String dataParaConverter = "12/01/2000";
		Calendar dataConvertida = Calendar.getInstance();
		dataConvertida.setTime(dateConverter.convert(dataParaConverter, null, null));
		Assert.assertEquals(12, dataConvertida.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(0, dataConvertida.get(Calendar.MONTH));
		Assert.assertEquals(2000, dataConvertida.get(Calendar.YEAR));
		
		dataParaConverter = "31/04/2011";
		try {
			dataConvertida.setTime(dateConverter.convert(dataParaConverter, null, null));
			Assert.fail("Nao deveria converter, data invalida");
		} catch (NullPointerException e) {}
	}
	
	@Test
	public void deveRetornarUmaHoraAPartirDeUmaString() {
		
		String dataParaConverter = "12:45";
		Calendar dataConvertida = Calendar.getInstance();
		dataConvertida.setTime(dateConverter.convert(dataParaConverter, null, null));
		Assert.assertEquals(12, dataConvertida.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(45, dataConvertida.get(Calendar.MINUTE));
		
		dataParaConverter = "12:61";
		try {
			dataConvertida.setTime(dateConverter.convert(dataParaConverter, null, null));
			Assert.fail("Nao deveria converter, hora invalida");
		} catch (NullPointerException e) {}
	}

}
