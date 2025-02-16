package br.com.api.model.joins;

import br.com.api.model.Vacina.PublicoAlvo;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Oculta apenas os campos nulos
public class VacinaDoseJoin { // DTO para representar a junção das tabelas
    private int doseId;
    private String vacinaNome;
    private String doseDescricao;
    private Integer idadeRecomendada;
    private Integer limiteAplicacao;
    private PublicoAlvo publicoAlvo;

    public VacinaDoseJoin(     int doseId, String vacinaNome, String doseDescricao, 
                            Integer idadeRecomendada, Integer limiteAplicacao, PublicoAlvo publicoAlvo) {
        
        this.doseId = doseId;
        this.vacinaNome = vacinaNome;
        this.doseDescricao = doseDescricao;
        this.idadeRecomendada = idadeRecomendada;
        this.limiteAplicacao = limiteAplicacao;
        this.publicoAlvo = publicoAlvo;
    }

    public VacinaDoseJoin( int doseId, String vacinaNome, String doseDescricao, Integer limiteAplicacao){
        this.doseId = doseId;
        this.vacinaNome = vacinaNome;
        this.doseDescricao = doseDescricao;
        this.limiteAplicacao = limiteAplicacao;
    }

    public int getDoseId() {
        return doseId;
    }

    public void setDoseId(int doseId) {
        this.doseId = doseId;
    }

    public String getVacinaNome() {
        return vacinaNome;
    }

    public void setVacinaNome(String vacinaNome) {
        this.vacinaNome = vacinaNome;
    }

    public String getDoseDescricao() {
        return doseDescricao;
    }

    public void setDoseDescricao(String doseDescricao) {
        this.doseDescricao = doseDescricao;
    }

    public Integer getIdadeRecomendada() {
        return idadeRecomendada;
    }

    public void setIdadeRecomendada(Integer idadeRecomendada) {
        this.idadeRecomendada = idadeRecomendada;
    }
    
    @JsonInclude(JsonInclude.Include.ALWAYS)
    public Integer getLimiteAplicacao() {
        return limiteAplicacao;
    }

    public void setLimiteAplicacao(Integer limiteAplicacao) {
        this.limiteAplicacao = limiteAplicacao;
    }

    public PublicoAlvo getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(PublicoAlvo publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
}

 













// package br.com.api.model.joins;

// import br.com.api.model.Vacina.PublicoAlvo;
// //join para listar todas as vacinas.

// public class Vacina_Dose {
//     int dose_id;
//     String vacina_vacina;
//     String dose_dose;
//     int dose_idade_recomendada_aplicacao;
//     int vacina_limite_aplicacao;
//     PublicoAlvo publico_alvo;
    
    
    
    
//     public Vacina_Dose(int dose_id, String vacina_vacina, String dose_dose, int dose_idade,
//         int dose_idade_recomendada_aplicacao, int vacina_limite_aplicacao, PublicoAlvo publico_alvo) {
        
//         this.dose_id = dose_id;
//         this.vacina_vacina = vacina_vacina;
//         this.dose_dose = dose_dose;
//         this.dose_idade_recomendada_aplicacao = dose_idade_recomendada_aplicacao;
//         this.vacina_limite_aplicacao = vacina_limite_aplicacao;
//         this.publico_alvo = publico_alvo;
//     }

//     public enum PublicoAlvo {
//         CRIANÇA, ADOLESCENTE, ADULTO, GESTANTE;
//     }

//     public int getDose_id() {
//         return dose_id;
//     }
//     public void setDose_id(int dose_id) {
//         this.dose_id = dose_id;
//     }
//     public String getVacina_vacina() {
//         return vacina_vacina;
//     }
//     public void setVacina_vacina(String vacina_vacina) {
//         this.vacina_vacina = vacina_vacina;
//     }
//     public String getDose_dose() {
//         return dose_dose;
//     }
//     public void setDose_dose(String dose_dose) {
//         this.dose_dose = dose_dose;
//     }

//     public int getDose_idade_recomendada_aplicacao() {
//         return dose_idade_recomendada_aplicacao;
//     }
//     public void setDose_idade_recomendada_aplicacao(int dose_idade_recomendada_aplicacao) {
//         this.dose_idade_recomendada_aplicacao = dose_idade_recomendada_aplicacao;
//     }
//     public int getVacina_limite_aplicacao() {
//         return vacina_limite_aplicacao;
//     }
//     public void setVacina_limite_aplicacao(int vacina_limite_aplicacao) {
//         this.vacina_limite_aplicacao = vacina_limite_aplicacao;
//     }
//     public PublicoAlvo getPublico_alvo() {
//         return publico_alvo;
//     }
//     public void setPublico_alvo(PublicoAlvo pubblico_alvo) {
//         this.publico_alvo = pubblico_alvo;
//     }
// }
