package DAO;

import Exceptions.EspacoDuplicadoException;
import Model.Espaco;
import Model.Reserva;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class ReservaDAO {
    static final String URL = "jdbc:sqlite:database/coworking.db";

    public static void criarTabelaReserva() {
        String sql = """
        CREATE TABLE IF NOT EXISTS reservas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            espaco_id INTEGER NOT NULL,
            data_inicio TEXT NOT NULL,
            data_fim TEXT NOT NULL,
            valor_calculado REAL NOT NULL,
            status TEXT NOT NULL,
            FOREIGN KEY (espaco_id) REFERENCES espacos(id));""";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserir(Reserva reserva) {
        String sql = "INSERT INTO reservas(espaco_id, data_inicio, data_fim, valor_calculado, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getEspaco().getId());
            stmt.setString(2, reserva.getDataHoraInicio().toString());
            stmt.setString(3, reserva.getDataHoraFim().toString());
            stmt.setDouble(4, reserva.getValorCalculado());
            stmt.setString(5, reserva.isAtiva()? "ativa": "concluida");

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelarReserva(int id){
        String sql = "UPDATE reservas SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "cancelado");
            stmt.setInt(2, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Reserva cancelada.");
            } else {
                System.out.println("Nenhuma reserva encontrada com esse ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarReservas() {
        String sql = "SELECT * FROM reservas";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("ID do espaço: " + rs.getInt("espaco_id"));
                System.out.println("Data de início: " + rs.getString("data_inicio"));
                System.out.println("Data fim: " + rs.getString("data_fim"));
                System.out.println("Valor: " + rs.getDouble("valor_calculado"));
                System.out.println("Status: " + rs.getString("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarReservasConcluidas() {
        String select = "SELECT id, data_fim FROM reservas WHERE status = 'ativa'";
        String update = "UPDATE reservas SET status = 'concluida' WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(select)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime fim = LocalDateTime.parse(rs.getString("data_fim"));

                if (fim.isBefore(LocalDateTime.now())) {
                    try (PreparedStatement up = conn.prepareStatement(update)) {
                        up.setInt(1, id);
                        up.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removerReserva(int id) {
        String sql = "DELETE FROM reservas WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Reserva removido com sucesso.");
            } else {
                System.out.println("Nenhuma reserva encontrado com esse ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
