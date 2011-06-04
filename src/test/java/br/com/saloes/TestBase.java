package br.com.saloes;

import java.util.logging.Logger;

import org.junit.BeforeClass;

import br.com.saloes.models.Consulta;
import br.com.saloes.models.Funcionario;
import br.com.saloes.models.Profissao;
import br.com.saloes.models.Usuario;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyOpts;

/**
 * All tests should extend this class to set up the GAE environment.
 * 
 * @see <a
 *      href="http://code.google.com/appengine/docs/java/howto/unittesting.html">Unit
 *      Testing in Appengine</a>
 * 
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class TestBase {
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(TestBase.class.getName());

	protected static ObjectifyFactory fact;

	private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalTaskQueueTestConfig());

	@BeforeClass
	public static void setUp() {
		helper.setUp();

		fact = new ObjectifyFactory() {
			@Override
			public Objectify begin(ObjectifyOpts opts) {
				// This can be used to enable/disable the memory cache globally.
				opts.setGlobalCache(true);

				// This can be used to enable/disable the session caching
				// objectify
				// Note that it will break several unit tests that check for
				// transmutation
				// when entities are run through the DB (ie, unknown List types
				// become
				// ArrayList). These failures are ok.
				opts.setSessionCache(false);

				return super.begin(opts);
			}
		};
		
		fact.register(Usuario.class);
		fact.register(Funcionario.class);
		fact.register(Consulta.class);
		fact.register(Profissao.class);
	}
}