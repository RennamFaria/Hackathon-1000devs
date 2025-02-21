package br.com.api.dao;

import br.com.api.dto.DTOUsuarioPedido;
import br.com.api.model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class DAOPaciente {
   

    // Variável de conexão com o banco
    public static Connection conexao = null;

    // 🔹 Método para buscar paciente por ID
    public static Paciente buscarPorId(int idPaciente) throws SQLException {
        String sql = "SELECT * FROM paciente WHERE id = ?";
    
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idPaciente);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                Paciente pc = new Paciente();
                pc.setId(resultado.getInt("id"));
                pc.setNome(resultado.getString("nome"));
                pc.setCpf(resultado.getString("cpf"));
                pc.setSexo(resultado.getString("sexo").charAt(0));
                pc.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
                return pc;
            }
        }
        return null; // Retorna null se não encontrar o paciente
    }

    // 🔹 Método para buscar todos os pacientes
    public static List<Paciente> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM paciente";
        List<Paciente> lista = new ArrayList<>();

        try (PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                Paciente p = new Paciente();  // 🔹 Corrigido nome da classe
                p.setId(resultado.getInt("id"));
                p.setNome(resultado.getString("nome"));
                p.setCpf(resultado.getString("cpf"));
                p.setSexo(resultado.getString("sexo").charAt(0));
                p.setDataNascimento(resultado.getDate("data_nascimento").toLocalDate());
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todos os pacientes: " + e.getMessage());
        }
        
        return lista;
    }
}
