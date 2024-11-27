package com.example.inin.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inin.data.model.Empresa;

import java.util.List;

@Dao
public interface EmpresaDao {
    @Insert
    void altaEmpresa(Empresa empresa);

    @Query("SELECT * FROM empresas")
    LiveData<List<Empresa>> listarEmpresas();

    @Query("SELECT * FROM empresas WHERE nombre=:nombre")
    LiveData<Empresa> buscarEmpresaPorNombre(String nombre);

    @Query("SELECT * FROM empresas WHERE nif=:nif")
    LiveData<Empresa> buscarEmpresaPorNif(String nif);

    @Query("DELETE FROM empresas WHERE idEmpresa = :idEmpresa")
    void bajaEmpresa(int idEmpresa);

    @Update
    void modificarEmpresa(Empresa empresa);
}

