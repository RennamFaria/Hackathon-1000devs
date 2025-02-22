package br.com.api.service;

import br.com.api.dao.DAOEstatistica;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoEstatisticas {

    private static DAOEstatistica DAO = new DAOEstatistica();

    public static Route getQuantidadeVacinasAplicadas() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    int pacienteId = Integer.parseInt(request.params(":id"));
                    int qtdeVacinas = DAO.getQuantidadeVacinasAplicadas(pacienteId);

                    response.status(200); // 200 OK
                    return qtdeVacinas;
                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request
                    return "{\"message\": \"ID do paciente inválido.\"}";
                } catch (Exception e) {
                    response.status(500); // 500 Internal Server Error
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route getQuantidadeVacinasProximoMes() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    int pacienteId = Integer.parseInt(request.params(":id"));

                    int qtdeVacinas = DAO.getQuantidadeVacinasProximoMes(pacienteId);

                    response.status(200); // 200 OK
                    return "{\"quantidade_vacinas_proximo_mes\": " + qtdeVacinas + "}";
                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request
                    return "{\"message\": \"ID do paciente inválido.\"}";
                } catch (Exception e) {
                    response.status(500); // 500 Internal Server Error
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route getQuantidadeVacinasAtrasadas() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    int pacienteId = Integer.parseInt(request.params(":id"));
                    int qtdeVacinas = DAO.getQuantidadeVacinasAtrasadas(pacienteId);

                    response.status(200); // 200 OK
                    return "{\"quantidade_vacinas_atrasadas\": " + qtdeVacinas + "}";
                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request

                    return "{\"message\": \"ID do paciente inválido.\"}";
                } catch (Exception e) {
                    response.status(500); // 500 Internal Server Error

                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route getQuantidadeVacinasAcimaDeIdade() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    int idade = Integer.parseInt(request.params(":idade"));

                    int qtdeVacinas = DAO.getQuantidadeVacinasAcimaDeIdade(idade);

                    response.status(200); // 200 OK
                    return "{\"quantidade_vacinas_acima_idade\": " + qtdeVacinas + "}";
                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request
                    return "{\"message\": \"Idade inválida.\"}";
                } catch (Exception e) {
                    response.status(500); // 500 Internal Server Error
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }

    public static Route getQuantidadeVacinasNaoAplicaveis() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                try {
                    int pacienteId = Integer.parseInt(request.params(":id"));

                    int qtdeVacinas = DAO.getQuantidadeVacinasNaoAplicaveis(pacienteId);

                    response.status(200); // 200 OK
                    return "{\"quantidade_vacinas_nao_aplicaveis\": " + qtdeVacinas + "}";
                } catch (NumberFormatException e) {
                    response.status(400); // 400 Bad Request
                    return "{\"message\": \"ID do paciente inválido.\"}";
                } catch (Exception e) {
                    response.status(500); // 500 Internal Server Error
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
            }
        };
    }





}
