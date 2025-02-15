package br.com.api.dao;

import br.com.api.model.Vacina;
import br.com.api.model.Vacina.PublicoAlvo;
import br.com.api.model.joins.VacinaDoseJoin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class DAOVacina {
    
    public static Connection conexao = null;

    public static ArrayList<VacinaDoseJoin> consultarTodasVacinas() throws SQLException{
        //cria o array list pra receber os dados dos usuarios que retornarao do banco de dados
        ArrayList<VacinaDoseJoin> lista = new ArrayList<VacinaDoseJoin>();

        //define o sql de consulta
        String sql =    "SELECT dose.id, vacina.vacina, dose.dose, dose.idade_recomendada_aplicacao, vacina.limite_aplicacao, vacina.publico_alvo " + 
                        "FROM dose INNER JOIN vacina ON " +
                        "dose.id_vacina = vacina.id;";

        try (   Statement comando = conexao.createStatement(); //cria o comando
                ResultSet resultado = comando.executeQuery(sql); //executa a consulta
            ) {

            //para cada registro retornado do banco de dados
            while(resultado.next()){
            
                // Converter publico_alvo para ENUM
                String publicoAlvoString = resultado.getString("publico_alvo");
                PublicoAlvo publicoAlvoEnum = PublicoAlvo.valueOf(publicoAlvoString);

                Integer limiteAplicacao = resultado.getInt("limite_aplicacao");
                if (resultado.wasNull()) {
                    limiteAplicacao = null;
                }
                //cria um novo objeto Vacina_Dose
                VacinaDoseJoin novaVacina = new VacinaDoseJoin(
                    resultado.getInt("id"), 
                    resultado.getString("vacina"), 
                    resultado.getString("dose"), 
                    resultado.getInt("idade_recomendada_aplicacao"), 
                    limiteAplicacao,
                    publicoAlvoEnum);
                
                //adiciona o objeto usuario no array list
                lista.add(novaVacina);
            }
        } 
        
        //retorna o array list de objetos usuarios
        return lista;

    }

    public static ArrayList<VacinaDoseJoin> consultarVacinasPorFaixaEtaria(String faixaEtaria) throws SQLException{         
        //cria o array list pra receber os dados dos usuarios que retornarao do banco de dados
        ArrayList<VacinaDoseJoin> lista = new ArrayList<VacinaDoseJoin>();

        //define o sql de consulta
        String sql =    "SELECT dose.id, vacina.vacina, dose.dose, dose.idade_recomendada_aplicacao, vacina.limite_aplicacao, vacina.publico_alvo " + 
                        "FROM dose INNER JOIN vacina ON " + 
                        "dose.id_vacina = vacina.id WHERE publico_alvo = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql) //cria o comando para receber sql dinamico
        ) { 
            //substitui a ? pelo codigo do usuario
            comando.setString(1, faixaEtaria);

            //executa o comando sql
            ResultSet resultado = comando.executeQuery();

            //para cada registro retornado do banco de dados
            while(resultado.next()){
                
                // Converter publico_alvo para ENUM
                String publicoAlvoString = resultado.getString("publico_alvo");
                PublicoAlvo publicoAlvoEnum = PublicoAlvo.valueOf(publicoAlvoString);

                //cria um novo objeto usuario
                VacinaDoseJoin novaVacina = new VacinaDoseJoin(
                    resultado.getInt("id"), 
                    resultado.getString("vacina"), 
                    resultado.getString("dose"), 
                    resultado.getInt("idade_recomendada_aplicacao"), 
                    resultado.getInt("limite_aplicacao"),
                    publicoAlvoEnum);
                
                
                //adiciona o objeto vacina no array list
                lista.add(novaVacina);
            }

        }

                //retorna o array list de objetos usuarios
                return lista;
                
    }

    public static ArrayList<VacinaDoseJoin> consultarVacinaDisponivelAcimaIdade(int idade) throws SQLException{         
        //cria o array list pra receber os dados dos usuarios que retornarao do banco de dados
        ArrayList<VacinaDoseJoin> lista = new ArrayList<VacinaDoseJoin>();

        //define o sql de consulta
        String sql =    "SELECT dose.id, vacina.vacina, dose.dose, dose.idade_recomendada_aplicacao, vacina.limite_aplicacao, vacina.publico_alvo " + 
                        "FROM dose INNER JOIN vacina ON " + 
                        "dose.id_vacina = vacina.id WHERE idade_recomendada_aplicacao > ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql) //cria o comando para receber sql dinamico
        ) { 
            //substitui a ? pelo codigo do usuario
            comando.setInt(1, idade);

            //executa o comando sql
            ResultSet resultado = comando.executeQuery();

            //para cada registro retornado do banco de dados
            while(resultado.next()){
                
                // Converter publico_alvo para ENUM
                String publicoAlvoString = resultado.getString("publico_alvo");
                PublicoAlvo publicoAlvoEnum = PublicoAlvo.valueOf(publicoAlvoString);

                
                //cria um novo objeto usuario
                VacinaDoseJoin novaVacina = new VacinaDoseJoin(
                    resultado.getInt("id"), 
                    resultado.getString("vacina"), 
                    resultado.getString("dose"), 
                    resultado.getInt("idade_recomendada_aplicacao"), 
                    resultado.getInt("limite_aplicacao"),
                    publicoAlvoEnum);

                
                
                //adiciona o objeto vacina no array list
                lista.add(novaVacina);
            }
        }

                //retorna o array list de objetos usuarios
                return lista;
                
    }

    public static ArrayList<VacinaDoseJoin> consultarVacinaNaoAplicavel (int idade) throws SQLException {
        //cria o array list pra receber os dados dos usuarios que retornarao do banco de dados
        ArrayList<VacinaDoseJoin> lista = new ArrayList<VacinaDoseJoin>();
        System.out.println(idade);
        //define o sql de consulta
        String sql =    "SELECT dose.id, vacina.vacina, dose.dose, vacina.limite_aplicacao " +
                        "FROM dose INNER JOIN vacina ON " +
                        "dose.id_vacina = vacina.id WHERE limite_aplicacao < ?";        

        try (PreparedStatement comando = conexao.prepareStatement(sql) //cria o comando para receber sql dinamico
        ) { 
            //substitui a ? pela idade
            comando.setInt(1, idade);

            //executa o comando sql
            ResultSet resultado = comando.executeQuery();

            //para cada registro retornado do banco de dados
            while(resultado.next()){

                //cria um novo objeto usuario
                VacinaDoseJoin novaVacina = new VacinaDoseJoin(
                    resultado.getInt("id"), 
                    resultado.getString("vacina"), 
                    resultado.getString("dose"), 
                    resultado.getInt("limite_aplicacao")
                    );
                
                //adiciona o objeto vacina no array list
                lista.add(novaVacina);
            }
        }

                //retorna o array list de objetos usuarios
                return lista;
    }

}
