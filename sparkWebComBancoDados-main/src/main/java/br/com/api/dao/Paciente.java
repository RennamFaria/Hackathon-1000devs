package br.com.api.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Paciente {
     public static int contador = 0;
    private int id;
    private String nome;
    private char sexo;
    private String cpf;
    private LocalDate dataNascimento;

    // Construtor da classe Paciente
    public Paciente(String nome, char sexo, String cpf, LocalDate dataNascimento) {
        contador++;
        this.id = contador;
        
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setSexo(sexo);
        setDataNascimento(dataNascimento);
    }
    
    public Paciente() {
    }
    
    public void setId(int Id) { 
        this.id = Id; 
    }
    
    public int getId() {
        return id;
    }
    
    public void setNome(String nome) {
        // Implementar validações, se necessário
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setCpf(String cpf) {
        // Implementar validações, se necessário
        this.cpf = cpf;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setSexo(char sexo) {
        // Implementar validações, se necessário
        this.sexo = sexo;
    }
    
    public char getSexo() {
        return sexo;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getDataNascimento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dataNascimento.format(formatter);
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", CPF: " + cpf + ", Sexo: " + sexo 
               + ", Data de Nascimento: " + dataNascimento;
    }
    
}
