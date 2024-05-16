package org.pauloalvarez.model;

/**
 *
 * @author Paulo Alvarez
 */
public class InfoFactura {
    private int codigoInfoFactura;
    private double precioUnitario;
    private int cantidad;
    private int numeroFactura;
    private String codigoProducto;

    public InfoFactura() {
    }

    public InfoFactura(int codigoInfoFactura, double precioUnitario, int cantidad, int numeroFactura, String codigoProducto) {
        this.codigoInfoFactura = codigoInfoFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.numeroFactura = numeroFactura;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoInfoFactura() {
        return codigoInfoFactura;
    }

    public void setCodigoInfoFactura(int codigoInfoFactura) {
        this.codigoInfoFactura = codigoInfoFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
}
