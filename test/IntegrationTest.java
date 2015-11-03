
import org.junit.Test;
import play.db.jpa.Transactional;
import play.test.FakeApplication;
import play.test.WithBrowser;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

public class IntegrationTest extends WithBrowser {

    @Override
    public FakeApplication provideFakeApplication() {
        return fakeApplication(inMemoryDatabase());
    }

    @Test
    public void testeCarregarPaginaPrincipal() {
        browser.goTo("http://localhost:" + testServer.port());
    }

    @Test
    public void testeAdicionarAnuncio() {
        browser.goTo("http://localhost:" + testServer.port());
        assertThat(browser.pageSource()).doesNotContain("Testando pelo código");
        browser.click("#botaoAnunciar");

        browser.fill("#inputTitulo").with("Testando pelo código");
        browser.fill("#inputDescricao").with("Anúncio criado nos testes");
        browser.fill("#inputCidade").with("Campina Grande");
        browser.fill("#inputBairro").with("Bodocongó");
        browser.fill("#inputInstrumentos").with("Gaita, Violão, Saxofone");
        browser.fill("#inputEstilosGosta").with("Blues, Rock, Jazz");
        browser.fill("#inputEstilosNaoGosta").with("Pagode, Funk");
        browser.fill("#inputPalavraChave").with("testando123");
        browser.fill("#inputEmail").with("email1");
        browser.fill("#inputFacebook").with("Perfil1");
        browser.fillSelect("#inputContatos1");

        browser.click("#submitNovoAnuncio");
        assertThat(browser.pageSource()).contains("Testando pelo código");
    }
    
    @Test
    public void deveFinalizarAnuncio() {
        testeAdicionarAnuncio();

        browser.goTo("http://localhost:" + testServer.port());
        assertThat(browser.pageSource()).contains("Testando pelo código");

        browser.click(".finalizarAnuncio");
        browser.fill("#inputFinalizar").with("testando123");
        browser.fillSelect("#inputEncontrou1");
        browser.click("#submitFinalizar26");

        assertThat(browser.pageSource()).doesNotContain("Testando pelo código");
    }

}
