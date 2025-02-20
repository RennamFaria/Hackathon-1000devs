package br.com.api.model;

public class Dose {
    
    private int id;
    private int id_vacina;
    private String dose;
    private int idade_recomendada_aplicacao;
    
    public Dose(int id, int id_vacina, String dose, int idade_recomendada_aplicacao) {
        this.id = id;
        this.id_vacina = id_vacina;
        this.dose = dose;
        this.idade_recomendada_aplicacao = idade_recomendada_aplicacao;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId_vacina() {
        return id_vacina;
    }
    public void setId_vacina(int id_vacina) {
        this.id_vacina = id_vacina;
    }
    public String getDose() {
        return dose;
    }
    public void setDose(String dose) {
        this.dose = dose;
    }
    public int getIdade_recomendada_aplicacao() {
        return idade_recomendada_aplicacao;
    }
    public void setIdade_recomendada_aplicacao(int idade_recomendada_aplicacao) {
        this.idade_recomendada_aplicacao = idade_recomendada_aplicacao;
    }
}
