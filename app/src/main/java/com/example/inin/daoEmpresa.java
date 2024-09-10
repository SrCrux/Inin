package com.example.inin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class daoEmpresa {

    private SQLiteDatabase cx;
    private ArrayList<Empresa> listaEmpresa;
    private Empresa empresa;
    private Context ct;
    private String nombreBdEmpresa = "BDEmpresa";
    private final String CREAR_TABLA="create table if not exists empresa(idEmpresa integer primary key autoincrement, nombreEmpresa text, nifEmpresa text)";
    private final String SELECT_EMPRESA_NOMBRE ="select * from empresa where nombreEmpresa = ?";

    public daoEmpresa(Context context) {
        this.ct = context;
        cx = context.openOrCreateDatabase(nombreBdEmpresa,Context.MODE_PRIVATE,null);
        cx.execSQL(CREAR_TABLA);
    }

    public Empresa getEmpresaPorNombre(String nombreEmpresa) {
        Cursor cursor = cx.rawQuery(SELECT_EMPRESA_NOMBRE, new String[]{nombreEmpresa});
        Empresa empresaConsultada = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idEmpresa = cursor.getInt(cursor.getColumnIndexOrThrow("idEmpresa"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombreEmpresa"));
                String nif = cursor.getString(cursor.getColumnIndexOrThrow("nifEmpresa"));

                empresaConsultada = new Empresa(idEmpresa, nombre, nif);
            }
            cursor.close();
        }
        return empresaConsultada;
    }
}
