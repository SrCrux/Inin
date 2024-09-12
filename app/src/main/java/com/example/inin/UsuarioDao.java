package com.example.inin;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDao {

    private final SQLiteDatabase database;


    public UsuarioDao(Context context) {
        database = DataBase.getDatabase();
    }

    public boolean addUsuario(String nombreUsuario, String passwordUsuario, String pinUsuario, boolean admin, int idEmpresa ){

        ContentValues values = new ContentValues();
        values.put("nombreUsuario", nombreUsuario);
        values.put("passwordUsuario", passwordUsuario);
        values.put("pinUsuario", pinUsuario);
        values.put("admin", admin);
        values.put("idEmpresa",idEmpresa);
        long result = database.insert("usuario",null,values);
        return result !=-1;
    }

}
