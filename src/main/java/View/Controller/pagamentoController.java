package View.Controller;

import DAO.PagamentoDAO;
import DAO.ReservaDAO;
import Model.MetodoPagamento;
import Model.Pagamento;
import Model.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import View.Controller.ReservaController;
import Model.Pagamento;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class pagamentoController {

    @FXML
    private RadioButton pix;

    @FXML
    private RadioButton cartao;

    @FXML
    private RadioButton dinheiro;

    @FXML
    private Button btnPagar;

    @FXML private Label preco;

    ReservaController reservaC=new ReservaController();
    private Reserva reserva;

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
        preco.setText("R$ " + String.valueOf(reserva.getValorCalculado()));

    }


    @FXML
    private void pagar() throws SQLException, IOException {

        RadioButton selecionado = (RadioButton) toggleGroup.getSelectedToggle();
        MetodoPagamento metodo= MetodoPagamento.valueOf(selecionado.getText());
        Pagamento pagamento= new Pagamento(
                reserva, reserva.getValorCalculado(), metodo);

        if (toggleGroup.getSelectedToggle()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERRO");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um metodo de pagamento!");
            alert.showAndWait();

        }
        PagamentoDAO.inserir(pagamento);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pagamento efetuado");
        alert.setHeaderText(null);
        alert.setContentText("Pagamento efetuado com sucesso");
        alert.showAndWait();
        voltarReserva();

    }


    private ToggleGroup toggleGroup = new ToggleGroup();


    @FXML private void initialize(){
        pix.setToggleGroup(toggleGroup);
        cartao.setToggleGroup(toggleGroup);
        dinheiro.setToggleGroup(toggleGroup);
    }
    @FXML
    private void voltarReserva() throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/View/fxml/ReservaView.fxml"));
        Stage stage= (Stage) btnPagar.getScene().getWindow();
        stage.close();
    }

}

