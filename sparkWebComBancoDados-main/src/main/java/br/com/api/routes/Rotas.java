package br.com.api.routes;
import  br.com.api.service.ServicoPaciente;
import spark.Spark;


public class Rotas {
    public static void  processarRotas(){
        Spark.get("/paciente/consultar", ServicoPaciente.consultarTodosPacientes());
        
        Spark.get("/paciente/consultar/:id",ServicoPaciente.consultarPacientePorId() );
    }
    
}
