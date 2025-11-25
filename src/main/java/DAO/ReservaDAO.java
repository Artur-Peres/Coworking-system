package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservaDAO extends Database{
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
}
