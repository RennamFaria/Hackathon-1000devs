package br.com.api.service;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.dao.DAOPaciente;
import br.com.api.dao.Paciente;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoPaciente {

    // 🔹 Método para buscar paciente por ID
    public static Route consultarPacientePorId() {
        return (Request request, Response response) -> {
            ObjectMapper converteJson = new ObjectMapper();
            int id;

            try {
                // Extrai o parâmetro ID da URL e converte para inteiro
                id = Integer.parseInt(request.params(":id"));


                // Busca o paciente no banco de dados
                Paciente paciente = DAOPaciente.buscarPorId(id);

                if (paciente != null) {
                    response.status(200); // 200 OK
                    return converteJson.writeValueAsString(paciente);
                } else {
                    response.status(209); // 209 - Nenhum registro encontrado
                    return "{\"message\": \"Nenhum paciente encontrado com este ID.\"}";
                }
            } catch (NumberFormatException e) {
                response.status(400); // 400 - ID inválido
                return "{\"message\": \"ID fornecido está no formato incorreto.\"}";
            }
        };
    }

    // 🔹 Método para buscar todos os pacientes
    public static Route consultarTodosPacientes() {
        return (Request request, Response response) -> {
            ObjectMapper converteJson = new ObjectMapper();

            // Busca todos os pacientes cadastrados no banco
            List<Paciente> pacientes = DAOPaciente.buscarTodos();

            if (pacientes.isEmpty()) {
                response.status(209); // 209 - Nenhum paciente encontrado
                return "{\"message\": \"Nenhum Paciente encontrado no banco de dados.\"}";
            } else {
                response.status(200); // 200 - OK
                return converteJson.writeValueAsString(pacientes);
            }
        };
    }
}