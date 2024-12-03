package com.example.inin.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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


    @Query("SELECT * FROM productos WHERE idEmpresa=:idEmpresa")
    LiveData<List<Producto>> listarProductosPorEmpresa(long idEmpresa);

    @Query("SELECT * FROM productos WHERE idProducto = :idProducto")
    LiveData<Producto> buscarProducto(int idProducto);

    @Query("DELETE FROM productos WHERE idProducto = :idProducto")
    void bajaProducto(int idProducto);

    @Update
    void modificarProducto(Producto producto);

    @Query("SELECT * FROM productos WHERE idProducto IN (:ids)")
    List<Producto> obtenerProductosPorIds(List<Integer> ids);

    @Update
    void actualizarProductos(List<Producto> productos);
}
