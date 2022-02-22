package com.example.gy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gy.R;
import com.example.gy.model.Ejercicio;
import com.example.gy.util.GymSQliteHelper;

import java.util.ArrayList;

public class EjercicioDao {

    public ArrayList<Ejercicio> getEjercicios(Context contexto){
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ejercicio ",null );

        //Recorremos los resultados para mostrarlos en pantalla
        //Nos aseguramos de que existe al menos un registro
        Ejercicio ej = null;
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                int id = c.getInt(0);
                String imagen =c.getString(1);
                String nombre = c.getString(2);
                String descripcion = c.getString(2);

                ej = new Ejercicio();
                ej.setId(id);
                ej.setDescripcion(descripcion);

                //Resources res = contexto.getResources();
                //int resID = res.getIdentifier(imagen, "drawable", contexto.getPackageName());
                ej.setImagen(imagen);

                ej.setNombre(nombre);

                ejercicios.add(ej);
            }while (c.moveToNext());
        }

        return ejercicios;
    }

    public boolean crearEjercicio(Context contexto, Ejercicio ejercicio){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("imagen", ejercicio.getImagen());
        values.put("nombre", ejercicio.getNombre());
        values.put("descripcion", ejercicio.getDescripcion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("ejercicio", null, values);

        return true;
    }



}
