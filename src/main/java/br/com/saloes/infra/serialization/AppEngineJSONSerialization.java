package br.com.saloes.infra.serialization;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.NoRootSerialization;
import br.com.caelum.vraptor.serialization.Serializer;

@Component
public class AppEngineJSONSerialization implements JSONSerialization {

	private HttpServletResponse response;
	boolean withoutRoot = false;
	
	public AppEngineJSONSerialization(HttpServletResponse response) {
		this.response = response;
	}
	
	@Override
	public boolean accepts(String format) {
		return "json".equals(format);
	}

	@Override
	public <T> Serializer from(T arg0, String arg1) {
		return from(arg0);
	}

	@Override
	public <T> Serializer from(final T bean) {
		Serializer serializer = new Serializer() {
			
			@Override
			public void serialize() {

				try {
					ObjectMapper mapper = new ObjectMapper();
					if (withoutRoot) {
						mapper.getSerializationConfig().set(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
					}
					
					response.setContentType("application/json");
					response.getWriter().print(mapper.writeValueAsString(bean));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public Serializer exclude(String... arg0) {
				return null;
			}

			@Override
			public Serializer include(String... arg0) {
				return null;
			}

			@Override
			public Serializer recursive() {
				return null;
			}

		};

		return serializer;
	}

	@Override
	public JSONSerialization indented() {
		return null;
	}

	@Override
	public <T> NoRootSerialization withoutRoot() {
		withoutRoot = true;
		return this;
	}

}
