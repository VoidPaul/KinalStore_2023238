package org.pauloalvarez.model;

/**
 *
 * @author Paulo Alvarez
 */
public class InfoCompra {
    private int codigoInfoCompra;
    private double costoUnitario;
    private int cantidad;
    private String codigoProducto;
    private int numeroDocumento;

    public InfoCompra() {
    }

    public InfoCompra(int codigoInfoCompra, double costoUnitario, int cantidad, String codigoProducto, int numeroDocumento) {
        this.codigoInfoCompra = codigoInfoCompra;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.codigoProducto = codigoProducto;
        this.numeroDocumento = numeroDocumento;
    }

    public int getCodigoInfoCompra() {
        return codigoInfoCompra;
    }

    public void setCodigoInfoCompra(int codigoInfoCompra) {
        this.codigoInfoCompra = codigoInfoCompra;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    
}
