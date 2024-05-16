package org.pauloalvarez.model;

/**
 *
 * @author Paulo Alvarez
 */
public class Proveedor {
    private int codigoProveedor;
    private String NITProveedor;
    private String nombresProveedor;
    private String apellidosProveedor;
    private String direccionProveedor;
    private String razonSocial;
    private String contactoPrinicipal;
    private String paginaWeb;

    public Proveedor() {
    }

    public Proveedor(int codigoProveedor, String NITProveedor, String nombresProveedor, String apellidosProveedor, String direccionProveedor, String razonSocial, String contactoPrinicipal, String paginaWeb) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.nombresProveedor = nombresProveedor;
        this.apellidosProveedor = apellidosProveedor;
        this.direccionProveedor = direccionProveedor;
        this.razonSocial = razonSocial;
        this.contactoPrinicipal = contactoPrinicipal;
        this.paginaWeb = paginaWeb;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getNombresProveedor() {
        return nombresProveedor;
    }

    public void setNombresProveedor(String nombresProveedor) {
        this.nombresProveedor = nombresProveedor;
    }

    public String getApellidosProveedor() {
        return apellidosProveedor;
    }

    public void setApellidosProveedor(String apellidosProveedor) {
        this.apellidosProveedor = apellidosProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getContactoPrinicipal() {
        return contactoPrinicipal;
    }

    public void setContactoPrinicipal(String contactoPrinicipal) {
        this.contactoPrinicipal = contactoPrinicipal;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
    
}
