package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PagamentoDAO  extends Database{
    static final String URL = "jdbc:sqlite:database/coworking.db";

    public void criarTabelaPagamento(){
        String sql="""
        CREATE TABLE pagamentos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            reserva_id INTEGER NOT NULL,
            valor_pago REAL NOT NULL,
            data_pagamento TEXT NOT NULL,
            metodo TEXT NOT NULL, -- pix, cartao, dinheiro
            FOREIGN KEY (reserva_id) REFERENCES reservas(id)""";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
