package com.example.inin;

public class Usuario {

    private int idUsuario;
    private boolean administrador;
    private String nombreUsuario;
    private String passwordUsuario;
    private String pinUsuario;
    private int imagenUsuario;
    private String idEmpresa;

    public Usuario() {
    }

    public Usuario(int idUsuario, boolean administrador, String nombreUsuario, String passwordUsuario, String pinUsuario, int imagenUsuario, String idEmpresa) {
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

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
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
