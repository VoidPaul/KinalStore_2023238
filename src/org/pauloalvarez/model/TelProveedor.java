package org.pauloalvarez.model;

/**
 *
 * @author Paulo Alvarez
 */
public class TelProveedor {
    private int codigoTelProveedor;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observaciones;
    private int codigoProveedor;

    public TelProveedor() {
    }

    public TelProveedor(int codigoTelProveedor, String numeroPrincipal, String numeroSecundario, String observaciones, int codigoProveedor) {
        this.codigoTelProveedor = codigoTelProveedor;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
        this.codigoProveedor = codigoProveedor;
    }

    public int getCodigoTelProveedor() {
        return codigoTelProveedor;
    }

    public void setCodigoTelProveedor(int codigoTelProveedor) {
        this.codigoTelProveedor = codigoTelProveedor;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
    
}
