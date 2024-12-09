package com.example.tap2024proyecto.components;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PDFGenerator {

    // Método para generar el reporte del administrador
    public static void generarReporteAdministrador(String contenido, Window stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File archivoSeleccionado = fileChooser.showSaveDialog(stage);
        if (archivoSeleccionado != null) {
            try {
                PdfWriter writer = new PdfWriter(new FileOutputStream(archivoSeleccionado));
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);
                document.add(new Paragraph("Reporte de Administración"));
                document.add(new Paragraph(contenido));

                document.close();
                System.out.println("PDF generado en: " + archivoSeleccionado.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al generar el archivo PDF.");
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }

    // Método para generar el recibo de compra para el cliente
    public static void generarReciboCompra(String clienteNombre, List<String> productos, double total, Window stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File archivoSeleccionado = fileChooser.showSaveDialog(stage);

        if (archivoSeleccionado != null) {
            try {
                PdfWriter writer = new PdfWriter(new FileOutputStream(archivoSeleccionado));
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                // Título del recibo
                document.add(new Paragraph("Recibo de Compra"));
                document.add(new Paragraph("Cliente: " + clienteNombre));
                document.add(new Paragraph("Fecha de Compra: " + java.time.LocalDate.now())); // Fecha actual

                // Tabla de productos comprados
                Table table = new Table(3); // 3 columnas: Producto, Cantidad, Precio
                table.addHeaderCell("Producto");
                table.addHeaderCell("Cantidad");
                table.addHeaderCell("Precio");

                // Llenado de la tabla con los productos
                for (String producto : productos) {
                    String[] datosProducto = producto.split(" - ");
                    table.addCell(datosProducto[0]); // Producto
                    table.addCell(datosProducto[1]); // Cantidad
                    table.addCell(datosProducto[2]); // Precio
                }

                document.add(table);

                // Total de la compra
                document.add(new Paragraph("Total: $" + total));

                document.close();
                System.out.println("Recibo generado en: " + archivoSeleccionado.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al generar el recibo.");
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }
}
