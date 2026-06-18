package spp.modelo.dto;

public class CatalogoEvaluacion {

    private int idCatalogo;
    private String tipoEvaluacion;

    public CatalogoEvaluacion() {
    }

    public CatalogoEvaluacion(int idCatalogo, String tipoEvaluacion) {
        this.idCatalogo = idCatalogo;
        this.tipoEvaluacion = tipoEvaluacion;
    }

    public int getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(int idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getTipoEvaluacion() {
        return tipoEvaluacion;
    }

    public void setTipoEvaluacion(String tipoEvaluacion) {
        this.tipoEvaluacion = tipoEvaluacion;
    }
}
