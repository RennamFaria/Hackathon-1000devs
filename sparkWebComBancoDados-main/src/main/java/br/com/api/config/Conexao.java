package br.com.api.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {


        //Conexao banco de dados 
     private static final String url = "jdbc:mysql://127.0.0.1:3306/vacinacao"; 
     private static final String usuario = "root"; // Altere para seu usuário
     private static final String senha = "Mari2003#";  // Altere para sua senha

    public static Connection getConexao() throws Exception {
        return DriverManager.getConnection(url, usuario, senha);
    }
 }
