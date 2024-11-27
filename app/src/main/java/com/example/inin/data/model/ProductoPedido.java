package com.example.inin.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "producto_pedido")
public class ProductoPedido {

    @PrimaryKey(autoGenerate = true)
    private int idProductoPedido;
    private int idPedido;
    private int idProducto;
    private LocalDate fechaCaducidad;
    private int cantidad;
    private double precio;

    public ProductoPedido() {
    }

    public ProductoPedido(int idProductoPedido, int idPedido, int idProducto, LocalDate fechaCaducidad, int cantidad, double precio) {
        this.idProductoPedido = idProductoPedido;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getIdProductoPedido() {
        return idProductoPedido;
    }

    public void setIdProductoPedido(int idProductoPedido) {
        this.idProductoPedido = idProductoPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
