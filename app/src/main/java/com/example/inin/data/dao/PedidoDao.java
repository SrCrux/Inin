package com.example.inin.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inin.data.model.Pedido;

import java.util.List;

@Dao
public interface PedidoDao {

    @Insert
    void altaPedido(Pedido pedido);

    @Query("SELECT * FROM pedidos")
    List<Pedido> listarPedidos();

    @Query("SELECT * FROM pedidos WHERE idPedido = :idPedido")
    Pedido buscarPedido(int idPedido);

    @Query("DELETE FROM pedidos WHERE idPedido = :idPedido")
    void bajaPedido(int idPedido);

    @Update
    void modificarPedido(Pedido pedido);
}