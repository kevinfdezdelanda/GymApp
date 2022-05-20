package com.example.gy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gy.R;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;
import com.example.gy.util.GymSQliteHelper;

import java.util.ArrayList;

public class RutinaDao {

    private Context contexto;

    public RutinaDao(Context contexto){
        this.contexto = contexto;
    }

    //Obtiene todas las rutinas
    public ArrayList<Rutina> getRutinas(){
        ArrayList<Rutina> rutinas = new ArrayList<>();

        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM rutina where visible = true",null );

        //Recorremos los resultados para mostrarlos en pantalla
        //Nos aseguramos de que existe al menos un registro
        Rutina rutina = null;
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.
            do {
                int id = c.getInt(0);
                String descripcion =c.getString(2);
                String nombre = c.getString(1);
                String imagen = c.getString(3);

                rutina = new Rutina();
                rutina.setId(id);
                rutina.setDescripcion(descripcion);

                //Resources res = contexto.getResources();
                //int resID = res.getIdentifier(imagen, "drawable", contexto.getPackageName());
                rutina.setImagen(imagen);

                rutina.setNombre(nombre);

                rutinas.add(rutina);
            }while (c.moveToNext());
        }

        return rutinas;
    }

    //Obtiene los datos de la rutina pasada como argumento
    public Rutina getRutina(Rutina r){
        //Abrimos la base de datos "DBUsuarios" en modo de escritura
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM rutina where id_rutina = "+r.getId(),null );

        //Recorremos los resultados para mostrarlos en pantalla
        //Nos aseguramos de que existe al menos un registro
        Rutina rutina = null;
        if (c.moveToFirst()){
            //Recorremos el cursor hasta que no haya más registros.

            int id = c.getInt(0);
            String descripcion =c.getString(2);
            String nombre = c.getString(1);
            String imagen = c.getString(3);

            rutina = new Rutina();
            rutina.setId(id);
            rutina.setDescripcion(descripcion);

            //Resources res = contexto.getResources();
            //int resID = res.getIdentifier(imagen, "drawable", contexto.getPackageName());
            rutina.setImagen(imagen);

            rutina.setNombre(nombre);


        }
        return rutina;

    }

    //Crea una nueva rutina
    public boolean crearRutina(Rutina rutina){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("imagen", rutina.getImagen());
        values.put("nombre", rutina.getNombre());
        values.put("descripcion", rutina.getDescripcion());
        values.put("visible",true);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("Rutina", null, values);

        if(newRowId != -1){
            return true;
        }else{
            return false;
        }

    }

    //Añade el ejercicio a la rutina
    public boolean aniadirEjercicio(Rutina r, Ejercicio e){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("id_rutina", r.getId());
        values.put("id_ejercicio", e.getId());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("rutina_ejercicio", null, values);

        if(newRowId != -1){
            return true;
        }else{
            return false;
        }
    }

    //Quita el ejercicio de la rutina
    public boolean quitarEjercicio(Rutina r, Ejercicio e){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        // Insert the new row, returning the primary key value of the new row
        return db.delete("rutina_ejercicio", "id_rutina" + "=" + r.getId() + " and " + "id_ejercicio" + "=" + e.getId(), null) > 0;
    }

    //Edita la rutina
    public boolean editarRutina(Rutina r){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nombre", r.getNombre()); //These Fields should be your String values of actual column names
        cv.put("descripcion", r.getDescripcion());

        boolean b =  db.update("rutina", cv, "id_rutina = "+r.getId(), null)>0;

        return b;
    }

    //Borra la rutina
    public boolean borrarRutina(Rutina r){
        GymSQliteHelper usdbh =
                new GymSQliteHelper(contexto, contexto.getString(R.string.bbdd), null, 1);

        // Gets the data repository in write mode
        SQLiteDatabase db = usdbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("visible", false); //These Fields should be your String values of actual column names

        boolean b =  db.update("rutina", cv, "id_rutina = "+r.getId(), null)>0;

        return b;
    }
}
