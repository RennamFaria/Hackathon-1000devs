package br.com.api.routes;

import br.com.api.routes.Rotas;
import br.com.api.service.ServicoImunizacao;
import br.com.api.service.ServicoUsuario;
import br.com.api.service.ServicoVacina;
import spark.Spark; 

public class Rotas {
    
    public static void processarRotas(){
        //cadastra no spark quais rotas existem e quais metodos 
        //devem ser executados quando cada rota for requisitada
        Spark.post("/cadastrar", ServicoUsuario.cadastrarUsuario());
        Spark.get("/consultar/:id", ServicoUsuario.consultarUsuarioPorId());
        Spark.get("/consultar", ServicoUsuario.consultarTodosUsuarios());
        Spark.put("/alterar/:id", ServicoUsuario.alterarUsuario());
        Spark.delete("/excluir/:id", ServicoUsuario.excluirUsuario());
        
        //TO DO: Para criar novas rotas, basta adicionar novas linhas seguindo o padrao abaixo, 
        //onde XXXX e o metodo http (post, get, put ou delete), yyyyyy a url que define a rota
        //e ZZZZZ o metodo a ser executado quando a rota for acionada
        //Spark.XXXXX("YYYYYYY", ZZZZZ);

        Spark.get("/vacinas/consultar", ServicoVacina.consultarTodasVacinas());
        Spark.get("/vacinas/consultar/faixa_etaria/:faixa", ServicoVacina.consultarVacinaPorFaixaEtaria());
        Spark.get("/vacinas/consultar/idade_maior/:meses", ServicoVacina.consultarVacinaDisponivelAcimaIdade()); 
        Spark.get("/vacinas/consultar/nao_aplivacaveis/paciente/:id", ServicoVacina.consultarVacinaNaoAplicavel());

        Spark.post("/imunizacao/inserir", ServicoImunizacao.cadastrarImunizacao());
        Spark.put("/imunizacao/alterar/:id", ServicoImunizacao.alterarImunizacao());
        Spark.delete("/imunizacao/excluir/:id", ServicoImunizacao.excluirImunizacaoPorID());
        Spark.delete("/imunizacao/excluir/paciente/:id", ServicoImunizacao.excluirTodasImunizacaoPaciente());
        Spark.get("/imunizacao/consultar", ServicoImunizacao.consultarTodasImunizacao());
        Spark.get("/imunizacao/consultar/:id", ServicoImunizacao.consultarImunizacaoPorID());
        Spark.get("/imunizacao/consultar/paciente/:id", ServicoImunizacao.consultarTodasImunizacaoPorIDPaciente());
        Spark.get("/imunizacao/consultar/paciente/:id/aplicacao/:dt_ini/:dt_fim", ServicoImunizacao.consultarTodasImunizacaoPorIDPacienteEntreDatas());
        
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
