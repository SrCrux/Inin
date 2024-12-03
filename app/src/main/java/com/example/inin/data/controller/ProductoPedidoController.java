package com.example.inin.data.controller;

import androidx.lifecycle.LiveData;
import com.example.inin.data.dao.ProductoPedidoDao;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;

import java.time.LocalDate;
import java.util.List;

public class ProductoPedidoController {
    ProductoPedidoDao productoPedidoDao;

    public ProductoPedidoController(ProductoPedidoDao productoPedidoDao) {
        this.productoPedidoDao = productoPedidoDao;
    }

    public void altaProductoPedido(ProductoPedido productoPedido) {
        new Thread(() -> productoPedidoDao.altaProductoPedido(productoPedido)).start();
    }

    public void bajaProductoPedido(int idProductoPedido) {
        new Thread(() -> productoPedidoDao.bajaProductoPedido(idProductoPedido)).start();
    }

    public void modificarProductoPedido(ProductoPedido productoPedido) {
        new Thread(() -> productoPedidoDao.modificarProductoPedido(productoPedido)).start();
    }

    public LiveData<List<ProductoPedido>> listarProductosPorPedido(long idPedido) {
        return productoPedidoDao.listarProductoPedidoPorPedido(idPedido);
    }

    public LiveData<List<ProductoPedido>> obtenerProductosPorFechaCaducidadMasProxima(){
        return productoPedidoDao.obtenerProductosPorFechaCaducidadMasProxima();
    }
}