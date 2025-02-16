package br.com.api.model;
import java.time.LocalDate;

public class Paciente {
    private int id;
    private String nome;
    private String cpf; 
    private Sexo sexo; //é construído assim porque é um ENUM
    private LocalDate dataNascimento; 

    public Paciente(int id, String nome, String cpf, Sexo sexo, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf; 
        this.sexo = sexo; 
        this.dataNascimento = dataNascimento; 
    }
    
    public Paciente(String nome, String cpf, Sexo sexo, LocalDate dataNascimento) {
        this.nome = nome; 
        this.cpf = cpf; 
        this.sexo = sexo; 
        this.dataNascimento = dataNascimento; 
    }

    public enum Sexo { 
        M, F; 
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento(){
        return dataNascimento;
        
    }

    public void setDataNascimento (LocalDate dataNascimento){
        this.dataNascimento = dataNascimento; 
    }
     
}