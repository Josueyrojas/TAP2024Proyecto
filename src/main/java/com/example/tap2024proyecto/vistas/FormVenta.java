package com.example.tap2024proyecto.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import com.example.tap2024proyecto.models.VentasDAO;
import com.example.tap2024proyecto.models.ClienteDAO;

public class FormVenta extends Stage {

    private TextField txtFechaVenta;
    private ComboBox<ClienteDAO> cmbClientes;
    private Button btnGuardar;
    private VBox vBox;
    private VentasDAO objVenta;
    private Scene escena;
    private TableView<VentasDAO> tblVenta;

    public FormVenta(TableView<VentasDAO> tbl, VentasDAO objV) {
        tblVenta = tbl;
        crearUI();

        if (objV != null) {
            this.objVenta = objV;
            txtFechaVenta.setText(objVenta.getFechaVenta());
            cmbClientes.setValue(getClienteById(objVenta.getIdCliente()));
            this.setTitle("Editar Venta");
        } else {
            this.objVenta = new VentasDAO();
            this.setTitle("Agregar Venta");
        }

        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        txtFechaVenta = new TextField();
        txtFechaVenta.setPromptText("Ingrese la fecha de la venta (YYYY-MM-DD)");

        cmbClientes = new ComboBox<>();
        cargarClientes();

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> guardarVenta());

        vBox = new VBox(txtFechaVenta, cmbClientes, btnGuardar);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        escena = new Scene(vBox, 300, 200);
    }

    private void cargarClientes() {
        ClienteDAO objCliente = new ClienteDAO();
        cmbClientes.setItems(objCliente.SELECTALL());
        cmbClientes.setPromptText("Seleccione un cliente");
    }

    private ClienteDAO getClienteById(int idCliente) {
        for (ClienteDAO cliente : cmbClientes.getItems()) {
            if (cliente.getIdCte() == idCliente) {
                return cliente;
            }
        }
        return null;
    }

    private void guardarVenta() {
        objVenta.setFechaVenta(txtFechaVenta.getText());
        ClienteDAO clienteSeleccionado = cmbClientes.getValue();

        if (clienteSeleccionado != null) {
            objVenta.setIdCliente(clienteSeleccionado.getIdCte());

            String msj;
            Alert.AlertType type;

            if (objVenta.getIdVenta() > 0) {
                objVenta.UPDATE();
                msj = "Venta actualizada con éxito.";
                type = Alert.AlertType.INFORMATION;
            } else {
                if (objVenta.Insert() > 0) {
                    msj = "Venta registrada con éxito.";
                    type = Alert.AlertType.INFORMATION;
                } else {
                    msj = "Error al registrar la venta. Intente nuevamente.";
                    type = Alert.AlertType.ERROR;
                }
            }

            Alert alerta = new Alert(type);
            alerta.setTitle("Mensaje del Sistema");
            alerta.setContentText(msj);
            alerta.showAndWait();

            tblVenta.setItems(objVenta.SELECTALL());
            tblVenta.refresh();
            this.close();
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setContentText("Por favor seleccione un cliente.");
            alerta.showAndWait();
        }
    }
}