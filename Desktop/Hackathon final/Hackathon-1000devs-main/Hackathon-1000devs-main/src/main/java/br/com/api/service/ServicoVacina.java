package br.com.api.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.dao.DAOPaciente;
import br.com.api.dao.DAOVacina;
import br.com.api.model.Vacina;
import br.com.api.model.joins.VacinaDoseJoin;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoVacina {

    //método que busca todas as vacinas
    public static Route consultarTodasVacinas() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();

                //busca todos as vacinas cadastrados no array list
                //é necessário realizar um cast explicito para converter o retorno 
                //do servicoVacinas
                ArrayList<VacinaDoseJoin> vacinas = DAOVacina.consultarTodasVacinas();

                //se o arraylist estiver vazio
                if (vacinas.isEmpty()) {
                    response.status(404); // 404 - nada encontrado
                    return "{\"message\": \"Nenhuma vacina encontrada no banco de dados.\"}";
                } else {
                    //se nao estiver vazio devolve o arraylist convertido para json
                    response.status(200); // 200 Ok
                    return converteJson.writeValueAsString(vacinas);
                }
            }
        };
    }

    //método que busca vacinas por faixa etaria
    public static Route consultarVacinaPorFaixaEtaria(){
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();

                String faixaEtaria = null;
                
                try {
                    //pegando valor da url
                    faixaEtaria = request.params(":faixa").toUpperCase(); 
                    
                    // Verifica se o valor informado está no Enum
                    Vacina.PublicoAlvo.valueOf(faixaEtaria); // Tenta converter a string em um valor do Enum

                    //busca todos as vacinas cadastrados no array list
                    //é necessário realizar um cast explicito para converter o retorno 
                    //do servicoVacinas
                    ArrayList<VacinaDoseJoin> vacinas = DAOVacina.consultarVacinasPorFaixaEtaria(faixaEtaria);

                    if (vacinas.isEmpty()) {
                        response.status(404); // 404
                        return "{\"message\": \"Nenhuma vacina encontrada no banco de dados.\"}";
                    } else {
                        //se nao estiver vazio devolve o arraylist convertido para json
                        response.status(200); // 200 Ok
                        return converteJson.writeValueAsString(vacinas);
                    }
                } catch (IllegalArgumentException e) {
                    response.status(400);
                    return "{\"error\": \"Faixa etária inválida. Valores permitidos: CRIANÇA, ADOLESCENTE, ADULTO, GESTANTE.\"}";
                }
                
                    
                
            }
        };
    }


    //Método que buscas vacinas disponíveis acima de uma idade(meses)
    public static Route consultarVacinaDisponivelAcimaIdade(){
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();
                int idade;

                try {
                    //pegando valor da url
                    idade = Integer.parseInt(request.params(":meses").toUpperCase());   

                    //busca todos as vacinas cadastrados no array list
                    //é necessário realizar um cast explicito para converter o retorno 
                    //do servicoVacinas
                    ArrayList<VacinaDoseJoin> vacinas = DAOVacina.consultarVacinaDisponivelAcimaIdade(idade);

                    if (vacinas.isEmpty()) {
                        response.status(404); // 404
                        return "{\"message\": \"Nenhuma vacina encontrada no banco de dados.\"}";
                    } else {
                        //se nao estiver vazio devolve o arraylist convertido para json
                        response.status(200); // 200 Ok
                        return converteJson.writeValueAsString(vacinas);
                    }
                } catch (IllegalArgumentException e) {
                    response.status(400);
                    return "{\"error\": \"Idade inserida inválida, informe o valor em meses\"}";
                }
                
                
                
            }
        };
    }
    
    public static Route consultarVacinaNaoAplicavel(){
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();
                
                int id;
                int idade;

                try {
                    
                    //pegando valor da url
                    id = Integer.parseInt(request.params(":id"));   

                    //Buscar data de nascimento no banco
                    LocalDate dataNascimento = DAOPaciente.buscarDataNascimento(id);

                    if (dataNascimento == null) {
                        response.status(404);
                        return "Paciente não encontrado";
                    }

                    //Converter idade para meses
                    idade = Period.between(dataNascimento, LocalDate.now()).getYears() * 12 + Period.between(dataNascimento, LocalDate.now()).getMonths();
                    
                    
                    //Chamar método de vacinas não aplicáveis
                    ArrayList<VacinaDoseJoin> vacinas = DAOVacina.consultarVacinaNaoAplicavel(idade);

                    if (vacinas.isEmpty()) {
                        response.status(404); // 404
                        return "{\"message\": \"Nenhuma vacina encontrada no banco de dados.\"}";
                    } else {
                        //se nao estiver vazio devolve o arraylist convertido para json
                        response.status(200); // 200 Ok
                        return converteJson.writeValueAsString(vacinas);
                    }
                } catch (IllegalArgumentException e) {
                    response.status(400);
                    return "{\"error\": \"Id inserido é invalido\"}";
                }
                
                
                
            }
        };
    }
}