package com.example.inin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EmpresaDao {

    private final SQLiteDatabase database;
    private final String SELECT_EMPRESA_NOMBRE = "select * from empresa where upper(nombreEmpresa) = upper(?)";
    private final String SELECT_EMPRESA_NIF = "select * from empresa where upper(nifEmpresa) = upper(?)";

    public EmpresaDao(Context context) {
        database = DataBase.getDatabase();
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
        ContentValues values = new ContentValues();
        values.put("nombreEmpresa", nombreEmpresa);
        values.put("nifEmpresa", nifEmpresa);
        long result = database.insert("empresa", null, values);
        return result != -1;
    }
}
