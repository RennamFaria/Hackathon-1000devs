package br.com.api;
import br.com.api.config.Conexao;
import br.com.api.dao.*;
import spark.Spark;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Filter;

import java.sql.Connection;

import br.com.api.routes.Rotas;

public class Main {
    
    public static void main(String[] args) {
        try {
            // Obtém uma conexão válida com o banco de dados
            Connection conexao = Conexao.getConexao(); // Corrigido: Nome da variável com "c" minúsculo

            // Atribui a conexão criada no atributo da classe DAOPaciente

            DAOPaciente.conexao = conexao;

            // Define a porta do servidor
            Spark.port(8080);

            // Habilitar CORS
            Spark.options("/*", new Route() {
                @Override
                public Object handle(Request requisicaoHttp, Response respostaHttp) {
                    String accessControlRequestHeaders = requisicaoHttp.headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        respostaHttp.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                    }
                    String accessControlRequestMethod = requisicaoHttp.headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        respostaHttp.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                    }
                    return "OK";
                }
            });

            // Configura as permissões de CORS para aceitar requisições de qualquer origem
            Spark.before(new Filter() {
                @Override
                public void handle(Request requisicaoHttp, Response respostaHttp) {
                    respostaHttp.header("Access-Control-Allow-Origin", "*"); // Permite todas as origens
                    respostaHttp.header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
                    respostaHttp.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
                }
            });
            Rotas.processarRotas();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}