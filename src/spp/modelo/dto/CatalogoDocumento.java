package spp.modelo.dto;

public class CatalogoDocumento {

    private int idCatalogoDocumento;
    private String tipoDocumento;
    private String descripcion;
    private boolean activo;

    public CatalogoDocumento() {
    }

    public CatalogoDocumento(int idCatalogoDocumento, String tipoDocumento, String descripcion, boolean activo) {
        this.idCatalogoDocumento = idCatalogoDocumento;
        this.tipoDocumento = tipoDocumento;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdCatalogoDocumento() {
        return idCatalogoDocumento;
    }

    public void setIdCatalogoDocumento(int idCatalogoDocumento) {
        this.idCatalogoDocumento = idCatalogoDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
