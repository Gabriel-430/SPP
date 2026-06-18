package spp.modelo.dto;

public class Proyecto {

    private int idProyecto;
    private int cupoDisponible;
    private String estadoProyecto;
    private OrganizacionVinculada organizacion;
    private ResponsableTecnicoProyecto responsable;
    private String nombreProyecto;
    private String descripcionActividades;

    public Proyecto() {
    }

    public Proyecto(int idProyecto, int cupoDisponible, String estadoProyecto, OrganizacionVinculada organizacion, ResponsableTecnicoProyecto responsable) {
        this.idProyecto = idProyecto;
        this.cupoDisponible = cupoDisponible;
        this.estadoProyecto = estadoProyecto;
        this.organizacion = organizacion;
        this.responsable = responsable;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public String getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(String estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public OrganizacionVinculada getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(OrganizacionVinculada organizacion) {
        this.organizacion = organizacion;
    }

    public ResponsableTecnicoProyecto getResponsable() {
        return responsable;
    }

    public void setResponsable(ResponsableTecnicoProyecto responsable) {
        this.responsable = responsable;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcionActividades() {
        return descripcionActividades;
    }

    public void setDescripcionActividades(String descripcionActividades) {
        this.descripcionActividades = descripcionActividades;
    }
    
}
