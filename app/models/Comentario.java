package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comentario {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String pergunta;

    @Column
    private String resposta;

    public Comentario() {

    }

    public Comentario(String pergunta) throws Exception {
        this.pergunta = pergunta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) throws Exception {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) throws Exception {
        this.resposta = resposta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}