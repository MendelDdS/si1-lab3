import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.*;

import controllers.Application;
import models.Anuncio;
import models.Comentario;
import models.DAO.GenericDAO;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.FakeRequest;
import play.test.Helpers;
import scala.Option;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {
	private EntityManager entityManager;
	private static FakeApplication fake;
    private GenericDAO DAO = new GenericDAO();

	@BeforeClass
	public static void startApp() {
		fake = Helpers.fakeApplication(new Global());
		Helpers.start(fake);
	}

    @AfterClass
    public static void stopApp() {
        Helpers.stop(fake);
    }
    
	@Before
	public void setUp() {
        Option<JPAPlugin> jpaPlugin = fake.getWrappedApplication().plugin(JPAPlugin.class);
        entityManager = jpaPlugin.get().em("default");
        JPA.bindForCurrentThread(entityManager);
        entityManager.getTransaction().begin();
	}
	
	@Test
	public void deveCarregarIndex() {
		Result result = Application.index();
		assertThat(status(result)).isEqualTo(OK);
	}
	
	@Test
	public void deveCarregarCadastro() {
		Result result = callAction(controllers.routes.ref.Application.cadastro(), new FakeRequest());
		assertThat(status(result)).isEqualTo(OK);
	}
	
	@Test
	public void precisaCriarAnuncio() {
		Map<String, String> requestMap = new HashMap<>();
		requestMap.put("titulo", "TituloTest");
		requestMap.put("descricao", "Teste de application");
		requestMap.put("cidade", "Campina Grande");
		requestMap.put("bairro", "Jose Pinheiro");
		requestMap.put("email", "email@test.com");
		requestMap.put("facebook", "perfil1");
		requestMap.put("palavraChave", "passTest");
		requestMap.put("instrumentos", "teste, teste");
		requestMap.put("interesse", "Ocasionalmente");
		requestMap.put("estilosGosta", "testando, testando");
		requestMap.put("estilosNaoGosta", "testando2, testando1");
		
		FakeRequest fakeRequest = new FakeRequest().withFormUrlEncodedBody(requestMap);
		Result resultPost = callAction(controllers.routes.ref.Application.novoAnuncio(), fakeRequest);
		assertThat(status(resultPost)).isEqualTo(OK);
		
		Result resultGet = callAction(controllers.routes.ref.Application.index(), new FakeRequest());
		assertThat(status(resultGet)).isEqualTo(OK);
		assertThat(contentType(resultGet)).isEqualTo("text/html");
		assertThat(contentAsString(resultGet)).contains("TituloTest");
		
	}
	
	@Test
    public void testeFazerPerguntaEResposta() {
        List<Anuncio> anuncios = DAO.findAllByClass(Anuncio.class);
        Anuncio anuncioTest = anuncios.get(0);

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pergunta", "Testando pergunta");

        FakeRequest fakeRequest = new FakeRequest().withFormUrlEncodedBody(requestMap);
        Result resultPost = callAction(controllers.routes.ref.Application.fazerPergunta(anuncioTest.getId()), fakeRequest);
        assertThat(status(resultPost)).isEqualTo(OK);
        assertThat(contentAsString(resultPost)).contains("Testando pergunta");

        Comentario conversa = anuncioTest.getConversas().get(0);

        Map<String, String> requestMap2 = new HashMap<>();
        requestMap2.put("resposta", "Testando pergunta");
        requestMap2.put("palavraChave", "1231");

        FakeRequest fakeRequest2 = new FakeRequest().withFormUrlEncodedBody(requestMap2);
        Result resultPost2 = callAction(controllers.routes.ref.Application.responderPergunta(conversa.getId(), anuncioTest.getId()), fakeRequest2);
        assertThat(status(resultPost2)).isEqualTo(OK);
        assertThat(contentAsString(resultPost2)).contains("Testando pergunta");
    }

    @After
    public void tearDown() {
    	entityManager.getTransaction().commit();
        JPA.bindForCurrentThread(null);
        entityManager.close();
    }
}
