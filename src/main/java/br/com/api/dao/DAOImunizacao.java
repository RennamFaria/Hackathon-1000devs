package br.com.api.dao;

import br.com.api.model.Imunizacao;
import br.com.api.model.joins.ImunizacaoDetalhada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;


import java.util.ArrayList;

public class DAOImunizacao {
    //atributo utilizado para receber a conexao criada no metodo main
    public static Connection conexao = null;
    
    public static int cadastrarImunizacao(Imunizacao imunizacao) throws SQLException{
        //Define a consulta sql 
        String sql =    "INSERT INTO imunizacoes (id_paciente, id_dose, data_aplicacao, fabricante, lote, local_aplicacao, profissional_aplicador) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);";

        //Statement.RETURN_GENERATED_KEYS parametro que diz que o banco de dados deve retornar o id da chave primaria criada
        try(PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            //adiciona os valores de nome e mail no lugar das ? da string sql
            comando.setInt(1, imunizacao.getIdPaciente());
            comando.setInt(2, imunizacao.getIdDose());
            
            // Converte LocalDate para java.sql.Date
            LocalDate localDate = imunizacao.getDataAplicacao();
            Date sqlDate = Date.valueOf(localDate); // Converte LocalDate para java.sql.Date
            comando.setDate(3, sqlDate);

            comando.setString(4, imunizacao.getFabricante());
            comando.setString(5, imunizacao.getLote());
            comando.setString(6, imunizacao.getLocalAplicacao());
            comando.setString(7, imunizacao.getProfissionalAplicador());

            //envia o sql para o banco de dados
            comando.executeUpdate();

            //obtem o resultado retornado do banco de dados
            //getGenerateKeys ira retornar o id da chave primaria que o banco de dados criou
            try (ResultSet idGerado = comando.getGeneratedKeys()) {
                //verifica se o banco de dados retornou um id
                if (idGerado.next()) { 
                    //retorna o id gerado no banco de dados
                    return idGerado.getInt(1);
                }
            }
        }
        //Caso o fluxo de execucao chegue ate este ponto é porque ocorreu algum erro
        //Gera uma excecao de negocio dizendo que nenhum id foi gerado
        throw new SQLException("Erro ao inserir imunização: nenhum ID gerado.");
    }

    public static int atualizarImunizacao(Imunizacao imunizacao) throws SQLException{
        //define o sql 
        String sql = "UPDATE imunizacoes SET id_paciente = ?, id_dose = ?, data_aplicacao = ?, fabricante = ?, lote = ?, local_aplicacao = ?, profissional_aplicador = ? WHERE id = ?";

        try (
            PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao para receber o sql dinamico
        ) {
            comando.setInt(1, imunizacao.getIdPaciente());
            comando.setInt(2, imunizacao.getIdDose());
            
            // Converte LocalDate para java.sql.Date
            LocalDate localDate = imunizacao.getDataAplicacao();
            Date sqlDate = Date.valueOf(localDate); // Converte LocalDate para java.sql.Date
            comando.setDate(3, sqlDate);

            comando.setString(4, imunizacao.getFabricante());
            comando.setString(5, imunizacao.getLote());
            comando.setString(6, imunizacao.getLocalAplicacao());
            comando.setString(7, imunizacao.getProfissionalAplicador());
            comando.setInt(8, imunizacao.getId());

            //executa a atualizacao e armazena o retorno do banco com a quantidade de linhas atualizadas
            int qtdeLinhasAlteradas = comando.executeUpdate();

            //retorna a quantidade de linhas atualizadas
            return qtdeLinhasAlteradas;
        }
    }
    
    //exclui uimunizacao pelo ID
    //Entrada: Tipo int. ID da imunizacao a ser excluido
    //Retorno: Tipo int. Retorna a quatidade de linhas excluidas
    public static int excluirImunizacaoPorID(int idImunizacao) throws SQLException{
        //define o sql de exclusao
        String sql = "DELETE FROM imunizacoes WHERE id = ?";

        try (
            PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
        ) {
            //substitui a ? pelo id do usuario
            comando.setInt(1, idImunizacao);

            //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
            int qtdeLinhasExcluidas = comando.executeUpdate();

            return qtdeLinhasExcluidas;
        } catch (Exception e) {
            throw e;
        } 
    }

    public static int excluirTodasImunizacoesPaciente (int idPaciente) throws SQLException {
        //define o sql de exclusao
        String sql = "DELETE FROM imunizacoes WHERE id_paciente = ?";

        try (
            PreparedStatement comando = conexao.prepareStatement(sql) //cria a conexao com o sql a ser preparado
        ) {
            //substitui a ? pelo id do usuario
            comando.setInt(1, idPaciente);

            //executa a consulta e armazena o resultado da quantidade de linhas excluidas na variavel 
            int qtdeLinhasExcluidas = comando.executeUpdate();

            return qtdeLinhasExcluidas;
        } catch (Exception e) {
            throw e;
        } 
    }

    public static ArrayList<ImunizacaoDetalhada> consultarTodasImunizacao() throws SQLException{
        ArrayList<ImunizacaoDetalhada> lista = new ArrayList<>();

        // Defina a consulta SQL
        String sql =    "SELECT imunizacoes.id, paciente.nome AS nome_paciente, vacina.vacina AS nome_vacina, " +
                        "dose.dose, imunizacoes.data_aplicacao, imunizacoes.fabricante, imunizacoes.lote, imunizacoes.local_aplicacao, imunizacoes.profissional_aplicador " +
                        "FROM imunizacoes " +
                        "INNER JOIN paciente ON imunizacoes.id_paciente = paciente.id " +
                        "INNER JOIN dose ON imunizacoes.id_dose = dose.id " +
                        "INNER JOIN vacina ON dose.id_vacina = vacina.id";
    
        try (   PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()) {
    
            while (resultado.next()) {
                // Cria um novo objeto ImunizacaoDetalhada
                ImunizacaoDetalhada imunizacao = new ImunizacaoDetalhada(
                        resultado.getInt("id"),                   // idImunizacao
                        resultado.getString("nome_paciente"),     // nomePaciente
                        resultado.getString("nome_vacina"),       // nomeVacina
                        resultado.getString("dose"),              // dose
                        resultado.getDate("data_aplicacao").toLocalDate(), // dataAplicacao
                        resultado.getString("fabricante"),        // fabricante
                        resultado.getString("lote"),              // lote
                        resultado.getString("local_aplicacao"),    // localAplicacao
                        resultado.getString("profissional_aplicador") //profissionalAplicador
                );
    
                // Adiciona o objeto ImunizacaoDetalhada na lista
                lista.add(imunizacao);
            }
        } 
        // Retorna o ArrayList de objetos ImunizacaoDetalhada
        return lista;
    }

    //realiza a consulta de um usuario pelo ID
    //Entrada: Tipo int. ID do usuario a ser pesquisado
    //Retorno: Tipo Usuario. Retorna o objeto Usuario
    public static ImunizacaoDetalhada consultarPorID(int idImunizacao) throws SQLException{
        //inicia o objeto pessoa como null
        ImunizacaoDetalhada imunizacao = null;

        //define o sql da consulta
        String sql =    "SELECT imunizacoes.id, paciente.nome AS nome_paciente, vacina.vacina AS nome_vacina, " +
                        "dose.dose, imunizacoes.data_aplicacao, imunizacoes.fabricante, imunizacoes.lote, imunizacoes.local_aplicacao, imunizacoes.profissional_aplicador " +
                        "FROM imunizacoes " +
                        "INNER JOIN paciente ON imunizacoes.id_paciente = paciente.id " +
                        "INNER JOIN dose ON imunizacoes.id_dose = dose.id " +
                        "INNER JOIN vacina ON dose.id_vacina = vacina.id " +
                        "WHERE imunizacoes.id = ?";

        try (
            PreparedStatement comando = conexao.prepareStatement(sql) //cria o comando para receber sql dinamico
        ) {
            //substitui a ? pelo codigo do usuario
            comando.setInt(1, idImunizacao);

            //executa o comando sql
            ResultSet resultado = comando.executeQuery();

            //verifica se tem algum resultado retornado pelo banco
            if (resultado.next()) {
                //cria o objeto pessoa com os dados retornados do banco
                imunizacao = new ImunizacaoDetalhada(
                    resultado.getInt("id"),                   // idImunizacao
                    resultado.getString("nome_paciente"),     // nomePaciente
                    resultado.getString("nome_vacina"),       // nomeVacina
                    resultado.getString("dose"),              // dose
                    resultado.getDate("data_aplicacao").toLocalDate(), // dataAplicacao
                    resultado.getString("fabricante"),        // fabricante
                    resultado.getString("lote"),              // lote
                    resultado.getString("local_aplicacao"),    // localAplicacao
                    resultado.getString("profissional_aplicador") //profissionalAplicador
                );                 
            } 
                
            //retorna o objeto pessoa
            return imunizacao;
        }
    }

    public static ArrayList<ImunizacaoDetalhada> consultarTodasImunizacaoPorIDPaciente(int idPaciente) throws SQLException{
        ArrayList<ImunizacaoDetalhada> lista = new ArrayList<>();

        // Defina a consulta SQL
        String sql =    "SELECT imunizacoes.id, paciente.nome AS nome_paciente, vacina.vacina AS nome_vacina, " +
                        "dose.dose, imunizacoes.data_aplicacao, imunizacoes.fabricante, imunizacoes.lote, imunizacoes.local_aplicacao, imunizacoes.profissional_aplicador " +
                        "FROM imunizacoes " +
                        "INNER JOIN paciente ON imunizacoes.id_paciente = paciente.id " +
                        "INNER JOIN dose ON imunizacoes.id_dose = dose.id " +
                        "INNER JOIN vacina ON dose.id_vacina = vacina.id " +
                        "WHERE paciente.id = ?";
    
            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria o comando para receber sql dinamico
            ) {
                //substitui a ? pelo codigo do usuario
                comando.setInt(1, idPaciente);

                //executa o comando sql
                ResultSet resultado = comando.executeQuery();
    
                while (resultado.next()) {
                    // Cria um novo objeto ImunizacaoDetalhada
                    ImunizacaoDetalhada imunizacao = new ImunizacaoDetalhada(
                            resultado.getInt("id"),                   // idImunizacao
                            resultado.getString("nome_paciente"),     // nomePaciente
                            resultado.getString("nome_vacina"),       // nomeVacina
                            resultado.getString("dose"),              // dose
                            resultado.getDate("data_aplicacao").toLocalDate(), // dataAplicacao
                            resultado.getString("fabricante"),        // fabricante
                            resultado.getString("lote"),              // lote
                            resultado.getString("local_aplicacao"),    // localAplicacao
                            resultado.getString("profissional_aplicador") //profissionalAplicador
                );
    
                // Adiciona o objeto ImunizacaoDetalhada na lista
                lista.add(imunizacao);
            }
        } 
        // Retorna o ArrayList de objetos ImunizacaoDetalhada
        return lista;
    }

    public static ArrayList<ImunizacaoDetalhada> consultarTodasImunizacaoPorIDPacienteEntreDatas(int idPaciente, String dataInicio, String dataFim) throws SQLException{
        ArrayList<ImunizacaoDetalhada> lista = new ArrayList<>();

        String sql =    "SELECT imunizacoes.id, paciente.nome AS nome_paciente, vacina.vacina AS nome_vacina, " +
                        "dose.dose, imunizacoes.data_aplicacao, imunizacoes.fabricante, imunizacoes.lote, imunizacoes.local_aplicacao, imunizacoes.profissional_aplicador " +
                        "FROM imunizacoes " +
                        "INNER JOIN paciente ON imunizacoes.id_paciente = paciente.id " +
                        "INNER JOIN dose ON imunizacoes.id_dose = dose.id " +
                        "INNER JOIN vacina ON dose.id_vacina = vacina.id " +
                        "WHERE paciente.id = ? " +
                        "AND imunizacoes.data_aplicacao BETWEEN ? AND ?";
    
            try (
                PreparedStatement comando = conexao.prepareStatement(sql) //cria o comando para receber sql dinamico
            ) {
                //substitui a ? pelo codigo do usuario
                comando.setInt(1, idPaciente);
                comando.setString(2, dataInicio);
                comando.setString(3, dataFim);

                //executa o comando sql
                ResultSet resultado = comando.executeQuery();
    
                while (resultado.next()) {
                    // Cria um novo objeto ImunizacaoDetalhada
                    ImunizacaoDetalhada imunizacao = new ImunizacaoDetalhada(
                            resultado.getInt("id"),                   // idImunizacao
                            resultado.getString("nome_paciente"),     // nomePaciente
                            resultado.getString("nome_vacina"),       // nomeVacina
                            resultado.getString("dose"),              // dose
                            resultado.getDate("data_aplicacao").toLocalDate(), // dataAplicacao
                            resultado.getString("fabricante"),        // fabricante
                            resultado.getString("lote"),              // lote
                            resultado.getString("local_aplicacao"),    // localAplicacao
                            resultado.getString("profissional_aplicador") //profissionalAplicador
                );
    
                // Adiciona o objeto ImunizacaoDetalhada na lista
                lista.add(imunizacao);
            }
        } 
        // Retorna o ArrayList de objetos ImunizacaoDetalhada
        return lista;
    }
}
