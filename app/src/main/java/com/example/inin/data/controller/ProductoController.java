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

    public LiveData<Producto> buscarProductoPorNombre(int idProducto) {
        return productoDao.buscarProducto(idProducto);
    }

    public LiveData<List<Producto>> listarProductosPorEmpresa(int idEmpresa) {
        return productoDao.listarProductosPorEmpresa(idEmpresa);
    }
}
