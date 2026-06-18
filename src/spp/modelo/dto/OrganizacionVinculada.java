package spp.modelo.dto;

public class OrganizacionVinculada {

    private int idOrganizacion;
    private String razonSocial;
    private String nombreComercial;
    private String rfc;
    private String direccion;

    public OrganizacionVinculada() {
    }

    public OrganizacionVinculada(int idOrganizacion, String razonSocial, String nombreComercial, String rfc, String direccion) {
        this.idOrganizacion = idOrganizacion;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.rfc = rfc;
        this.direccion = direccion;
    }

    public int getIdOrganizacion() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion = idOrganizacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    @Override
    public String toString() {
        return nombreComercial + " - " + rfc;
    }
}
