package DAO;

import Model.Espaco;
import Model.SalaDeReuniao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EspacoDAO {
    static final String URL = "jdbc:sqlite:database/coworking.db";

    public void criarTabelaEspaco() {
        String sql = """
        CREATE TABLE IF NOT EXISTS espacos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL,
            capacidade INTEGER NOT NULL,
            disponivel INTEGER NOT NULL,
            preco_por_hora REAL NOT NULL,
            tipo TEXT NOT NULL,
            taxa_projetor REAL,
            taxa_evento REAL);""";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Espaco espaco) {
        String sql = "INSERT INTO espacos(nome, capacidade, disponivel, preco_por_hora, tipo, taxa_projetor, taxa_evento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, espaco.getNome());
            stmt.setInt(2, espaco.getCapacidade());
            stmt.setInt(3, espaco.isDisponivel() ? 1 : 0);
            stmt.setDouble(4, espaco.getPrecoPorHora());
            stmt.setString(5, espaco.getTipo());
            stmt.setDouble(6, espaco.getTipo().equals("sala de reuniao")? 15 : 0);
            stmt.setDouble(7, espaco.getTipo().equals("auditorio")? 100 : 0);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
