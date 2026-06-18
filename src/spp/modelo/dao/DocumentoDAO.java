package spp.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import spp.modelo.MySQLConnectionManager;
import spp.modelo.dto.Documento;

/*
 * Autor: Gabriel Hernández Martínez
 * Fecha de creación: 16/06/2026
 * Descripción: Maneja el acceso a datos base para cualquier tipo de Documento.
 */
public class DocumentoDAO {

    public boolean validarArchivoExistente(String rutaArchivo, String matricula) {
        String query = "SELECT d.id_documento FROM documento d "
                + "INNER JOIN practicaprofesional p ON d.id_practica = p.id_practica "
                + "INNER JOIN practicante pr ON p.id_practicante = pr.id_practicante "
                + "WHERE d.rutaArchivo = ? AND pr.matricula = ?";
        boolean existe = false;

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query)) {
            declaracion.setString(1, rutaArchivo);
            declaracion.setString(2, matricula);
            ResultSet resultado = declaracion.executeQuery();
            if (resultado.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public int guardarDocumento(Documento documento) {
        int idGenerado = 0;
        String query = "INSERT INTO documento (nombreDocumento, fechaEntrega, fechaEmision, estado, rutaArchivo, id_catalogoDocumento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            declaracion.setString(1, documento.getNombreDocumento());
            declaracion.setDate(2, documento.getFechaEntrega() != null ? Date.valueOf(documento.getFechaEntrega()) : null);
            declaracion.setDate(3, documento.getFechaEmision() != null ? Date.valueOf(documento.getFechaEmision()) : null);
            declaracion.setString(4, documento.getEstado());
            declaracion.setString(5, documento.getRutaArchivo());

            if (documento.getIdCatalogoDocumento() > 0) {
                declaracion.setInt(6, documento.getIdCatalogoDocumento());
            } else {
                declaracion.setNull(6, java.sql.Types.INTEGER);
            }

            int filasAfectadas = declaracion.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet llaves = declaracion.getGeneratedKeys()) {
                    if (llaves.next()) {
                        idGenerado = llaves.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    public boolean vincularDocumentoConPracticante(int idDocumento, String matricula) {
        String query = "UPDATE documento SET id_practica = ("
                + "SELECT p.id_practica FROM practicaprofesional p "
                + "INNER JOIN practicante pr ON p.id_practicante = pr.id_practicante "
                + "WHERE pr.matricula = ? LIMIT 1"
                + ") WHERE id_documento = ?";
        boolean exito = false;

        try (Connection conexion = MySQLConnectionManager.obtenerConexion(); PreparedStatement declaracion = conexion.prepareStatement(query)) {
            declaracion.setString(1, matricula);
            declaracion.setInt(2, idDocumento);
            int filasAfectadas = declaracion.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }

    public List<Documento> recuperarDocumentos(int idPracticante) throws SQLException {
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
}
