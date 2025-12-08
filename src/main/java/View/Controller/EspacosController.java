package View.Controller;


import DAO.EspacoDAO;
import Model.Espaco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EspacosController {

    @FXML private TableView<Espaco> tabelaEspacos;
    @FXML private TableColumn<Espaco, Integer> colId;
    @FXML private TableColumn<Espaco, String> colNome;
    @FXML private TableColumn<Espaco, Integer> colCapacidade;
    @FXML private TableColumn<Espaco, Double> colPreco;
    @FXML private TableColumn<Espaco, String> colTipo;

    @FXML private Button btnRemover;
    @FXML private Button btnEditar;
    @FXML private Button btnVoltar;

    private ObservableList<Espaco> listaEspacos;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarDados();
    }

    private void configurarTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoPorHora"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    private void carregarDados() {
        try {
            List<Espaco> espacos = EspacoDAO.listarEspacos();
            listaEspacos = FXCollections.observableArrayList(espacos);
            tabelaEspacos.setItems(listaEspacos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removerEspaco() {
        Espaco selecionado = tabelaEspacos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            EspacoDAO.removerEspaco(selecionado.getId());
            listaEspacos.remove(selecionado);
        }
    }

    @FXML
    private void editarEspaco() throws IOException {
        Espaco selecionado = tabelaEspacos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/TelaCadastroView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tabelaEspacos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void voltar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/MenuPView.fxml"));
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
