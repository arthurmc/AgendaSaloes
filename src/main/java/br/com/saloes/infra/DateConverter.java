package br.com.saloes.infra;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.ioc.ApplicationScoped;

@Convert(Date.class)
@ApplicationScoped
public class DateConverter implements Converter<Date> {

	private final static Logger LOG = Logger.getLogger(DateConverter.class);

	@Override
	public Date convert(String value, Class<? extends Date> type, ResourceBundle bundle) {
		if (value == null || value.equals("")) {
			return null;
		}
		
		Date data = null;
		try {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			data = format.parse(value);
			LOG.warn("Parametro do tipo data: " + value + " passou no conversor de datas");
			return data;
		} catch (Exception e) {
			LOG.warn("Parametro do tipo data: " + value + " nao passou no conversor de datas");
		}
		
		try {
			DateFormat format = new SimpleDateFormat("HH:mm");
			format.setLenient(false);
			data = format.parse(value);
			LOG.warn("Parametro do tipo data: " + value + " passou no conversor de horas");
			return data;
		} catch (Exception e) {
			LOG.warn("Parametro do tipo data: " + value + " nao passou no conversor de horas");
		}

		return data;
	}

}
