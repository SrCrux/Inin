package com.example.inin.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int idUsuario;
    private int idEmpresa;
    private boolean administrador;
    private String nombreUsuario;
    private String passwordUsuario;
    private String pinUsuario;
    private int imagenUsuario;

    public Usuario() {
    }

    public Usuario(int idEmpresa, boolean administrador, String nombreUsuario, String passwordUsuario, int imagenUsuario) {
        this.idEmpresa = idEmpresa;
        this.administrador = administrador;
        this.nombreUsuario = nombreUsuario;
        this.passwordUsuario = passwordUsuario;
        this.imagenUsuario = imagenUsuario;
    }

    public Usuario(int idUsuario, boolean administrador, String nombreUsuario, String passwordUsuario, String pinUsuario, int imagenUsuario, int idEmpresa) {
        this.idUsuario = idUsuario;
        this.administrador = administrador;
        this.nombreUsuario = nombreUsuario;
        this.passwordUsuario = passwordUsuario;
        this.pinUsuario = pinUsuario;
        this.imagenUsuario = imagenUsuario;
        this.idEmpresa = idEmpresa;
    }

    public Usuario(String nombreUsuario, int imagenUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.imagenUsuario = imagenUsuario;
    }

    public int getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(int imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public String getPinUsuario() {
        return pinUsuario;
    }

    public void setPinUsuario(String pinUsuario) {
        this.pinUsuario = pinUsuario;
    }
}
