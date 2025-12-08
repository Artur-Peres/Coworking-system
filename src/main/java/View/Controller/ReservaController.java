package View.Controller;

import DAO.EspacoDAO;
import DAO.PagamentoDAO;
import DAO.ReservaDAO;
import Exceptions.horarioconflitanteException;
import Model.Espaco;
import Model.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;




import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import Model.SalaDeReuniao;

public class ReservaController {

    @FXML private TextField idEspaco;
    @FXML private DatePicker dataI;
    @FXML private DatePicker dataF;
    @FXML private TextField txtHoraI;
    @FXML private TextField txtHoraF;
    @FXML private Button btnSALVAR;
    @FXML private Button btnVOLTAR;
    @FXML private Button btnReserva;
    @FXML private Button btnCancelar;
    @FXML private Button btnVoltar;
    @FXML private CheckBox projetor;



    private Reserva reserva=null;

    @FXML
    private void telaReserva() throws IOException {
      Parent telaReserva =FXMLLoader.load(getClass().getResource("/View/fxml/ReservaView.fxml"));
      Scene reserva= new Scene(telaReserva);

      Stage stage= (Stage)btnReserva.getScene().getWindow();
      stage.setScene(reserva);
      stage.show();

    }
    @FXML
    private void telaCancelar() throws IOException {
        Parent telaCancelar =FXMLLoader.load(getClass().getResource("/View/cancelarReservaView.fxml"));
        Scene cancelar= new Scene(telaCancelar);

        Stage stage= (Stage)btnCancelar.getScene().getWindow();
        stage.setScene(cancelar);
        stage.show();

    }

    @FXML
    private void salvarReserva() throws IOException, SQLException {

        try {

            if (idEspaco.getText().isEmpty() && dataI.getValue()==null && dataF.getValue()==null &&
                    txtHoraI.getText().isEmpty() && txtHoraF.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Os campos estão vazios!");
                alert.setHeaderText(null);
                alert.setContentText("Preencha os campos");
                alert.showAndWait();
                return;
            }

            int idespaco = Integer.parseInt(idEspaco.getText());
            LocalDate dataRI = dataI.getValue();
            LocalDate dataRF = dataF.getValue();

            String horai = txtHoraI.getText();
            String horaf = txtHoraF.getText();

            LocalTime horaI = LocalTime.parse(horai);
            LocalTime horaF = LocalTime.parse(horaf);

            LocalDateTime inicio = LocalDateTime.of(dataRI, horaI);
            LocalDateTime fim = LocalDateTime.of(dataRF, horaF);

            Espaco espaco = EspacoDAO.criarObjetoPeloDB(idespaco);

            if (projetor.isSelected() && espaco instanceof SalaDeReuniao) {
                ((SalaDeReuniao) espaco).setUsoProjetor(true);
            }

            reserva = new Reserva(espaco, inicio, fim);


            if (ReservaDAO.horarioConflitante(idespaco, inicio, fim)) {
                throw new horarioconflitanteException("Já existe uma reserva nesse intervalo de horário.");
            }


            ReservaDAO.inserir(reserva);
            ReservaDAO.atualizarReservasConcluidas();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos preenchidos!");
            alert.setHeaderText(null);
            alert.setContentText("Dados cadastrados");
            alert.showAndWait();

            pagamento();
            limparReserva();
            return;

        } catch (horarioconflitanteException e) {


            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Horário Indisponível");
            erro.setHeaderText(null);
            erro.setContentText(e.getMessage());
            erro.showAndWait();

        }
    }

    public Reserva getReserva() {
        return reserva;
    }
    @FXML
        private void voltarAReserva(ActionEvent event) throws IOException {
            Parent root= FXMLLoader.load(getClass().getResource("/View/fxml/TelaReservaView.fxml"));
            Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene= new Scene(root);
            stage.setScene(scene);
            stage.show();

    }
    @FXML
    private void voltarAoMenu(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("/View/fxml/MenuPView.fxml"));
        Stage stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene= new Scene(root);
        stage.setScene(scene);
        stage.show();



    }
    @FXML
    private void pagamento() throws IOException {
        PagamentoDAO.criarTabelaPagamento();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/pagamentoView.fxml"));
        Parent telaPagamento = loader.load();


        pagamentoController controller = loader.getController();
        controller.setReserva(reserva);

        Stage stage = new Stage();
        stage.setTitle("Pagamento");
        stage.setScene(new Scene(telaPagamento));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    @FXML
    private void limparReserva() throws IOException {
        idEspaco.clear();
        txtHoraI.clear();
        txtHoraF.clear();
        dataI.setValue(null);
        dataF.setValue(null);

    }




}







