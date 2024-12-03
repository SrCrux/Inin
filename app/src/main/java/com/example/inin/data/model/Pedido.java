package com.example.inin.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(tableName = "pedidos")
public class Pedido implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long idPedido;
    private long idEmpresa;
    private LocalDate fecha;
    private double precioTotal;

    public Pedido() {
    }

    public Pedido(long idEmpresa, LocalDate fecha, double precioTotal) {
        this.idEmpresa = idEmpresa;
        this.fecha = fecha;
        this.precioTotal = precioTotal;
    }

    public Pedido(long idPedido, long idEmpresa, LocalDate fecha, double precioTotal) {
        this.idPedido = idPedido;
        this.idEmpresa = idEmpresa;
        this.fecha = fecha;
        this.precioTotal = precioTotal;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
