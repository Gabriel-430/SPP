package spp.service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dao.DocumentoDAO;
import spp.modelo.dto.Documento;
import spp.modelo.dto.Reporte;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 17/06/2026
 * Descripción: Lógica de negocio para validar, subir y preparar documentos.
 */
public class DocumentoService {

    private final DocumentoDAO documentoDAO;

    public DocumentoService() {
        this.documentoDAO = new DocumentoDAO();
    }

    public boolean archivoYaExiste(String rutaArchivo, String matricula) {
        return documentoDAO.validarArchivoExistente(rutaArchivo, matricula);
    }

    public boolean subirDocumento(Documento documento, String matricula, File archivoFisico) {
        documento.setFechaEntrega(LocalDate.now());
        documento.setFechaEmision(LocalDate.now());
        documento.setEstado("Pendiente");

        try {
            File directorio = new File("archivos_subidos");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            File destino = new File(documento.getRutaArchivo());
            Files.copy(archivoFisico.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        int idGenerado = documentoDAO.guardarDocumento(documento);

        if (idGenerado > 0) {
            return documentoDAO.vincularDocumentoConPracticante(idGenerado, matricula);
        }

        return false;
    }

    public File obtenerArchivoPorRuta(String ruta) {
        return new File(ruta);
    }

    public String extraerDatosArchivo(File archivo) throws IllegalArgumentException {
        if (!archivo.exists() || !archivo.isFile()) {
            throw new IllegalArgumentException("El archivo físico no se encontró en el servidor.");
        }
        return mostrarVistaPrevia(archivo);
    }

    public String mostrarVistaPrevia(File archivo) {
        try (PDDocument documentoPDF = PDDocument.load(archivo)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(documentoPDF);
        } catch (IOException e) {
            System.err.println("Error al procesar el PDF: " + e.getMessage());
            return "El documento seleccionado no pudo ser procesado o no contiene texto legible.";
        }
    }

    public void descargarArchivo(Documento documento, File rutaDestino) throws IOException {
        File archivoOrigen = obtenerArchivoPorRuta(documento.getRutaArchivo());
        if (!archivoOrigen.exists()) {
            throw new IOException("El archivo de origen no existe.");
        }
        Files.copy(archivoOrigen.toPath(), rutaDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public List<Documento> recuperarDocumentosPorPracticaProfesional(int idPracticante) throws SQLException {
        List<Documento> documentos = new ArrayList<>();
        String consulta = "SELECT d.* FROM Documento d "
                + "INNER JOIN PracticaProfesional pp ON d.id_practica = pp.id_practica "
                + "WHERE pp.id_practicante = ?";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement sentencia = conexion.prepareStatement(consulta)) {

            sentencia.setInt(1, idPracticante);
            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    Documento doc = new Documento();
                    doc.setIdDocumento(resultado.getInt("id_documento"));
                    doc.setNombreDocumento(resultado.getString("nombreDocumento"));
                    doc.setRutaArchivo(resultado.getString("rutaArchivo"));
                    java.sql.Date sqlDate = resultado.getDate("fechaEntrega");
                    if (sqlDate != null) {
                        doc.setFechaEntrega(sqlDate.toLocalDate());
                    }
                    documentos.add(doc);
                }
            }
        }
        return documentos;
    }
    
    public void descargarArchivo(Reporte reporte) throws IOException {
        if (reporte == null || reporte.getRutaArchivo() == null) {
            throw new IOException("No se encontró la ruta del archivo.");
        }

        File archivoOrigen = obtenerArchivoPorRuta(reporte.getRutaArchivo());

        if (archivoOrigen == null || !archivoOrigen.exists()) {
            throw new IOException("El archivo no existe.");
    }

    Desktop.getDesktop().open(archivoOrigen);
}
}
