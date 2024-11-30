package com.example.tap2024proyecto.vistas;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.tap2024proyecto.models.VentasDAO;
import com.example.tap2024proyecto.models.ClienteDAO;

public class FormVenta extends Stage {
    private ComboBox<ClienteDAO> cmbCliente;
    private TextField txtFechaVenta;
    private TextField txtTotalVenta;
    private Button btnGuardar;
    private VBox vBox;
    private Scene escena;
    private VentasDAO objVenta;
    private TableView<VentasDAO> tblVenta;

    public FormVenta(TableView<VentasDAO> tableView, VentasDAO venta) {
        tblVenta = tableView;
        crearUI();

        if (venta != null) {
            this.objVenta = venta;
            cmbCliente.getSelectionModel().select(findCliente(objVenta.getIdCliente()));
            txtFechaVenta.setText(objVenta.getFechaVenta());
            txtTotalVenta.setText(String.valueOf(objVenta.getTotalVenta()));
            this.setTitle("Editar Venta");
        } else {
            this.objVenta = new VentasDAO();
            this.setTitle("Agregar Venta");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        cmbCliente = new ComboBox<>();
        cmbCliente.setPromptText("Selecciona un Cliente");
        cmbCliente.setItems(loadClientes());

        txtFechaVenta = new TextField();
        txtFechaVenta.setPromptText("Fecha de la Venta (YYYY-MM-DD)");

        txtTotalVenta = new TextField();
        txtTotalVenta.setPromptText("Total de la Venta");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> guardarVenta());

        vBox = new VBox(cmbCliente, txtFechaVenta, txtTotalVenta, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        escena = new Scene(vBox, 400, 300);
    }

    private ObservableList<ClienteDAO> loadClientes() {
        return new ClienteDAO().SELECTALL();
    }

    private ClienteDAO findCliente(int idCliente) {
        for (ClienteDAO cliente : loadClientes()) {
            if (cliente.getIdCte() == idCliente) {
                return cliente;
            }
        }
        return null;
    }

    private void guardarVenta() {
        if (cmbCliente.getSelectionModel().isEmpty()) {
            showAlert("Debe seleccionar un cliente.", Alert.AlertType.WARNING);
            return;
        }

        try {
            objVenta.setIdCliente(cmbCliente.getSelectionModel().getSelectedItem().getIdCte());
            objVenta.setFechaVenta(txtFechaVenta.getText());
            objVenta.setTotalVenta(Double.parseDouble(txtTotalVenta.getText()));

            String mensaje;
            Alert.AlertType tipo;

            if (objVenta.getIdVenta() > 0) {
                objVenta.UPDATE();
                mensaje = "Venta actualizada con éxito.";
                tipo = Alert.AlertType.INFORMATION;
            } else {
                if (objVenta.INSERT() > 0) {
                    mensaje = "Venta agregada con éxito.";
                    tipo = Alert.AlertType.INFORMATION;
                } else {
                    mensaje = "Error al agregar la venta.";
                    tipo = Alert.AlertType.ERROR;
                }
            }

            showAlert(mensaje, tipo);

            tblVenta.setItems(objVenta.SELECTALL());
            tblVenta.refresh();
            this.close();

        } catch (NumberFormatException e) {
            showAlert("El total debe ser un número válido.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Resultado");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
