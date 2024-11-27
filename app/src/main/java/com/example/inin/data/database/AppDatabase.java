package com.example.inin.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.inin.data.dao.EmpresaDao;
import com.example.inin.data.dao.PedidoDao;
import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.dao.ProductoPedidoDao;
import com.example.inin.data.dao.UsuarioDao;
import com.example.inin.data.model.Empresa;
import com.example.inin.data.model.Pedido;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;
import com.example.inin.data.model.Usuario;

@Database(entities = {Empresa.class, Usuario.class, Producto.class, Pedido.class, ProductoPedido.class}, version = 1, exportSchema = false)
@TypeConverters(Convertidor.class)
public abstract class AppDatabase extends RoomDatabase{

public abstract EmpresaDao empresaDao();
public abstract UsuarioDao usuarioDao();
public abstract ProductoDao productoDao();
public abstract PedidoDao pedidoDao();
public abstract ProductoPedidoDao productoPedidoDao();

private static AppDatabase INSTANCE;

public static synchronized AppDatabase getInstance(Context context){

    if (INSTANCE == null){
        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "inindb").fallbackToDestructiveMigration().build();
    }
    return INSTANCE;
}

}
