package br.com.api.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.api.model.Paciente;
import br.com.api.model.Paciente.Sexo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;

public class DAOPaciente {
    public static Connection conexao = null; //cria a conexão;

    //realiza a insercao dos dados no banco de dados
    //Entrada: Tipo Paciente. Recebe o objeto Paciente. 
    //Retorno: Tipo int. Retorna o Id da chave primaria criado no banco de dados
    public static int adicionarPaciente(Paciente paciente) throws SQLException{
        
        String sql = "INSERT INTO paciente (nome, cpf, sexo, data_nascimento) VALUES (?, ?, ?,? ) "; // faz a consulta sql no banco

        try (PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //agora adiciona os valores nome, cpf, sexo e data de nascimento no lugar de ? na query 
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getSexo().name()); // Sexo (ENUM convertido para String)(name é um metódo do ENUM que não pode ser sobrescrito, ele é o que é)
            // Converte LocalDate para java.sql.Date
            LocalDate localDate = LocalDate.parse(paciente.getdata_nascimento());
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


    public static int excluirporIdPaciente(int id ) throws SQLException{
        //realiza a exclsão dos dados no banco de dados
        //Entrada: Tipo Paciente. Recebe o objeto Paciente. 
        //Retorno: Tipo int. Retorna o número de linhas afetadas

        // define a query para a exclusao de um paciente por ID. 
        String sql = "DELETE FROM paciente WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            //substitui a ? pelo id do paciente pra realizar a exclusao
            comando.setInt(1,id);
 
            //executa a consulta e armazena o resultado de linhas afetadas na váriavel, serve para verificação de exclusao. 

            int qtdeLinhasExcluidas = comando.executeUpdate();

            return qtdeLinhasExcluidas;  
        } catch (SQLException e) {
            // Log do erro (opcional, mas recomendado)
            System.out.println("Erro ao excluir paciente: " + e.getMessage());
            // Relança a exceção
            throw e;
        }
    }

    
    public static int atualizarPaciente (Paciente paciente) throws SQLException{ 
        //realiza a exclsão dos dados no banco de dados
        //Entrada: Tipo Paciente. Recebe o objeto Paciente. 
        //Retorno: Tipo int. Retorna o número de linhas afetadas
    
        //definição da query 
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, sexo = ?, data_nascimento = ? WHERE id = ?";

        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
        comando.setString(1, paciente.getNome());
        comando.setString(2, paciente.getCpf());
        comando.setString(3, paciente.getSexo().name()); // Sexo (ENUM convertido para String)(name é um metódo do ENUM que não pode ser sobrescrito, ele é o que é)
        // Converte LocalDate para java.sql.Date
        LocalDate localDate = LocalDate.parse(paciente.getdata_nascimento());
        Date sqlDate = Date.valueOf(localDate); // Converte LocalDate para java.sql.Date
        comando.setDate(4, sqlDate);
        comando.setInt(5, paciente.getId());
            //executa a atualizacao e armazena o retorno do banco com a quantidade de linhas atualizadas
            int qtdeLinhasAlteradas = comando.executeUpdate();

            //retorna a quantidade de linhas atualizadas
            return qtdeLinhasAlteradas;
        
        } catch (SQLException e) {
        // Log do erro (opcional, mas recomendado)
        System.out.println("Erro ao atualizar paciente: " + e.getMessage());
        // Relança a exceção
        throw e;
        }
    }

    
    public static LocalDate buscarDataNascimento(int idPaciente) throws SQLException {
        String sql = "SELECT data_nascimento FROM paciente WHERE id = ?";
        
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();
    
            if (resultado.next()) {
                return resultado.getDate("data_nascimento").toLocalDate();
            }
        }
        return null; // Retorna null se não encontrar o paciente
    }

    // 🔹 Método para buscar paciente por ID
    public static Paciente buscarPorId(int idPaciente) throws SQLException {
        Paciente paciente = null;
        
        String sql = "SELECT * FROM paciente WHERE id = ?";
    
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                
            // Converter sexo para ENUM
            String sexoString = resultado.getString("sexo");
            Sexo sexo = Sexo.valueOf(sexoString.toUpperCase());
                
                paciente = new Paciente(
                    resultado.getInt("id"), 
                    resultado.getString("nome"), 
                    resultado.getString("cpf"), 
                    sexo,
                    resultado.getDate("data_nascimento").toLocalDate());
                
                return paciente;
            }
        }
        return null; // Retorna null se não encontrar o paciente
    }

    // 🔹 Método para buscar todos os pacientes
    public static List<Paciente> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM paciente";
        List<Paciente> lista = new ArrayList<Paciente>();

        try (PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                
                // Converter sexo para ENUM
                String sexoString = resultado.getString("sexo");
                Sexo sexo = Sexo.valueOf(sexoString.toUpperCase());
                
                //cria um novo objeto Vacina_Dose
                Paciente novopaciente = new Paciente(
                    resultado.getInt("id"), 
                    resultado.getString("nome"), 
                    resultado.getString("cpf"), 
                    sexo,
                    resultado.getDate("data_nascimento").toLocalDate());
                
                //adiciona o objeto usuario no array list
                lista.add(novopaciente);
            }
        }
        
        return lista;
    }
}
    
