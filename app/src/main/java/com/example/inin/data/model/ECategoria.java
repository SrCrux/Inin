package com.example.inin.data.model;

import com.example.inin.R;

public enum ECategoria {
    PESCADO("Pescado", R.drawable.pescado),
    CARNE("Carne", R.drawable.carne),
    VERDURA("Verdura", R.drawable.verdura),
    FRUTA("Fruta", R.drawable.fruta),
    SALSA("Salsa", R.drawable.salsa),
    LACTEO("Lacteo", R.drawable.lacteo),
    HUEVOS("Huevos", R.drawable.huevo),
    OTRO("Otro", R.drawable.otro),
    BEBIDA("Bebida", R.drawable.bebida);

    private final String nombreCategoria;
    private final int imagenCategoria;

    // Constructor que acepta nombre y recurso de imagen
    ECategoria(String nombreCategoria, int imagenCategoria) {
        this.nombreCategoria = nombreCategoria;
        this.imagenCategoria = imagenCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public int getImagenCategoria() {
        return imagenCategoria;
    }
}

