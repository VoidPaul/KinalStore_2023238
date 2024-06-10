package org.pauloalvarez.model;

import java.sql.Date;

/**
 *
 * @author Paulo Alvarez
 */
public class Compra {
    private int numeroCompra;
    private Date fechaCompra;
    private String descripcion;
    private double totalCompra;

    public Compra() {
    }

    public Compra(int numeroCompra, Date fechaCompra, String descripcion, double totalCompra) {
        this.numeroCompra = numeroCompra;
        this.fechaCompra = fechaCompra;
        this.descripcion = descripcion;
        this.totalCompra = totalCompra;
    }

    public int getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(int numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }
    
}
