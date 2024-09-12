package com.example.inin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class daoEmpresa {

    private final SQLiteDatabase database;
    private final Context ct;
    private final String nombreBdEmpresa = "BDEmpresa";
    private final String CREAR_TABLA="create table if not exists empresa(idEmpresa integer primary key autoincrement, nombreEmpresa text, nifEmpresa text)";
    private final String SELECT_EMPRESA_NOMBRE ="select * from empresa where upper(nombreEmpresa) = upper(?)";
    private final String SELECT_EMPRESA_NIF ="select * from empresa where upper(nifEmpresa) = upper(?)";

    public daoEmpresa(Context context) {
        this.ct = context;
        database = context.openOrCreateDatabase(nombreBdEmpresa,Context.MODE_PRIVATE,null);
        database.execSQL(CREAR_TABLA);
    }

    public Empresa getEmpresaPorNombre(String nombreEmpresa) {
        Cursor cursor = database.rawQuery(SELECT_EMPRESA_NOMBRE, new String[]{nombreEmpresa});
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

    public Empresa getEmpresaPorNif(String nifEmpresa) {
        Cursor cursor = database.rawQuery(SELECT_EMPRESA_NIF, new String[]{nifEmpresa});
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

    public boolean addEmpresa(String nombreEmpresa, String nifEmpresa) {
        // Crear un ContentValues para almacenar los valores que se insertarán en la base de datos
        ContentValues values = new ContentValues();
        values.put("nombreEmpresa", nombreEmpresa);
        values.put("nifEmpresa", nifEmpresa);

        // Insertar la empresa en la base de datos
        long result = database.insert("empresa", null, values);

        // Comprobar si la inserción fue exitosa
        return result != -1; // Si result es -1, significa que la inserción falló
    }


}
