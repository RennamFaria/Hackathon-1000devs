package br.com.api.model;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "vacina", "descricao", "limite_aplicacao", "publico_alvo"})
public class Vacina {
    private int id;
    private String vacina;
    private String descricao;
    private int limite_aplicacao;
    private PublicoAlvo publico_alvo; //ENUM

    public Vacina(int id, String vacina, String descricao, int limite_aplicacao) {
        this.id = id;
        this.vacina = vacina;
        this.descricao = descricao;
        this.limite_aplicacao = limite_aplicacao;
    }

    public Vacina(String vacina, int limite_aplicacao, PublicoAlvo publico_alvo) {
        this.vacina = vacina;
        this.limite_aplicacao = limite_aplicacao;
        this.publico_alvo = publico_alvo;
    }

    public Vacina(String vacina, String descricao, int limite_aplicacao) {
        this.vacina = vacina;
        this.descricao = descricao;
        this.limite_aplicacao = limite_aplicacao;
    }
    
    public enum PublicoAlvo {
        CRIANÇA, ADOLESCENTE, ADULTO, GESTANTE;
    }

    

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getVacina() {
        return vacina;
    }
    public void setVacina(String vacina) {
        this.vacina = vacina;
    }


    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public int getLimiteAplicacao(){
        return limite_aplicacao;
    }
    public void setLimiteAplicacao(int limite_aplicacao){
        this.limite_aplicacao = limite_aplicacao;
    }


    public PublicoAlvo getPublicoAlvo(){
        return publico_alvo;
    }
    public void setPublicoAlvo(PublicoAlvo publico_alvo) {
        this.publico_alvo = publico_alvo;
    }

}
