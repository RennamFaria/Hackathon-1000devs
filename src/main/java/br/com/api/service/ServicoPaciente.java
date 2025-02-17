package br.com.api.service;
import java.time.LocalDate;
import br.com.api.dao.DAOPaciente;
import br.com.api.model.Paciente.Sexo;
import br.com.api.model.Paciente;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServicoPaciente {
     // Método para lidar com a rota de adicionar paciente
    public static Route cadastrarPaciente() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception 
            {
                //extrai os parametros do boddy da requisicao http  
                //o metódo queryParams recebe apenas string, então o que não for String tem que ser convertido 
                String nome = request.queryParams("nome");
                String cpf = request.queryParams("cpf");
                String sexoStr = request.queryParams("sexo"); // Extrai o valor como String
                Sexo sexo = Sexo.valueOf(sexoStr); // Converte a String para o enum
                String data_nascimentoStr = request.queryParams("data_nascimento"); // Extrai o valor como String
                LocalDate data_nascimento = LocalDate.parse(data_nascimentoStr); // Converte a String para LocalDate

                //executa o metodo de adicionar o contato no array list
                Paciente paciente = new Paciente(nome, cpf, sexo, data_nascimento);

                try {
                    //passa o objeto para o DAO realizar a insercao no banco de dados
                    //e recebe o id gerado no banco de dados
                    int idPaciente = DAOPaciente.adicionarPaciente(paciente);
                    
                    //defini o status code do httpd
                    response.status(201); // 201 Created
                    
                    //possiveis opcoes Classe Anonima, HashMap, Classe interna, 
                    // Object mensagem = new Object() {
                    //     public String message = "Usuário criado com o ID " + idUsuario + " com sucesso." ;
                    // };
                    
                    // //retorna um array list vazio no formato json
                    // return converteJson.writeValueAsString(mensagem);
                    
                    //retorna o id criado e retorna via http response
                    return "{\"message\": \"Usuário criado com o ID " + idPaciente + " com sucesso.\"}" ;
                        
                } catch (Exception e) {
                    response.status(500); // 500 Erro no servidor
                    //Retorna a excecao gerado pelo DAOUsuario caso exista
                    return "{\"message\": \"" + e.getMessage() + "\"}" ;
                }
            }
        };
    }
    public static Route excluirPaciente(){
        return new Route(){
            @Override
            public Object handle(Request request, Response response) throws Exception{
                try {
                    //aqui eu preciso extrair do http o id e converter ele pra inteiro pra poder trabalhar a exclusão
                    int id = Integer.parseInt(request.params("id"));
                    //envia o id para ser excluido pro DAOPaciente 
                    int linhasExcluidas = DAOPaciente.excluirporIdPaciente(id);

                    //se a quantidade de linhas for maior que 0 significa que o usuario existia no banco de dados. Rogério optou por verificar o id da exclusao no serviço, não no DAO.
                    if (linhasExcluidas > 0) {
                        response.status(200); //exclusão com sucesso
                        return "{\"message\": \"Usuário com id " + id + " foi excluído com sucesso.\"}" ;
                    //se nao forma maior que 0 o usuario nao existia no banco de dados
                    } else {
                        response.status(209); //id não encontrado
                        return "{\"message\": \"Usuário com id " + id + " não foi encontrado no banco de dados.\"}" ;
                    }

                } catch (NumberFormatException e) { //alguma excecao na conversado do id fornecido na URL
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                }
            }
        };
    }
    public static Route atualizarPaciente(){
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception{
                try {
                 //extrai os parametros do boddy da requisicao http  
                //o metódo queryParams recebe apenas string, então o que não for String tem que ser convertido 
                String nome = request.queryParams("nome");
                String cpf = request.queryParams("cpf");
                String sexoStr = request.queryParams("sexo"); // Extrai o valor como String
                Sexo sexo = Sexo.valueOf(sexoStr); // Converte a String para o enum
                String data_nascimentoStr = request.queryParams("data_nascimento"); // Extrai o valor como String
                LocalDate data_nascimento = LocalDate.parse(data_nascimentoStr); // Converte a String para LocalDate

                //executa o metodo de adicionar o contato no array list
                Paciente paciente = new Paciente(nome, cpf, sexo, data_nascimento);

                int qtdeLinhasAlteradas = DAOPaciente.atualizarPaciente(paciente);

                    //se a quantidade de linhas for maior que 0 significa que o usuario existia no banco de dados. Rogério optou por verificar o id da exclusao no serviço, não no DAO.
                    if (qtdeLinhasAlteradas > 0) {
                        response.status(200); //exclusão com sucesso
                        return "{\"message\": \"Paciente " + paciente + " foi alterado(a) com sucesso.\"}" ;
                    //se nao forma maior que 0 o usuario nao existia no banco de dados
                    } else {
                        response.status(209); //id não encontrado
                        return "{\"message\": \"Paciente " + paciente + " não foi encontrado no banco de dados.\"}" ;
                    }
                } catch (NumberFormatException e) { //algum erro de conversao do id passado na url
                    response.status(400);
                    return "{\"message\": \"ID fornecido está no formato incorreto.\"}" ;
                } catch (Exception e) {
                    response.status(500);
                    return "{\"message\": \"Erro ao processar a requisição.\"}";
                }
                    
                }
            };
        };
    }


    

