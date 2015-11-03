package controllers;

import models.Anuncio;
import models.DAO.GenericDAO;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import java.util.Collections;
import java.util.List;

public class Application extends Controller {
    private static int adsHelped = 15;
    private static final GenericDAO DAO = new GenericDAO();
    private static Form<Anuncio> form = Form.form(Anuncio.class);
    private static Form<String> formFinish = Form.form(String.class);

    @Transactional
    public static Result index() {
        return anuncios();
    }

    @Transactional
    public static Result anuncios() {
        List<Anuncio> atualizado = DAO.findAllByClass(Anuncio.class);
        Collections.sort(atualizado);

        return ok(index.render(atualizado, false, adsHelped));
    }
    
    @Transactional
    public static Result novoAnuncio() {
        Form<Anuncio> formPreenchido = form.bindFromRequest();

        if (formPreenchido.hasErrors()) {
            List<Anuncio> atualizado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(atualizado);

            return badRequest(index.render(atualizado, false, adsHelped));
        } else {
            Anuncio newAnuncio = formPreenchido.get();

            DAO.persist(newAnuncio);
            DAO.flush();

            return anuncios();
        }
    }

    @Transactional
    public static Result deletaAnuncio(String codigo, Long id) {
        Form<String> formFinalizarPreenchido = formFinish.bindFromRequest();
        String codigoForm = formFinalizarPreenchido.data().get("finalizar");
        String encontrouParceiros = formFinalizarPreenchido.data().get("encontrouParceiros");

        if (codigoForm.equals(codigo)) {
            DAO.removeById(Anuncio.class, id);
            DAO.flush();

            if (encontrouParceiros.equals("Sim")) {
            	adsHelped++;
            }
            DAO.flush();

            return anuncios();
        } else {
            List<Anuncio> atualizado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(atualizado);

            return ok(index.render(atualizado, true, adsHelped));
        }
    }
    @Transactional
    public static Result fazerPergunta(Long id) {
        Form<String> formPerguntaPreenchido = formFinish.bindFromRequest();
        String pergunta = formPerguntaPreenchido.data().get("pergunta");

        Anuncio anuncio = DAO.findByEntityId(Anuncio.class, id);
        anuncio.fazerPergunta(pergunta);

        DAO.persist(anuncio);
        DAO.flush();

        return adPage(id);
    }
    
	@Transactional
    public static Result adPage(Long id) {
		Anuncio ad = DAO.findByEntityId(Anuncio.class, id);
		return ok(adPage.render(ad));
    }

    @Transactional
    public static Result responderPergunta(Long idConversa, Long id) {
        Form<String> formRespostaPreenchido = formFinish.bindFromRequest();
        String palavraChave = formRespostaPreenchido.data().get("palavraChave");
        String resposta = formRespostaPreenchido.data().get("resposta");

        Anuncio anuncio = DAO.findByEntityId(Anuncio.class, id);

        try {
            anuncio.responderPergunta(idConversa, resposta, palavraChave);
        } catch (Exception e) {
            List<Anuncio> atualizado = DAO.findAllByClass(Anuncio.class);
            Collections.sort(atualizado);

            return badRequest(index.render(atualizado, false, adsHelped));
        }
        DAO.persist(anuncio);
        DAO.flush();
        return adPage(id);
    }
    
    @Transactional
    public static Result comentarioAbusivo(Long idConversa, Long id) {
    	Anuncio anuncio = DAO.findByEntityId(Anuncio.class, id);    	
    	anuncio.deletarComentario(idConversa);    	
        DAO.persist(anuncio);
        DAO.flush();        
        return adPage(id);	
    }
    
    @Transactional
    public static Result sobre() {
    	return ok(views.html.sobre.render());
    }
    
    @Transactional
    public static Result cadastro() {
    	return ok(views.html.cadastro.render());
    }    
}
