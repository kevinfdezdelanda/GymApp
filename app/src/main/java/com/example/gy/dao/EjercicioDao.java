package com.example.gy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gy.R;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;
import com.example.gy.util.GymSQliteHelper;

import java.util.ArrayList;

public class EjercicioDao {

    private Context contexto;

    public EjercicioDao(Context contexto){
        this.contexto = contexto;
    }

    //Obtiene todos los ejercicios
    public ArrayList<Ejercicio> getEjercicios(){
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM ejercicio where visible = true",null );

        //Recorremos los resultados para mostrarlos en pantalla
        //Nos aseguramos de que existe al menos un registro
        Ejercicio ej = null;
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                int id = c.getInt(0);
                String imagen =c.getString(1);
                String nombre = c.getString(2);
                String descripcion = c.getString(3);

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

    //Obtiene los ejercicios de la rutina
    public ArrayList<Ejercicio> getEjerciciosRutina(Rutina rutina){
        ArrayList<Ejercicio> ejercicios = new ArrayList<>();

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT e.* FROM ejercicio e, rutina_ejercicio re where visible = true and e.id_ejercicio = re.id_ejercicio and re.id_rutina = " + rutina.getId(),null );

        //Recorremos los resultados para mostrarlos en pantalla
        //Nos aseguramos de que existe al menos un registro
        Ejercicio ej = null;
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                int id = c.getInt(0);
                String imagen =c.getString(1);
                String nombre = c.getString(2);
                String descripcion = c.getString(3);

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

    //Crea un nuevo ejercicio
    public boolean crearEjercicio(Ejercicio ejercicio){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("imagen", ejercicio.getImagen());
        values.put("nombre", ejercicio.getNombre());
        values.put("descripcion", ejercicio.getDescripcion());
        values.put("visible", true);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("ejercicio", null, values);

        if(newRowId != -1){
            return true;
        }else{
            return false;
        }
    }

    //Edita el ejercicio
    public boolean editarEjecicio(Ejercicio e){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nombre", e.getNombre()); //These Fields should be your String values of actual column names
        cv.put("descripcion", e.getDescripcion());

        boolean b =  db.update("ejercicio", cv, "id_ejercicio = "+e.getId(), null)>0;

        return b;
    }

    //Borra el ejercicio
    public boolean borrarEjercicio(Ejercicio e){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("visible", false); //These Fields should be your String values of actual column names

        boolean b =  db.update("ejercicio", cv, "id_ejercicio = "+e.getId(), null)>0;

        return b;
    }

}
