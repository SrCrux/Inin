package com.example.inin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataBase {

    private static SQLiteDatabase database;
    private static final String nombreBdEmpresa = "Inin";
    private static final String CREAR_TABLA_EMPRESA = "create table if not exists empresa(idEmpresa integer primary key autoincrement, nombreEmpresa text, nifEmpresa text)";
    private static final String CREAR_TABLA_USUARIO = "create table if not exists usuario(idUsuario integer primary key autoincrement, nombreUsuario text, passwordUsuario text, pinUsuario text, admin boolean,idEmpresa integer, foreign key(idEmpresa) references empresa(idEmpresa))";

    public static void openOrCreateDatabase(Context context) {

        database = context.openOrCreateDatabase(nombreBdEmpresa, Context.MODE_PRIVATE, null);
        database.execSQL(CREAR_TABLA_EMPRESA);
        database.execSQL(CREAR_TABLA_USUARIO);

    }

    public static SQLiteDatabase getDatabase() {

        return database;
    }

}
