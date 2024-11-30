package com.example.inin.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(tableName = "pedidos")
public class Pedido implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int idPedido;
    private long idEmpresa;
    private LocalDate fecha;

    public Pedido() {
    }

    public Pedido(long idEmpresa, LocalDate fecha) {
        this.idEmpresa = idEmpresa;
        this.fecha = fecha;
    }

    public Pedido(int idPedido, long idEmpresa, LocalDate fecha) {
        this.idPedido = idPedido;
        this.idEmpresa = idEmpresa;
        this.fecha = fecha;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
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
}
