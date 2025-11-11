package DAO;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            System.out.println("✅ Conexão com o SQLite estabelecida com sucesso!");
        }
    }
}