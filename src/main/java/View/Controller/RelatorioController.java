package View.Controller;
import DAO.ReservaDAO;
import Model.Reserva;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class RelatorioController {

    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFim;
    @FXML private Button btnBuscarPeriodo;
    @FXML private Button btnVoltar;


    @FXML private TableView<Reserva> tabelaReservas;
    @FXML private TableColumn<Reserva, Integer> colId;
    @FXML private TableColumn<Reserva, String> colEspaco;
    @FXML private TableColumn<Reserva, LocalDateTime> colInicio;
    @FXML private TableColumn<Reserva, LocalDateTime> colFim;
    @FXML private TableColumn<Reserva, String> colStatus;
    @FXML private TableColumn<Reserva, Double> colValor;

    @FXML private TableView<Map.Entry<String, Double>> tabelaFaturamento;
    @FXML private TableColumn<Map.Entry<String, Double>, String> colTipoFatur;
    @FXML private TableColumn<Map.Entry<String, Double>, Double> colValorFatur;

    @FXML private TableView<Map.Entry<String, Double>> tabelaUtilizacao;
    @FXML private TableColumn<Map.Entry<String, Double>, String> colEspacoUtil;
    @FXML private TableColumn<Map.Entry<String, Double>, Double> colHorasUtil;

    @FXML private ListView<String> listaRanking;

    @FXML
    public void initialize() {

        // ------------------ TABELA 1: Reservas ------------------
        colId.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getId()));
        colEspaco.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEspaco().getNome()));
        colInicio.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDataHoraFim()));
        colFim.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDataHoraFim()));
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        colValor.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getValorCalculado()));

        btnBuscarPeriodo.setOnAction(e -> buscarReservas());
        btnVoltar.setOnAction(e -> {
            try {
                voltar();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // ------------------ TABELA 2: Faturamento ------------------
        colTipoFatur.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getKey()));
        colValorFatur.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue()));
        tabelaFaturamento.setItems(FXCollections.observableArrayList(
                ReservaDAO.faturamentoPorTipo().entrySet()
        ));

        // ------------------ TABELA 3: Utilização ------------------
        colEspacoUtil.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getKey()));
        colHorasUtil.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getValue()));
        tabelaUtilizacao.setItems(FXCollections.observableArrayList(
                ReservaDAO.utilizacaoPorEspaco().entrySet()
        ));

        // ------------------ LISTA 4: Ranking ------------------
        listaRanking.getItems().addAll(ReservaDAO.rankingEspacosMaisUtilizados());
    }
    @FXML
    private void buscarReservas() {
        if (dpInicio.getValue() == null || dpFim.getValue() == null) return;

        LocalDateTime ini = dpInicio.getValue().atStartOfDay();
        LocalDateTime fim = dpFim.getValue().atTime(23, 59);

        tabelaReservas.setItems(FXCollections.observableArrayList(
                ReservaDAO.listarReservasPorPeriodo(ini, fim)
        ));
    }
    private void voltar()throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/fxml/MenuPView.fxml"));
            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

