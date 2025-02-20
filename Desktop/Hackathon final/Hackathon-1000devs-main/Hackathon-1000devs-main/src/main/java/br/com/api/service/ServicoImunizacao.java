package br.com.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.dao.DAOImunizacao;
import br.com.api.model.Imunizacao;
import br.com.api.model.joins.ImunizacaoDetalhada;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoImunizacao {
    public static Route cadastrarImunizacao() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                //extrai os parametros do boddy da requisicao http  
                int idPaciente = Integer.parseInt(request.queryParams("idPaciente"));
                int idDose = Integer.parseInt(request.queryParams("idDose"));
                LocalDate dataAplicacao = LocalDate.parse(request.queryParams("dataAplicacao"));
                String fabricante = request.queryParams("fabricante");
                String lote = request.queryParams("lote");
                String localAplicacao = request.queryParams("localAplicacao");
                String profissionalAplicador = request.queryParams("profissionalAplicador");

                //executar o metodo de adicionar o imunizacao no array list
                Imunizacao imunizacao = new Imunizacao(idPaciente, idDose, dataAplicacao, fabricante, lote, localAplicacao, profissionalAplicador);

                try {
                    //passa o objeto para o DAO realizar a insercao no banco de dados
                    //e recebe o id gerado no banco de dados
                    int idImunizacao = DAOImunizacao.cadastrarImunizacao(imunizacao);


                    //defini o status code do httpd
                    response.status(201); // 201 Created
                    return "{\"message\": \"Imunização com o ID " + idImunizacao + ", criada com sucesso.\"}" ;

                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request
                    return "{\"error\": \"ID do paciente ou ID da dose inválidos. Certifique-se de que são números inteiros.\"}";
                } catch (DateTimeParseException e) {
                    response.status(400); // 400 Bad Request
                    return "{\"error\": \"Data de aplicação inválida. Utilize o formato yyyy-MM-dd.\"}";
                } catch (Exception e) {
                    response.status(500); // 500 Internal Server Error
                    return "{\"error\": \"Erro interno do servidor: " + e.getMessage() + "\"}";
                }

            }
        };
    }

    // Método para lidar com a rota de atualizar imunizacao
    public static Route alterarImunizacao() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    //extrai os parametros do boddy da requisicao http  
                    int idImunizacao = Integer.parseInt(request.params(":id"));

                    int idPaciente = Integer.parseInt(request.queryParams("idPaciente"));
                    int idDose = Integer.parseInt(request.queryParams("idDose"));
                    LocalDate dataAplicacao = LocalDate.parse(request.queryParams("dataAplicacao"));
                    String fabricante = request.queryParams("fabricante");
                    String lote = request.queryParams("lote");
                    String localAplicacao = request.queryParams("localAplicacao");
                    String profissionalAplicador = request.queryParams("profissionalAplicador");
    
                    //cria o objeto usuario na memoria
                    Imunizacao imunizacao = new Imunizacao(idImunizacao, idPaciente, idDose, dataAplicacao, fabricante, lote, localAplicacao, profissionalAplicador);
    
                    //envia o objeto para ser inserido no banco de dados pelo DAO 
                    //e armazena a quantidade de linhas alteradas
                    int qtdeLinhasAlteradas = DAOImunizacao.atualizarImunizacao(imunizacao);
    
                    //se a quantidade de linhas alteradas for maior que 0 significa se existia a imunizacao no banco de dados
                    if (qtdeLinhasAlteradas > 0){
                        response.status(200); // 200 Ok
                        return "{\"message\": \"Imunizacao com id " + idImunizacao + " foi atualizado com sucesso.\"}";
                    //se nao for maior que 0 nao existia o usuario no banco de dados
                    } else {
                        response.status(404); // 404 Not Found
                        return "{\"message\": \"A imunização com id " + idImunizacao + " não foi encontrado.\"}";
                    }

                } catch (NumberFormatException e) {
                response.status(400); // 400 Bad Request
                return "{\"error\": \"Formato de ID fornecido está incorreto. Certifique-se de que é um número válido.\"}";
                } catch (DateTimeParseException e) {
                    response.status(400); // 400 Bad Request
                    return "{\"error\": \"Data de aplicação inválida. Utilize o formato yyyy-MM-dd.\"}";
                } catch (Exception e) {
                    e.printStackTrace();
                    response.status(500); // 500 Internal Server Error
                    return "{\"error\": \"Erro ao processar a requisição. Tente novamente mais tarde.\"}";
                }
            }
        };
    }

    // Método para lidar com a rota de excluir usuário
    public static Route excluirImunizacaoPorID() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {     
                    //extrai o parametro id da URL (header http), e converte para inteiro
                    int idImunizacao = Integer.parseInt(request.params(":id"));
                    
                    //envia o id a ser excluida para o DAO e recebe a quantidade de linhas excluidas
                    int linhasExcluidas = DAOImunizacao.excluirImunizacaoPorID(idImunizacao);

                    //se a quantidade de linhas for maior que 0 significa que o usuario existia no banco de dados
                    if (linhasExcluidas > 0) {
                        response.status(200); //exclusão com sucesso
                        return "{\"message\": \"Usuário com id " + idImunizacao + " foi excluído com sucesso.\"}" ;
                    //se nao forma maior que 0 o usuario nao existia no banco de dados
                    } else {
                        response.status(404); //id não encontrado
                        return "{\"message\": \"Usuário com id " + idImunizacao + " não foi encontrado no banco de dados.\"}" ;
                    }
                } catch (NumberFormatException e) { // Exceção ao tentar converter o ID da URL
                    response.status(400); // 400 Bad Request
                    return "{\"message\": \"ID fornecido está no formato incorreto. O ID deve ser um número válido.\"}";
                } catch (Exception e) { // Exceção genérica
                    response.status(500); // 500 Internal Server Error
                    return "{\"message\": \"Erro interno ao tentar excluir a imunização. Tente novamente mais tarde.\"}";
                }
                                    
            }
        };
    }

    public static Route excluirTodasImunizacaoPaciente() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {     
                    //extrai o parametro id da URL (header http), e converte para inteiro
                    int idPaciente = Integer.parseInt(request.params(":id"));
                    
                    //envia o id a ser excluida para o DAO e recebe a quantidade de linhas excluidas
                    int linhasExcluidas = DAOImunizacao.excluirTodasImunizacoesPaciente(idPaciente);

                    //se a quantidade de linhas for maior que 0 significa que o usuario existia no banco de dados
                    if (linhasExcluidas > 0) {
                        response.status(200); //exclusão com sucesso
                        return "{\"message\": \"Todas as imunizações do paciente de ID " + idPaciente + " foram excluídas com sucesso.\"}" ;
                    //se nao forma maior que 0 o usuario nao existia no banco de dados
                    } else {
                        response.status(404); //id não encontrado
                        return "{\"message\": \"Paciente com id " + idPaciente + " não foi encontrado no banco de dados.\"}" ;
                    }
                } catch (NumberFormatException e) { // Exceção ao tentar converter o ID da URL
                    response.status(400); // 400 Bad Request
                    return "{\"message\": \"ID fornecido está no formato incorreto. O ID deve ser um número válido.\"}";
                } catch (Exception e) { // Exceção genérica
                    response.status(500); // 500 Internal Server Error
                    return "{\"message\": \"Erro interno ao tentar excluir as imunizações. Tente novamente mais tarde.\"}";
                }
                                    
            }
        };
    }

    //método que busca todas as vacinas
    public static Route consultarTodasImunizacao() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();

                //busca todos as vacinas imunizacoes no array list
                //é necessário realizar um cast explicito para converter o retorno 
                //do servicoVacinas
                ArrayList<ImunizacaoDetalhada> imunizacoes = DAOImunizacao.consultarTodasImunizacao();

                //se o arraylist estiver vazio
                if (imunizacoes.isEmpty()) {
                    response.status(404); // 404 - nada encontrado
                    return "{\"message\": \"Nenhuma vacina encontrada no banco de dados.\"}";
                } else {
                    //se nao estiver vazio devolve o arraylist convertido para json
                    response.status(200); // 200 Ok
                    return converteJson.writeValueAsString(imunizacoes);
                }
            }
        };
    }

        // Método para lidar com a rota de buscar imunizacao por ID
        public static Route consultarImunizacaoPorID() {
            return new Route() {
                @Override
                public Object handle(Request request, Response response) throws Exception {
                    //classe para converter objeto para json
                    ObjectMapper converteJson = new ObjectMapper();
    
                    int idImunizacao;
    
                    try {
                        //extrai o parametro id da URL (header http), e converte para inteiro
                        idImunizacao = Integer.parseInt(request.params(":id"));
    
                        //busca o contato no array list pela id
                        ImunizacaoDetalhada imunizacaoDetalhada = DAOImunizacao.consultarPorID(idImunizacao);
    
                        if (imunizacaoDetalhada != null) {
                            //defini o http status code
                            response.status(200); // 200 OK
    
                            //retorna o objeto encontrado no formato json
                            return converteJson.writeValueAsString(imunizacaoDetalhada);
                        } else {
                            //defini o http status code
                            response.status(209); // 209 Consulta realizada com sucesso mas nao tem nenhum registro no banco
                            return "{\"message\": \"Nenhuma imunização encontrada com este ID.\"}" ;
                        }
                    } catch (NumberFormatException e) {
                        // Se ocorrer erro ao tentar converter o ID, retorna com status 400
                        response.status(400); // 400 Bad Request
                        return "{\"message\": \"ID fornecido está no formato incorreto.\"}";
                    } catch (Exception e) {
                        // Para qualquer outra exceção, retorna com status 500
                        response.status(500); // 500 Internal Server Error
                        return "{\"message\": \"Erro interno ao tentar consultar a imunização. Tente novamente mais tarde.\"}";
                    }
                }
            };
        }

        public static Route consultarTodasImunizacaoPorIDPaciente(){
            return new Route() {
                @Override
                public Object handle(Request request, Response response) throws Exception {
                    ObjectMapper converteJson = new ObjectMapper();

                    int idPaciente;
                    
                    try {
                        //pegando valor da url
                        idPaciente = Integer.parseInt(request.params(":id")); 
                        System.out.println(idPaciente);

                        //busca todos as vacinas cadastrados no array list
                        //é necessário realizar um cast explicito para converter o retorno 
                        //do servicoVacinas
                        ArrayList<ImunizacaoDetalhada> imunizacoes = DAOImunizacao.consultarTodasImunizacaoPorIDPaciente(idPaciente);

                        if (imunizacoes.isEmpty()) {
                            response.status(404); // 404
                            return "{\"message\": \"Nenhuma imunizacao encontrada no banco de dados.\"}";
                        } else {
                            //se nao estiver vazio devolve o arraylist convertido para json
                            response.status(200); // 200 Ok
                            return converteJson.writeValueAsString(imunizacoes);
                        }
                    } catch (IllegalArgumentException e) {
                        response.status(400);
                        return "{\"error\": \"Digitado um valor inválido para ID.\"}";
                    } catch (Exception e) {
                        // Para qualquer outro erro inesperado, retorna status 500
                        response.status(500); // 500 Internal Server Error
                        return "{\"error\": \"Erro interno ao tentar consultar as imunizações. Tente novamente mais tarde.\"}";
                    } 
                }
            };
    }
    
    public static Route consultarTodasImunizacaoPorIDPacienteEntreDatas(){
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                ObjectMapper converteJson = new ObjectMapper();

                int idPaciente;
                String dataInicio;
                String dataFim;
                
                try {
                    //pegando valor da url
                    idPaciente = Integer.parseInt(request.params(":id")); 
                    dataInicio = request.params(":dt_ini");
                    dataFim = request.params(":dt_fim");

                    // Defina o formato esperado
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    try {
                        // Tenta fazer o parsing para LocalDate, mas sem alterar o tipo da variável
                        LocalDate.parse(dataInicio, formatter);
                        LocalDate.parse(dataFim, formatter);
                    } catch (DateTimeParseException e) {
                        response.status(400);
                        return "{\"error\": \"Formato de data inválido. Use o formato yyyy-MM-dd.\"}";
                    }
                    
                    //busca todos as vacinas cadastrados no array list
                    //é necessário realizar um cast explicito para converter o retorno 
                    //do servicoVacinas
                    ArrayList<ImunizacaoDetalhada> imunizacoes = DAOImunizacao.consultarTodasImunizacaoPorIDPacienteEntreDatas(idPaciente, dataInicio, dataFim);

                    if (imunizacoes.isEmpty()) {
                        response.status(404); // 404
                        return "{\"message\": \"Nenhuma imunizacao encontrada no banco de dados.\"}";
                    } else {
                        //se nao estiver vazio devolve o arraylist convertido para json
                        response.status(200); // 200 Ok
                        return converteJson.writeValueAsString(imunizacoes);
                    }
                } catch (IllegalArgumentException e) {
                    response.status(400);
                    return "{\"error\": \"Digitado um valor inválido para ID.\"}";
                } catch (Exception e) {
                    // Para qualquer outro erro inesperado, retorna 500
                    response.status(500); // 500 Internal Server Error
                    return "{\"error\": \"Erro interno ao processar a requisição. Tente novamente mais tarde.\"}";
                }
            }
        };
    }
}    