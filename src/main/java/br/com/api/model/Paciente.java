package br.com.api.model;
import java.time.LocalDate;

public class Paciente {
    private int id;
    private String nome;
    private String cpf; 
    private Sexo sexo; //é construído assim porque é um ENUM
    private LocalDate data_nascimento; 

    public Paciente(int id, String nome, String cpf, Sexo sexo, LocalDate data_nascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf; 
        this.sexo = sexo; 
        this.data_nascimento = data_nascimento; 
    }
    
    public Paciente(String nome, String cpf, Sexo sexo, LocalDate data_nascimento) {
        this.nome = nome; 
        this.cpf = cpf; 
        this.sexo = sexo; 
        this.data_nascimento = data_nascimento; 
    }

    public static enum Sexo { 
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

    public LocalDate getdata_nascimento(){
        return data_nascimento;
        
    }

    public void setdata_nascimento (LocalDate data_nascimento){
        this.data_nascimento = data_nascimento; 
    }
     
}