package com.example.inin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class UsuarioDao {

    private final SQLiteDatabase database;
    private final String SELECT_USUARIO_NOMBRE = "select nombreUsuario, imagenUsuario from usuario where idEmpresa = (select idEmpresa from empresa where nombreEmpresa = ?)";


    public UsuarioDao(Context context) {
        database = DataBase.getDatabase();
    }

    public ArrayList<Usuario> getListaUsuarioPorNombreEmpresa(String nombreEmpresa) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(SELECT_USUARIO_NOMBRE, new String[]{nombreEmpresa});

            if (cursor.moveToFirst()) {
                do {
                    String nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"));
                    int imagenUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("imagenUsuario"));
                    Usuario usuario = new Usuario(nombreUsuario, imagenUsuario);
                    listaUsuarios.add(usuario);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaUsuarios;
    }


    public boolean addUsuario(String nombreUsuario, String passwordUsuario, String pinUsuario, boolean admin, int imagenUsuario, int idEmpresa) {

        ContentValues values = new ContentValues();
        values.put("nombreUsuario", nombreUsuario);
        values.put("passwordUsuario", passwordUsuario);
        values.put("pinUsuario", pinUsuario);
        values.put("admin", admin);
        values.put("imagenUsuario", imagenUsuario);
        values.put("idEmpresa", idEmpresa);
        long result = database.insert("usuario", null, values);
        return result != -1;
    }

}
