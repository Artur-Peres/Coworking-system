package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EspacoDAO extends Database {
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


}
