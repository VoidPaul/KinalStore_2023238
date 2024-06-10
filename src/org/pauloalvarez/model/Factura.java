package org.pauloalvarez.model;

import java.sql.Date;

/**
 *
 * @author Paulo Alvarez
 */
public class Factura {
    private int numeroFactura;
    private String estado;
    private double totalFacutura;
    private Date fechaFactura;
    private int codigoCliente;
    private int codigoEmpleado;

    public Factura() {
    }

    public Factura(int numeroFactura, String estado, double totalFacutura, Date fechaFactura, int codigoCliente, int codigoEmpleado) {
        this.numeroFactura = numeroFactura;
        this.estado = estado;
        this.totalFacutura = totalFacutura;
        this.fechaFactura = fechaFactura;
        this.codigoCliente = codigoCliente;
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotalFacutura() {
        return totalFacutura;
    }

    public void setTotalFacutura(double totalFacutura) {
        this.totalFacutura = totalFacutura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }
    
}
