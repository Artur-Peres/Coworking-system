package View;

import DAO.EspacoDAO;
import Exceptions.EspacoDuplicadoException;
import Model.Auditorio;
import Model.CabineIndividual;
import Model.Espaco;
import Model.SalaDeReuniao;

public class App {
    public static void main(String[] args) {

        EspacoDAO.criarTabelaEspaco();
        System.out.println("Tabela criada/verificada.\n");

        Espaco sala = new SalaDeReuniao( "Sala verde", 8, 50, "sala de reunião");
        Espaco cabine = new CabineIndividual("Cabine 02", 1, 100, "cabine individual");
        Espaco auditorio = new Auditorio("Auditório Secundário", 50, 120, "auditório");

        System.out.println("Inserindo espaços...");
        try {
            EspacoDAO.inserir(sala);
            EspacoDAO.inserir(cabine);
            EspacoDAO.inserir(auditorio);
        } catch (EspacoDuplicadoException e) {
            System.out.println(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("\nListando espaços cadastrados:\n");
        EspacoDAO.listarEspacos();

        System.out.println("\nRemovendo o espaço de ID 1...");
        EspacoDAO dao = new EspacoDAO();
        dao.removerEspaco(1);

        System.out.println("\nListando após remoção:\n");
        EspacoDAO.listarEspacos();
    }
}
