package DAO;

import Model.Espaco;
import Model.Reserva;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservaDAO {
    static final String URL = "jdbc:sqlite:database/coworking.db";

    public void criarTabelaReserva() {
        String sql = """
        CREATE TABLE reservas (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            espaco_id INTEGER NOT NULL,
            data_inicio TEXT NOT NULL,
            data_fim TEXT NOT NULL,
            valor_calculado REAL NOT NULL,
            status TEXT NOT NULL, -- ativa, concluida, cancelada
            FOREIGN KEY (espaco_id) REFERENCES espacos(id));""";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public void inserir(Reserva reserva) {
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
}
