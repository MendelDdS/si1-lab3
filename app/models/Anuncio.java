package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;


@Entity
public class Anuncio implements Comparable<Anuncio> {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String titulo;

    @Column
	private String descricao;

    @Column
	private String cidade;

    @Column
	private String bairro;

    @Column
    private String palavraChave;
 
	@Column
    private boolean interesseEmFormarBanda = false;

    @Column
    private boolean interesseEmTocarOcasionalmente = false;

    @Column
	private String estilosGosta;

    @Column
	private String estilosNaoGosta;

    @Column
	private String instrumentos;

    @Column
    private String email;

    @Column
    private String perfilFacebook;
    
    @Temporal(TemporalType.DATE)
    private Date data = new Date();
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comentario> conversas;
    
	@Transient
	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    
    public Anuncio() {
    	this.conversas = new ArrayList<Comentario>();
    }

	public Anuncio(String titulo, String descricao, String cidade, String bairro,String email, String perfilFacebook,
                   String interesse, String palavraChave, String Instrumentos, String estilosGosta, String estilosNaoGosta) throws Exception {

        this.titulo = titulo;
        this.descricao = descricao;
        this.cidade = cidade;
        this.bairro = bairro;
        this.email = email;
        this.palavraChave = palavraChave;
        this.perfilFacebook = perfilFacebook;
        this.estilosGosta = estilosGosta;
        this.estilosNaoGosta = estilosNaoGosta;
        this.instrumentos = Instrumentos;
    	this.conversas = new ArrayList<Comentario>();

    	if (interesse.equals("Banda")) {
        	this.interesseEmFormarBanda = true;
        }       
        else if (interesse.equals("Ocasionalmente")){ 
        	this.interesseEmTocarOcasionalmente = true;
        }
	}

    public Long getId() { return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) throws Exception {
		if (titulo.trim() == "" || titulo == null)
            throw new Exception("Por favor, insira um titulo ao seu anuncio.");
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) throws Exception {
		if (descricao.trim() == "" || descricao == null)
            throw new Exception("Por favor, insira uma descricao ao seu anuncio.");
        this.descricao = descricao;
	}

	public String getCidade() {
		return cidade;
	}
	
	public String getDateFormat() {
		return dateFormat.format(data);
	}
	
	public void setInteresse(String interesse) {
        if (interesse.equals("Banda")) {
        	this.interesseEmFormarBanda = true;
        }       
        else if (interesse.equals("Ocasionalmente")){ 
        	this.interesseEmTocarOcasionalmente = true;
        }
	}

	public void setCidade(String cidade) throws Exception {
		if (cidade.trim() == "" || cidade == null)
            throw new Exception("Por favor, insira sua cidade.");
        this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) throws Exception {
        if (bairro.trim() == "" || bairro.trim() == "")
            throw new Exception("Por favor, insira seu bairro.");
        this.bairro = bairro;
	}
	
    public Date getData() { return data;}

    public void setData(Date data) {
        this.data = data;
    }

    public boolean getInteresseEmFormarBanda() { return interesseEmFormarBanda;}

    public void setInteresseEmFormarBanda(boolean interessado) {
        if (interessado)
            interesseEmFormarBanda = true;
        else
            interesseEmFormarBanda = false;
    }

    public boolean getInteresseEmTocarOcasionalmente() { return interesseEmTocarOcasionalmente;}

    public void setInteresseEmTocarOcasionalmente(boolean interessado) {
        if (interessado)
            interesseEmTocarOcasionalmente = true;
        else
            interesseEmTocarOcasionalmente = false;
    }

    public String getEmail() { return email;}
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getFacebook() { return perfilFacebook;}
  
    public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

    public String getInteresse() {
    	if (getInteresseEmFormarBanda()) {
    		return "Interessado em formar banda";
    	} else {
    		return "Interessado em tocar ocasionalmente";
    	}    	
    }

    public void setEstilosGosta(String estilosGosta) {
        this.estilosGosta = estilosGosta;
    }
    

    public void setEstilosNaoGosta(String estilosNaoGosta) {
        this.estilosNaoGosta = estilosNaoGosta;
    }

    public void setInstrumentos(String Instrumentos) {
         this.instrumentos = Instrumentos;
    }

    public List<Comentario> getConversas() {
        return conversas;
    }

    public void setConversas(List<Comentario> conversas) {
        this.conversas = conversas;
    }
    
    public void fazerPergunta(String pergunta) {
        try {
            conversas.add(new Comentario(pergunta));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void responderPergunta(Long idConversa, String resposta, String codigo) throws Exception {
        if (!codigo.equals(this.palavraChave)) {
            throw new Exception("Somente quem tem o código do anúncio pode responder perguntas.");
        }

        for (Comentario conversa : conversas) {
            if (conversa.getId().equals(idConversa)) {
                try {
                    conversa.setResposta(resposta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
    public void deletarComentario(Long idConversa) {
    	for (Comentario conversa : conversas) {
            if (conversa.getId().equals(idConversa)) {
            	conversas.remove(conversa);
            	break;
            }
        }    	
    }
    
    public void setFacebook(String perfilFacebook) {
    	this.perfilFacebook = perfilFacebook;
    }
    
    public boolean isEstilosGostaEmpty() {
        return this.estilosGosta.length() == 0;
    }

    public boolean isEstilosNaoGostaEmpty() {
        return this.estilosNaoGosta.length() == 0;
    }
    
    @Override
	public int compareTo(Anuncio o) {
        return getData().compareTo(o.getData()) * (-1);
    }

	public String getEstilosGosta() {
		return estilosGosta;
	}

	public String getEstilosNaoGosta() {
		return estilosNaoGosta;
	}

	public String getInstrumentos() {
		return instrumentos;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Anuncio)) {
			return false;
		}
		Anuncio outroAnuncio = (Anuncio) obj;
		return Objects.equals(outroAnuncio.getTitulo(), this.getTitulo());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getTitulo());
	}
}
