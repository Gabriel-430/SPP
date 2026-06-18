package spp.modelo.dto;

public class ResponsableTecnicoProyecto {

    private int idResponsable;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private OrganizacionVinculada organizacion;

    public ResponsableTecnicoProyecto() {
    }

    public ResponsableTecnicoProyecto(int idResponsable, String nombreCompleto, String telefono, String correo, OrganizacionVinculada organizacion) {
        this.idResponsable = idResponsable;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.organizacion = organizacion;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public OrganizacionVinculada getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(OrganizacionVinculada organizacion) {
        this.organizacion = organizacion;
    }
    
    @Override
    public String toString(){
        return nombreCompleto + " - " + correo;
    }
    
}
