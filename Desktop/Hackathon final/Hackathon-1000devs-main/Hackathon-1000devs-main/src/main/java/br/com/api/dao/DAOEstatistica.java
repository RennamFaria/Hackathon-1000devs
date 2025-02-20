package br.com.api.dao;

import br.com.api.config.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOEstatistica {

    public static Connection connection = null;



    public int getQuantidadeVacinasAplicadas(int pacienteId) throws SQLException {
        String sql = "SELECT COUNT(*) AS qtde_vacinas_aplicadas FROM imunizacoes WHERE id_paciente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pacienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("qtde_vacinas_aplicadas") : 0;
            }
        }
    }

    public int getQuantidadeVacinasProximoMes(int pacienteId) throws SQLException {
        String sql = """
        SELECT COUNT(*) AS qtde_vacinas_proximo_mes 
        FROM dose d 
        JOIN vacina v ON d.id_vacina = v.id 
        WHERE d.idade_recomendada_aplicacao = TIMESTAMPDIFF(MONTH, 
            (SELECT data_nascimento FROM paciente WHERE id = ?), CURDATE()) + 1
    """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pacienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("qtde_vacinas_proximo_mes") : 0;
            }
        }
    }


    public int getQuantidadeVacinasAtrasadas(int pacienteId) throws SQLException {
        String sql = """
        SELECT COUNT(*) AS qtde_vacinas_atrasadas\s
        FROM dose d\s
        JOIN vacina v ON d.id_vacina = v.id\s
        WHERE d.idade_recomendada_aplicacao <= TIMESTAMPDIFF(MONTH,\s
            (SELECT data_nascimento FROM paciente WHERE id = ?), CURDATE())\s
        AND d.id NOT IN (SELECT id_dose FROM imunizacoes WHERE id_paciente = ?)
   \s""";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pacienteId);
            stmt.setInt(2, pacienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("qtde_vacinas_atrasadas") : 0;
            }
        }
    }

    public int getQuantidadeVacinasAcimaDeIdade(int idade) throws SQLException {
        String sql = "SELECT COUNT(*) AS qtde_vacinas FROM dose WHERE idade_recomendada_aplicacao > ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idade);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("qtde_vacinas") : 0;
            }
        }
    }

    public int getQuantidadeVacinasNaoAplicaveis(int pacienteId) throws SQLException {
        String sql = """
        SELECT COUNT(*) AS qtde_vacinas_nao_aplicaveis 
        FROM vacina v 
        JOIN dose d ON v.id = d.id_vacina 
        WHERE v.limite_aplicacao IS NOT NULL 
        AND v.limite_aplicacao < TIMESTAMPDIFF(MONTH, 
            (SELECT data_nascimento FROM paciente WHERE id = ?), CURDATE())
    """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pacienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt("qtde_vacinas_nao_aplicaveis") : 0;
            }
        }
    }


}
