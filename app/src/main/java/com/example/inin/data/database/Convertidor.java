package com.example.inin.data.database;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
/**
 * Clase que convierte la fecha del pedido a un String y viceversa para que ROOM pueda leerlo.
 */
public class Convertidor {

    public static final DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @TypeConverter
    public static LocalDate deString(String fecha){
        if (fecha == null){
            return null;
        }
        return LocalDate.parse(fecha,formateador);
    }

    @TypeConverter
    public static String deLocalDate(LocalDate fecha) {
        if (fecha == null) {
            return null;
        }
        return fecha.format(formateador);
    }
}
