package com.example.gy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gy.R;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Entrenamiento;
import com.example.gy.model.Serie;
import com.example.gy.util.GymSQliteHelper;

import java.util.ArrayList;
import java.util.Date;

public class SerieDao {

    private Context contexto;

    public SerieDao(Context contexto){
        this.contexto = contexto;
    }

    //Obtiene los datos de las series del entrenamiento
    public ArrayList<Serie> getSeriesEntrenamiento(Entrenamiento en){
        ArrayList<Serie> series = new ArrayList<>();

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT s.*" +
                "FROM `serie` s " +
                "WHERE s.id_entrenamiento = "+en.getId(),null );

        //Recorremos los resultados para mostrarlos en pantalla
        //Nos aseguramos de que existe al menos un registro
        Serie se = null;
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                int id = c.getInt(0);
                int num_serie = c.getInt(2);
                int reps = c.getInt(3);
                double peso = c.getDouble(4);

                se = new Serie();
                se.setId(id);
                se.setNumSerie(num_serie);
                se.setReps(reps);
                se.setPeso(peso);
                se.calcular1rm();

                series.add(se);
            }while (c.moveToNext());
        }

        return series;
    }

    //Guarda la serie
    public boolean guardarSerie(int idEntreno,Serie s){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("id_entrenamiento", idEntreno);
        values.put("num_serie", s.getNumSerie());
        values.put("reps", s.getReps());
        values.put("peso", s.getPeso());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("serie", null, values);

        if(newRowId == -1){
            return false;
        }else{
            return  true;
        }
    }
}
