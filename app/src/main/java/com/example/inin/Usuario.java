package com.example.inin;

public class Usuario {

    private int idUsuario;
    private boolean administrador;
    private String nombreUsuario;
    private String passwordUsuario;
    private String pinUsuario;

    public Usuario() {
    }

    public Usuario(int idUsuario, boolean administrador, String nombreUsuario, String passwordUsuario, String pinUsuario) {
        this.idUsuario = idUsuario;
        this.administrador = administrador;
        this.nombreUsuario = nombreUsuario;
        this.passwordUsuario = passwordUsuario;
        this.pinUsuario = pinUsuario;
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
