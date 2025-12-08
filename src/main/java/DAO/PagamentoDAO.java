package DAO;

import Model.Espaco;
import Model.Pagamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PagamentoDAO {
    static final String URL = "jdbc:sqlite:database/coworkingdb.db";

    public static void criarTabelaPagamento(){
        System.out.println("Criando pag dao");
        String sql="""
        CREATE TABLE IF NOT EXISTS pagamentos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            reserva_id INTEGER NOT NULL,
            valor_pago REAL NOT NULL,
            data_pagamento TEXT NOT NULL,
            metodo TEXT NOT NULL, -- pix, cartao, dinheiro
            FOREIGN KEY (reserva_id) REFERENCES reservas(id))""";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserir(Pagamento pagamento) {
        String sql = "INSERT INTO pagamentos(reserva_id, valor_pago, data_pagamento, metodo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pagamento.getReserva().getId());
            stmt.setDouble(2, pagamento.getValorPago());
            stmt.setString(3, pagamento.getData().toString());
            stmt.setString(4, pagamento.getMetodo().getDescricao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
