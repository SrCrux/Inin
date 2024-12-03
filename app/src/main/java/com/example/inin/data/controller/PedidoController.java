package com.example.inin.data.controller;

import androidx.lifecycle.LiveData;

import com.example.inin.data.dao.PedidoDao;
import com.example.inin.data.model.Pedido;
import com.example.inin.data.model.Producto;

import java.util.List;

public class PedidoController {
    PedidoDao pedidoDao;

    public PedidoController(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    public long altaPedido(Pedido pedido) {
        return pedidoDao.altaPedido(pedido);
    }

    public void bajaPedido(long idPedido) {
        new Thread(() -> pedidoDao.bajaPedido(idPedido)).start();
    }

    public void modificarPedido(Pedido pedido) {
        new Thread(() -> pedidoDao.modificarPedido(pedido)).start();
    }

    public Pedido buscarPedido(long idPedido) {
        return pedidoDao.buscarPedido(idPedido);
    }

    public LiveData<List<Pedido>> listarPedidosPorEmpresa(long idEmpresa) {
        return pedidoDao.listarPedidosPorEmpresa(idEmpresa);
    }
}
