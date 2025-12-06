package DAO;

import Exceptions.EspacoDuplicadoException;
import Model.Auditorio;
import Model.CabineIndividual;
import Model.Espaco;
import Model.SalaDeReuniao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspacoDAO {
    static final String URL = "jdbc:sqlite:database/coworking.db";

    public static void criarTabelaEspaco() {
        String sql = """
        CREATE TABLE IF NOT EXISTS espacos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL UNIQUE,
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

    public static void inserir(Espaco espaco) throws EspacoDuplicadoException{
        String sql = "INSERT INTO espacos(nome, capacidade, disponivel, preco_por_hora, tipo, taxa_projetor, taxa_evento) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, espaco.getNome());
            stmt.setInt(2, espaco.getCapacidade());
            stmt.setInt(3, espaco.isDisponivel() ? 1 : 0);
            stmt.setDouble(4, espaco.getPrecoPorHora());
            stmt.setString(5, espaco.getTipo());
            stmt.setDouble(6, espaco.getTipo().equals("sala de reuniao")? 15 : 0);
            stmt.setDouble(7, espaco.getTipo().equals("auditorio")? 100 : 0);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                espaco.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                throw new EspacoDuplicadoException(espaco.getNome());
            }
            e.printStackTrace();
        }
    }

    public static void listarEspacos() {
        String sql = "SELECT * FROM espacos";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Capacidade: " + rs.getInt("capacidade"));
                System.out.printf("Preço por hora: " + rs.getDouble("preco_por_hora"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removerEspaco(int id) {
        String sql = "DELETE FROM espacos WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Espaço removido com sucesso.");
            } else {
                System.out.println("Nenhum espaço encontrado com esse ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
