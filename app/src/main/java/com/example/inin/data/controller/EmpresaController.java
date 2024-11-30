package com.example.inin.data.controller;

import androidx.lifecycle.LiveData;

import com.example.inin.data.dao.EmpresaDao;
import com.example.inin.data.model.Empresa;

import java.util.List;

public class EmpresaController {

    private EmpresaDao empresaDao;

    public EmpresaController(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    public long altaEmpresa(Empresa empresa) {
        return empresaDao.altaEmpresa(empresa);
    }

    public void bajaEmpresa(int idEmpresa) {
        new Thread(() -> empresaDao.bajaEmpresa(idEmpresa)).start();
    }

    public void modificarEmpresa(Empresa empresa) {
        new Thread(() -> empresaDao.modificarEmpresa(empresa)).start();
    }

    public LiveData<Empresa> buscarEmpresaPorNombre(String nombre) {
        return empresaDao.buscarEmpresaPorNombre(nombre);
    }

    public LiveData<Empresa> buscarEmpresaPorNif(String nif) {
        return empresaDao.buscarEmpresaPorNif(nif);
    }

    public LiveData<List<Empresa>> listarEmpresas() {
        return empresaDao.listarEmpresas();
    }
}

