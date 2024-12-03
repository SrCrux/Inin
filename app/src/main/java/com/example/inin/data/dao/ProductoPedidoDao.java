package com.example.inin.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface ProductoPedidoDao {

    @Insert
    void altaProductoPedido(ProductoPedido productoPedido);

    @Query("SELECT * FROM producto_pedido WHERE idPedido=:idPedido")
    LiveData<List<ProductoPedido>> listarProductoPedidoPorPedido(long idPedido);

    @Query("DELETE FROM producto_pedido WHERE idProductoPedido = :idProductoPedido")
    void bajaProductoPedido(int idProductoPedido);

    @Update
    void modificarProductoPedido(ProductoPedido productoPedido);

    @Query("SELECT * FROM producto_pedido ORDER BY fechaCaducidad ASC")
    LiveData<List<ProductoPedido>> obtenerProductosPorFechaCaducidadMasProxima();
}
