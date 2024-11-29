package com.example.inin.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inin.data.model.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void altaUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE idEmpresa=:idEmpresa")
    LiveData<List<Usuario>> listarUsuariosPorEmpresa(int idEmpresa);

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombre AND idEmpresa = :idEmpresa")
    LiveData<Usuario> buscarUsuarioPorNombre(String nombre,int idEmpresa);

    @Query("DELETE FROM usuarios WHERE idUsuario = :idUsuario")
    void bajaUsuario(int idUsuario);

    @Update
    void modificarUsuario(Usuario usuario);
}
