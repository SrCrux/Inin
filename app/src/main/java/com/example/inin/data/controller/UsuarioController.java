package com.example.inin.data.controller;

import androidx.lifecycle.LiveData;

import com.example.inin.data.dao.UsuarioDao;
import com.example.inin.data.model.Usuario;


import java.util.List;

public class UsuarioController {
    private UsuarioDao usuarioDao;

    public UsuarioController(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public void altaUsuario(Usuario usuario) {
        new Thread(() -> usuarioDao.altaUsuario(usuario)).start();
    }

    public void bajaUsuario(int idUsuario) {
        new Thread(() -> usuarioDao.bajaUsuario(idUsuario)).start();
    }

    public void modificarUsuario(Usuario usuario) {
        new Thread(() -> usuarioDao.modificarUsuario(usuario)).start();
    }

    public LiveData<Usuario> buscarUsuarioPorNombre(String nombre, int idEmpresa) {
        return usuarioDao.buscarUsuarioPorNombre(nombre,idEmpresa);
    }

    public LiveData<List<Usuario>> listarUsuariosPorEmpresa(int idEmpresa) {
        return usuarioDao.listarUsuariosPorEmpresa(idEmpresa);
    }
}
