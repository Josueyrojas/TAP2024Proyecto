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
    private ListView<String> listProductos;  // Añadido para mostrar los productos seleccionados

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

        // Lista de productos seleccionados para la venta
        listProductos = new ListView<>();
        listProductos.setPrefHeight(150);
        listProductos.setPlaceholder(new Label("Selecciona los productos"));

        vBox = new VBox(cmbCliente, txtFechaVenta, txtTotalVenta, listProductos, btnGuardar);
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

            // Obtener los IDs de los productos seleccionados
            int[] idsProductos = obtenerIdsDeProductos();

            String mensaje;
            Alert.AlertType tipo;

            if (objVenta.getIdVenta() > 0) {
                // Llamar al método UPDATE y pasar el arreglo de IDs de los productos
                objVenta.UPDATE(idsProductos);
                mensaje = "Venta actualizada con éxito.";
                tipo = Alert.AlertType.INFORMATION;
            } else {
                // Llamar al método INSERT con los productos seleccionados
                if (objVenta.INSERT(idsProductos) > 0) {
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


    // Este método debe retornar los IDs de los productos seleccionados en la UI
    private int[] obtenerIdsDeProductos() {
        // Asumiendo que los productos están representados por Strings, y puedes tener una lógica para obtener los IDs
        // Cambia esto según cómo manejes los productos (por ejemplo, podrías tener una lista de productos en tu UI)

        // Ejemplo estático:
        return new int[]{1, 2, 3};  // Reemplaza esto con la lógica real para obtener los productos seleccionados
    }

    private void showAlert(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Resultado");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
