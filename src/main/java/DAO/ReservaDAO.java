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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReservaDAO {
    static final String URL = "jdbc:sqlite:database/coworkingdb.db";

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
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            System.out.println("Status enviado = " + reserva.getStatus());

            stmt.setInt(1, reserva.getEspaco().getId());
            stmt.setString(2, reserva.getDataHoraInicio().toString());
            stmt.setString(3, reserva.getDataHoraFim().toString());
            stmt.setDouble(4, reserva.getValorCalculado());
            stmt.setString(5, reserva.getStatus());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reserva.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Reserva criarObjetoPeloDB(int id) throws SQLException {
        String sql = "SELECT * FROM reservas WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    int espacoId = rs.getInt("espaco_id");
                    Espaco espaco = EspacoDAO.criarObjetoPeloDB(espacoId);
                    if (espaco == null) {
                        throw new SQLException("Espaço com id " + espacoId + " não encontrado.");
                    }

                    LocalDateTime inicio = LocalDateTime.parse(rs.getString("data_inicio"));
                    LocalDateTime fim = LocalDateTime.parse(rs.getString("data_fim"));

                    String status = rs.getString("status");

                    Reserva reserva = new Reserva(
                            espaco,
                            inicio,
                            fim
                    );
                    reserva.setId(rs.getInt("id"));
                    reserva.setStatus(status);
                    reserva.setValorCalculado(rs.getDouble("valor_calculado"));
                    return reserva;
                }
            }
        }
        return null;
    }


    public static void atualizarReservasConcluidas() {
        String select = "SELECT id, data_fim FROM reservas WHERE status = 'ativo'";
        String update = "UPDATE reservas SET status = 'concluida' WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(select)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String fimStr = rs.getString("data_fim");

                LocalDateTime fim = LocalDateTime.parse(fimStr);

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
    public static boolean horarioConflitante(int espacoId, LocalDateTime inicio, LocalDateTime fim) {
        String sql = """
        SELECT COUNT(*) FROM reservas
        WHERE espaco_id = ?
          AND status NOT IN ('concluida', 'cancelado') 
          AND (
                (? < data_fim) AND 
                (? > data_inicio)
              )
        """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, espacoId);
            stmt.setString(2, inicio.toString());
            stmt.setString(3, fim.toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Reserva> listarReservasPorPeriodo(LocalDateTime inicio, LocalDateTime fim){
        List<Reserva> listaRelatPeriodo = new ArrayList<>();
        String sql = """
                    SELECT id, espaco_id, data_inicio, data_fim, status, valor_calculado
                    FROM reservas 
                    WHERE data_inicio >= ? 
                      AND data_fim <= ?
                      AND status = 'completo'
                    ORDER BY data_inicio;
                    """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, inicio.toString());
            ps.setString(2, fim.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva(
                        EspacoDAO.criarObjetoPeloDB(rs.getInt("espaco_id")),
                        LocalDateTime.parse(rs.getString("data_inicio")),
                        LocalDateTime.parse(rs.getString("data_fim"))
                );
                reserva.setId(rs.getInt("id"));
                reserva.setStatus(rs.getString("status"));
                reserva.setValorCalculado(rs.getDouble("valor_calculado"));
                listaRelatPeriodo.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaRelatPeriodo;
    }

    public static Map<String, Double> faturamentoPorTipo() {
        Map<String, Double> resultado = new LinkedHashMap<>(); // preserva ordem de inserção

        String sql = """
                    SELECT e.tipo,
                           COALESCE(SUM(r.valor_calculado), 0) AS total
                    FROM reservas r
                    JOIN espacos e ON r.espaco_id = e.id
                    WHERE r.status = 'completo'
                    GROUP BY e.tipo
                    ORDER BY total DESC
                    """;


        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                double total = rs.getDouble("total");
                resultado.put(tipo, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public static Map<String, Double> utilizacaoPorEspaco() {
        Map<String, Double> resultado = new LinkedHashMap<>();

        String sql = """
                    SELECT 
                        e.nome AS espaco,
                        SUM((julianday(r.data_fim) - julianday(r.data_inicio)) * 24) AS horas_usadas
                    FROM reservas r
                    JOIN espacos e ON r.espaco_id = e.id
                    WHERE r.status = 'completo'
                    GROUP BY e.nome
                    ORDER BY horas_usadas DESC
                    """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String espaco = rs.getString("espaco");
                double horas = rs.getDouble("horas_usadas");
                resultado.put(espaco, horas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public static List<String> rankingEspacosMaisUtilizados() {
        List<String> ranking = new ArrayList<>();

        String sql = """
                    SELECT e.id,
                           e.nome,
                           e.tipo,
                           COUNT(r.id) AS total_reservas
                    FROM espacos e
                    LEFT JOIN reservas r 
                        ON e.id = r.espaco_id 
                       AND r.status = 'completo'
                    GROUP BY e.id
                    ORDER BY total_reservas DESC
                    """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                int total = rs.getInt("total_reservas");

                ranking.add("Espaço " + nome + " (ID " + id + ", " + tipo + ") — " +
                        total + " reservas");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ranking;
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



    }


