package View.Controller;

import DAO.EspacoDAO;
import DAO.ReservaDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import View.Controller.RelatorioController;

import java.io.IOException;

public class MenuPController {
    @FXML
    private Button btncadastro;

    @FXML
    private void cadastrarEspaco() throws IOException {
        EspacoDAO.criarTabelaEspaco();
        Parent root= FXMLLoader.load(getClass().getResource("/View/fxml/TelaCadastroView.fxml"));
        Scene scene= new Scene(root);

        Stage stage= (Stage) btncadastro.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private  Button btnreserva;
    @FXML private Button btnRelatorio;
    @FXML private Button btnEspacos;



    @FXML
    private void abrirReserva() throws IOException {
        ReservaDAO.criarTabelaReserva();
        Parent root= FXMLLoader.load(getClass().getResource("/View/fxml/TelaReservaView.fxml"));
        Scene scene= new Scene(root);

        Stage stage= (Stage) btnreserva.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML private void relatorios() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/View/fxml/Relatorios.fxml"));
        Scene scene= new Scene(root);

        Stage stage= (Stage) btnRelatorio.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    @FXML private void abrirEspacos() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/View/fxml/EspacosView.fxml"));
        Scene scene= new Scene(root);

        Stage stage= (Stage) btncadastro.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}


