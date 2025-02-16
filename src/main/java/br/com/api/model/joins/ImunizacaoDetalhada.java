package br.com.api.model.joins;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ImunizacaoDetalhada {
    private int idImunizacao;
    private String nomePaciente;
    private String nomeVacina;
    private String dose;


    private LocalDate dataAplicacao;
    
    private String fabricante;
    private String lote;
    private String localAplicacao;
    private String profissionalAplicador;

    // Construtor
    public ImunizacaoDetalhada( int idImunizacao, String nomePaciente, String nomeVacina, String dose, 
                                LocalDate dataAplicacao, String fabricante, String lote, String localAplicacao, String profissionalAplicador) {
        this.idImunizacao = idImunizacao;
        this.nomePaciente = nomePaciente;
        this.nomeVacina = nomeVacina;
        this.dose = dose;
        this.dataAplicacao = dataAplicacao;
        this.fabricante = fabricante;
        this.lote = lote;
        this.localAplicacao = localAplicacao;
        this.profissionalAplicador = profissionalAplicador;
    }

    // Getters e Setters
    public int getIdImunizacao() {
        return idImunizacao;
    }

    public void setIdImunizacao(int idImunizacao) {
        this.idImunizacao = idImunizacao;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDataAplicacao() {
        // Formatar a data como String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dataAplicacao.format(formatter); // Retorna no formato "YYYY-MM-DD"
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getLocalAplicacao() {
        return localAplicacao;
    }

    public void setLocalAplicacao(String localAplicacao) {
        this.localAplicacao = localAplicacao;
    }

    public String getProfissionalAplicador() {
        return profissionalAplicador;
    }

    public void setProfissionalAplicador(String profissionalAplicador) {
        this.profissionalAplicador = profissionalAplicador;
    }
}