package br.com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class DAOPaciente {
    public static Connection conexao = null;
    
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
}
