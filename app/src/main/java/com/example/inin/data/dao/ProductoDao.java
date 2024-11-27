package com.example.inin.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inin.data.model.Producto;
import com.example.inin.data.model.Usuario;

import java.util.List;

@Dao
public interface ProductoDao {

    @Insert
    void altaProducto(Producto producto);

    @Query("SELECT * FROM productos")
    List<Producto> listarProductos();

    @Query("SELECT * FROM productos WHERE idProducto = :idProducto")
    Producto buscarProducto(int idProducto);

    @Query("DELETE FROM productos WHERE idProducto = :idProducto")
    void bajaProducto(int idProducto);

    @Update
    void modificarProducto(Producto producto);
}
