package View;

import DAO.EspacoDAO;
import DAO.ReservaDAO;
import Exceptions.EspacoDuplicadoException;
import Model.Auditorio;
import Model.CabineIndividual;
import Model.Espaco;
import Model.Reserva;
import Model.SalaDeReuniao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {

        EspacoDAO.criarTabelaEspaco();
        ReservaDAO.criarTabelaReserva();

        System.out.println("Tabela criada/verificada.\n");

        //Espaco sala = new SalaDeReuniao( "Sala verde 2", 8, 50, "sala de reunião");
        //Espaco cabine = new CabineIndividual("Cabine 4", 1, 100, "cabine individual");
        //Espaco auditorio = new Auditorio("Auditório Secundário 2", 50, 120, "auditório");

        String inicioStr = "2025-01-10 11:00";
        String fimStr    = "2025-01-10 16:00";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
        LocalDateTime fim    = LocalDateTime.parse(fimStr, formatter);

        //Reserva reserva1 = new Reserva(auditorio, inicio, fim);

        System.out.println("Inserindo espaços...");
        /*
        try {
            //EspacoDAO.inserir(cabine);
            //EspacoDAO.inserir(auditorio);
        } catch (EspacoDuplicadoException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            ReservaDAO.inserir(reserva1);
        } catch (Exception e){
            e.printStackTrace();
        }
        */
        System.out.println("\nListando espaços cadastrados:\n");
        EspacoDAO.listarEspacos();

        ReservaDAO.atualizarReservasConcluidas();

        System.out.println("\nRemovendo o espaço");
        /*
        ReservaDAO.removerReserva(1);
        ReservaDAO.removerReserva(2);
        ReservaDAO.removerReserva(3);
        */
        ReservaDAO.removerReserva(6);

        System.out.println("\nListando após remoção:\n");
        EspacoDAO.listarEspacos();
        ReservaDAO.listarReservas();
    }
}
