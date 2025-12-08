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
    static final String URL = "jdbc:sqlite:database/coworkingdb.db";

    public static void criarTabelaEspaco() {
        String sql = """
        CREATE TABLE IF NOT EXISTS espacos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL UNIQUE,
            capacidade INTEGER NOT NULL,
            disponivel INTEGER NOT NULL,
            preco_por_hora REAL NOT NULL,
            tipo TEXT NOT NULL,
            uso_projetor INTEGER DEFAULT 0,
            taxa_projetor REAL,
            taxa_evento REAL);""";


        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserir(Espaco espaco) throws EspacoDuplicadoException {
        String sql = """
        INSERT INTO espacos
        (nome, capacidade, disponivel, preco_por_hora, tipo, taxa_projetor, taxa_evento)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, espaco.getNome());
            stmt.setInt(2, espaco.getCapacidade());
            stmt.setInt(3, espaco.isDisponivel() ? 1 : 0);
            stmt.setDouble(4, espaco.getPrecoPorHora());
            stmt.setString(5, espaco.getTipo());


            double taxaProjetor = 0;
            double taxaEvento = 0;

            if (espaco instanceof SalaDeReuniao) {
                taxaProjetor = ((SalaDeReuniao) espaco).getTaxaProjetor();
            }

            if (espaco instanceof Auditorio) {
                taxaEvento = ((Auditorio) espaco).getTaxaEvento();
            }

            stmt.setDouble(6, taxaProjetor);
            stmt.setDouble(7, taxaEvento);

            // -------------------------
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

    public static Espaco criarObjetoPeloDB(int id) throws SQLException {
        String sql = "SELECT * FROM espacos WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String nome = rs.getString("nome");
                    int capacidade = rs.getInt("capacidade");
                    double preco = rs.getDouble("preco_por_hora");
                    String tipo = rs.getString("tipo");

                    Espaco espaco = null;

                    switch (tipo) {

                        case "sala de reuniao" -> {
                            SalaDeReuniao sala = new SalaDeReuniao(
                                    nome,
                                    capacidade,
                                    preco,
                                    tipo
                            );
                            // Carrega taxa do banco
                            sala.setTaxaProjetor(rs.getDouble("taxa_projetor"));
                            espaco = sala;
                        }

                        case "auditorio" -> {
                            Auditorio aud = new Auditorio(
                                    nome,
                                    capacidade,
                                    preco,
                                    tipo
                            );
                            // Carrega taxa do banco
                            aud.setTaxaEvento(rs.getDouble("taxa_evento"));
                            espaco = aud;
                        }

                        case "cabine individual" -> {
                            espaco = new CabineIndividual(
                                    nome,
                                    capacidade,
                                    preco,
                                    tipo
                            );
                        }
                    }

                    // Configurar ID do objeto
                    if (espaco != null) {
                        espaco.setId(rs.getInt("id"));
                    }

                    return espaco;
                }
            }
        }
        return null;
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
    public static void atualizar(Espaco espaco) {
        String sql = "UPDATE espacos SET nome = ?, capacidade = ?, preco = ?, tipo = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, espaco.getNome());
            stmt.setInt(2, espaco.getCapacidade());
            stmt.setDouble(3, espaco.getPrecoPorHora());
            stmt.setString(4, espaco.getTipo());
            stmt.setInt(5, espaco.getId());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Espaço atualizado com sucesso.");
            } else {
                System.out.println("Nenhum espaço encontrado para atualizar.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Espaco> listarEspacos() throws SQLException {
        List<Espaco> lista = new ArrayList<>();
        String sql = "SELECT * FROM espacos";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int capacidade = rs.getInt("capacidade");
                double preco = rs.getDouble("preco");
                String tipo = rs.getString("tipo");

                Espaco espaco;

                switch (tipo.toLowerCase()) {
                    case "sala de reuniao":
                        espaco = new SalaDeReuniao(nome, capacidade, preco, tipo);
                        break;
                    case "cabine individual":
                        espaco = new CabineIndividual(nome, capacidade, preco, tipo);
                        break;
                    case "auditorio":
                        espaco = new Auditorio(nome, capacidade, preco, tipo);
                        break;
                    default:
                        continue;
                }

                espaco.setId(id);
                lista.add(espaco);
            }
        }
        return lista;
    }




}
