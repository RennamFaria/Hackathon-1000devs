package br.com.api.routes;

import br.com.api.service.ServicoEstatisticas;
import br.com.api.service.ServicoImunizacao;
import br.com.api.service.ServicoVacina;
import br.com.api.service.ServicoPaciente;

import spark.Spark; 

public class Rotas {
    
    public static void processarRotas(){
        //cadastra no spark quais rotas existem e quais metodos 
        //devem ser executados quando cada rota for requisitada

        
        //TO DO: Para criar novas rotas, basta adicionar novas linhas seguindo o padrao abaixo, 
        //onde XXXX e o metodo http (post, get, put ou delete), yyyyyy a url que define a rota
        //e ZZZZZ o metodo a ser executado quando a rota for acionada
        //Spark.XXXXX("YYYYYYY", ZZZZZ);

        //PACIENTE
        Spark.post("/paciente/inserir", ServicoPaciente.cadastrarPaciente());
        Spark.delete("/paciente/excluir/:id", ServicoPaciente.excluirPaciente());
        Spark.put("/paciente/alterar/:id", ServicoPaciente.atualizarPaciente());
        Spark.get("/paciente/consultar", ServicoPaciente.consultarTodosPacientes());
        Spark.get("/paciente/consultar/:id", ServicoPaciente.consultarPacientePorId());

        //VACINA
        Spark.get("/vacinas/consultar", ServicoVacina.consultarTodasVacinas());
        Spark.get("/vacinas/consultar/faixa_etaria/:faixa", ServicoVacina.consultarVacinaPorFaixaEtaria());
        Spark.get("/vacinas/consultar/idade_maior/:meses", ServicoVacina.consultarVacinaDisponivelAcimaIdade()); 
        Spark.get("/vacinas/consultar/nao_aplivacaveis/paciente/:id", ServicoVacina.consultarVacinaNaoAplicavel());

        //IMUNIZACAO
        Spark.post("/imunizacao/inserir", ServicoImunizacao.cadastrarImunizacao());
        Spark.put("/imunizacao/alterar/:id", ServicoImunizacao.alterarImunizacao());
        Spark.delete("/imunizacao/excluir/:id", ServicoImunizacao.excluirImunizacaoPorID());
        Spark.delete("/imunizacao/excluir/paciente/:id", ServicoImunizacao.excluirTodasImunizacaoPaciente());
        Spark.get("/imunizacao/consultar", ServicoImunizacao.consultarTodasImunizacao());
        Spark.get("/imunizacao/consultar/:id", ServicoImunizacao.consultarImunizacaoPorID());
        Spark.get("/imunizacao/consultar/paciente/:id", ServicoImunizacao.consultarTodasImunizacaoPorIDPaciente());
        Spark.get("/imunizacao/consultar/paciente/:id/aplicacao/:dt_ini/:dt_fim", ServicoImunizacao.consultarTodasImunizacaoPorIDPacienteEntreDatas());


        //ESTATISTICA
        Spark.get("/estatisticas/imunizacoes/paciente/:id", ServicoEstatisticas.getQuantidadeVacinasAplicadas());
        Spark.get("/estatisticas/proximas_imunizacoes/paciente/:id", ServicoEstatisticas.getQuantidadeVacinasProximoMes());
        Spark.get("/estatisticas/imunizacoes_atrasadas/paciente/:id", ServicoEstatisticas.getQuantidadeVacinasAtrasadas());
        Spark.get("/estatisticas/imunizacoes/idade_maior/:idade", ServicoEstatisticas.getQuantidadeVacinasAcimaDeIdade());
        Spark.get("/estatisticas/vacinas/nao_aplivacaveis/paciente/:id", ServicoEstatisticas.getQuantidadeVacinasNaoAplicaveis());


        //DOSE
    }

    
    //Para criar novos metodos basta utilizar o esqueleto abaixo
    //Metodo Esqueleto
    //XXXX: Nome do metodo que o usuario quer criar
    //YYYY: Parametros de entrada para o metodo se existirem
    //ZZZZ: Implementação do método
    //QQQQ: Status code do HTTP
    //SSSS: Informação que será retornado
    //
    // public static Route XXXX(YYYY) {
    //     return new Route() {
    //         @Override
    //         public Object handle(Request request, Response response) throws Exception {
    //
    //             ZZZZ
    //
    //             response.status(QQQQ); 
    //             return SSSS;
    //         }
    //     };
    // }  
}