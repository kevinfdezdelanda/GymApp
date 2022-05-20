package com.example.gy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gy.R;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Entrenamiento;
import com.example.gy.model.Rutina;
import com.example.gy.model.Serie;
import com.example.gy.util.GymSQliteHelper;

import java.util.ArrayList;

public class EntrenamientoDao {

    private Context contexto;

    public EntrenamientoDao(Context contexto){
        this.contexto = contexto;
    }

    //Obtiene el ultimo entrenamiento
    public Entrenamiento getUltimoEntrenamiento(Ejercicio ej){

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM `entrenamiento` " +
                "WHERE id_ejercicio = "+ej.getId()+
                " ORDER BY fecha DESC LIMIT 1;",null );

        //Recorremos los resultados para mostrarlos en pantalla
        //Nos aseguramos de que existe al menos un registro
        Entrenamiento en  = new Entrenamiento();
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                int id = c.getInt(0);
                String nota = c.getString(2);

                en.setNota(nota);
                en.setId(id);
                en.setEjercicio(ej);
            }while (c.moveToNext());
        }

        return en;
    }

    //Guarda el entrenamiento
    public boolean guardarEntrenamiento(ArrayList<Entrenamiento> entrenamientos){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        for (int i = 0; i < entrenamientos.size(); i++) {
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put("id_ejercicio", entrenamientos.get(i).getEjercicio().getId());
            values.put("nota", entrenamientos.get(i).getNota());
            java.sql.Date sqlDate = new java.sql.Date(entrenamientos.get(i).getFecha().getTime());
            values.put("fecha", String.valueOf(sqlDate));

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert("entrenamiento", null, values);

            if(newRowId == -1){
                return false;
            }else{
                for (int j = 0; j < entrenamientos.get(i).getSeries().size(); j++) {
                    SerieDao sd = new SerieDao(contexto);
                    if(!sd.guardarSerie((int) newRowId, entrenamientos.get(i).getSeries().get(j))){
                        return false;
                    }
                }

            }
        }

        return true;
    }
}
