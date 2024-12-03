package com.example.inin.data.controller;

import androidx.lifecycle.LiveData;

import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.Usuario;

import java.util.List;

public class ProductoController {
    ProductoDao productoDao;

    public ProductoController(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    public void altaProducto(Producto producto) {
        new Thread(() -> productoDao.altaProducto(producto)).start();
    }

    public void bajaProducto(int idProducto) {
        new Thread(() -> productoDao.bajaProducto(idProducto)).start();
    }

    public void modificarProducto(Producto producto) {
        new Thread(() -> productoDao.modificarProducto(producto)).start();
    }

    public LiveData<Producto> buscarProducto(int idProducto) {
        return productoDao.buscarProducto(idProducto);
    }

    public LiveData<List<Producto>> listarProductosPorEmpresa(long idEmpresa) {
        return productoDao.listarProductosPorEmpresa(idEmpresa);
    }

    public List<Producto> obtenerProductosPorId(List<Integer> ids){
        return productoDao.obtenerProductosPorIds(ids);
    }

    public void actualizarProductos(List<Producto> productos){
        new Thread(() -> productoDao.actualizarProductos(productos)).start();
    }
}
