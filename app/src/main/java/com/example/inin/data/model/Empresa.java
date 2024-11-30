package com.example.inin.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "empresas")
public class Empresa {
    @PrimaryKey(autoGenerate = true)
    private long idEmpresa;
    private String nombre;
    private String nif;

    public Empresa() {
    }

    public Empresa(String nombre, String nif) {
        this.nombre = nombre;
        this.nif = nif;
    }

    public Empresa(long idEmpresa, String nombre, String nif) {
        this.idEmpresa = idEmpresa;
        this.nombre = nombre;
        this.nif = nif;
    }

    public long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
