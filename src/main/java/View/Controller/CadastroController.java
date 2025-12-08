package View.Controller;

import DAO.EspacoDAO;
import Exceptions.EspacoDuplicadoException;
import Model.Auditorio;
import Model.CabineIndividual;
import Model.Espaco;
import Model.SalaDeReuniao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CadastroController {

    @FXML private TextField txtNome;
    @FXML private TextField txtTipo;
    @FXML private TextField txtCapacidade;
    @FXML private TextField txtPreco;
    @FXML private Button btnSalvar;
    @FXML private Button btnVoltar;
    @FXML private ComboBox<String> comboTipo;

    private Espaco espacoEditando;




    @FXML
    protected void salvarCadastro() throws EspacoDuplicadoException {
        if (txtNome.getText().isEmpty() || comboTipo.getValue() == null || txtCapacidade.getText().isEmpty() || txtPreco.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vazios");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos antes de salvar.");
            alert.showAndWait();
            return;
        }

        String nome = txtNome.getText().toLowerCase();
        String tipo = comboTipo.getValue().toLowerCase();
        int capacidade = Integer.parseInt(txtCapacidade.getText());
        Double preco = Double.parseDouble(txtPreco.getText());

        Espaco espaco;

        if (tipo.equals("sala de reuniao")) {
            espaco = new SalaDeReuniao(nome, capacidade, preco, tipo);
        } else if (tipo.equals("cabine individual")) {
            espaco = new CabineIndividual(nome, capacidade, preco, tipo);
        } else {
            espaco = new Auditorio(nome, capacidade, preco, tipo);
        }

        if (espacoEditando == null) {
            EspacoDAO.inserir(espaco);
        } else {
            espaco.setId(espacoEditando.getId());
            EspacoDAO.atualizar(espaco);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(espacoEditando == null ? "Espaço cadastrado!" : "Espaço atualizado!");
        alert.showAndWait();

        limparCampos();
        espacoEditando = null;
    }

    @FXML
    private void voltarAoMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/fxml/MenuPView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void limparCampos() {
        txtNome.clear();
        txtCapacidade.clear();
        txtPreco.clear();
        comboTipo.setValue(null);
    }

    public void setEspacoEditando(Espaco espaco) {
        this.espacoEditando = espaco;
        preencherCampos();
    }

    private void preencherCampos() {
        if (espacoEditando != null) {
            txtNome.setText(espacoEditando.getNome());
            txtCapacidade.setText(String.valueOf(espacoEditando.getCapacidade()));
            txtPreco.setText(String.valueOf(espacoEditando.getPrecoPorHora()));
            comboTipo.setValue(espacoEditando.getTipo());
        }
    }
}