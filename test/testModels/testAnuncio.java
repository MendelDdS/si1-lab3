package testModels;

import models.Anuncio;
import org.junit.*;

/**
 * Created by MendelDdS on 24/05/2015.
 */
public class testAnuncio {
    Anuncio anuncio;

    @Before
    public void setUP() {
        anuncio = new Anuncio();
    }

    @Test
    public void testaDadosDoAnuncio() {
        try {
            anuncio.setTitulo("");
            anuncio.setBairro("Conceicao");
            anuncio.setCidade("Campina Grande");
            anuncio.setDescricao("Estou a procura de uma banda");
        } catch (Exception e) {
            Assert.assertEquals("Por favor, insira um titulo ao seu anuncio.", e.getMessage());
        }

        try {
            anuncio.setTitulo("Procuro banda de Rock");
            anuncio.setBairro("");
            anuncio.setCidade("Campina Grande");
            anuncio.setDescricao("Estou a procura de uma banda");
        } catch (Exception e) {
            Assert.assertEquals("Por favor, insira seu bairro.", e.getMessage());
        }

        try {
            anuncio.setTitulo("Procuro banda de Rock");
            anuncio.setBairro("Conceicao");
            anuncio.setCidade("");
            anuncio.setDescricao("Estou a procura de uma banda");
        } catch (Exception e) {
            Assert.assertEquals("Por favor, insira sua cidade.", e.getMessage());
        }

        try {
            anuncio.setTitulo("Procuro banda de Rock");
            anuncio.setBairro("Conceicao");
            anuncio.setCidade("Campina Grande");
            anuncio.setDescricao("");
        } catch (Exception e) {
            Assert.assertEquals("Por favor, insira uma descricao ao seu anuncio.", e.getMessage());
        }

        try {
            anuncio.setTitulo("Procuro banda de Rock");
            anuncio.setBairro("Conceicao");
            anuncio.setCidade("Campina Grande");
            anuncio.setDescricao("Estou a procura de uma banda");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
