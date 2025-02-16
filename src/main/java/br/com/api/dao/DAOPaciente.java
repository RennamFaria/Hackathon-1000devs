package br.com.api.dao;
import br.com.api.model.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.sql.Date;

public class DAOPaciente {
    public static Connection conexao = null; //cria a conexão;
    //realiza a insercao dos dados no banco de dados
    //Entrada: Tipo Paciente. Recebe o objeto Paciente. 
    //Retorno: Tipo int. Retorna o Id da chave primaria criado no banco de dados
    public static int adicionarPaciente(Paciente paciente) throws SQLException{

        
        String sql = "INSERT * INTO paciente (nome, cpf, sexo, dataNascimento) VALUES (?, ?, ?,? ) "; // faz a consulta sql no banco

       try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        //agora adiciona os valores nome, cpf, sexo e data de nascimento no lugar de ? na query 
        comando.setString(1, paciente.getNome());
        comando.setString(2, paciente.getCpf());
        comando.setString(3, paciente.getSexo().name()); // Sexo (ENUM convertido para String)(name é um metódo do ENUM que não pode ser sobrescrito, ele é o que é)
        // Converte LocalDate para java.sql.Date
        LocalDate localDate = paciente.getDataNascimento();
        Date sqlDate = Date.valueOf(localDate); // Converte LocalDate para java.sql.Date
        comando.setDate(4, sqlDate);

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
        throw new SQLException("Erro ao inserir usuário: nenhum ID gerado.");
    }

    
}
