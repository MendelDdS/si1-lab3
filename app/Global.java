import models.Anuncio;
import play.*;

import models.DAO.GenericDAO;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

    private static GenericDAO DAO = new GenericDAO();

    @Override
    public void onStart(Application app) {
    	super.onStart(app);
    	
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
        	@Override
            public void invoke() throws Throwable {
                for (int i = 1; i <= 25; i++) {
                    Anuncio anuncio = new Anuncio();
                    anuncio.setTitulo("Titulo " + i);
                    anuncio.setDescricao("Isto foi uma descrição " + i);
                    anuncio.setCidade("Cidade " + i);
                    anuncio.setBairro("Setor " + i);
                    anuncio.setEmail("Email" + i + "@test.com");
                    anuncio.setFacebook("Perfil" + i);
                    anuncio.setInstrumentos("Instrumentos " + i);
                    anuncio.setEstilosGosta("Estilos que gosto " + i);
                    anuncio.setEstilosNaoGosta("Estilos que não gosto " + i);

                    if (i % 2 == 0) {
                        anuncio.setInteresse("Banda");
                    } else {
                        anuncio.setInteresse("Ocasionalmente");
                    }

                    anuncio.setPalavraChave("123" + i);

                    DAO.persist(anuncio);
                    DAO.flush();
                }
            }
        });
    }
}
