package com.example.gy.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GymSQliteHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreateEjercicio =
            "CREATE TABLE 'ejercicio' (\n" +
                    "  'id_ejercicio'  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  'imagen' varchar(50),\n" +
                    "  'nombre' varchar(25) NOT NULL,\n" +
                    "  'descripcion' varchar(50) NOT NULL\n" +
                    ")";

    String sqlCreateEntrenamiento =
            "CREATE TABLE 'entrenamiento' (\n" +
                    "  'id_entreno' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  'id_ejercicio' int(11) NOT NULL,\n" +
                    "  'nota' varchar(50) NOT NULL,\n" +
                    "  'fecha' date NOT NULL\n" +
                    ") ";

    String sqlCreateRutina = "CREATE TABLE 'rutina' (\n" +
            "  'id_rutina' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  'nombre' varchar(25) NOT NULL,\n" +
            "  'descripcion' varchar(50) NOT NULL,\n" +
            "  'imagen' varchar(50)\n" +
            ")";

    String sqlCreateSerie = "CREATE TABLE 'serie' (\n" +
            "  'id_serie' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  'id_entrenamiento' int(11) NOT NULL,\n" +
            "  'num_serie' int(11) NOT NULL,\n" +
            "  'reps' int(11) NOT NULL,\n" +
            "  'peso' double NOT NULL\n" +
            ")";


    public GymSQliteHelper(Context contexto, String nombre,
                           SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreateEjercicio);
        db.execSQL(sqlCreateEntrenamiento);
        db.execSQL(sqlCreateRutina);
        db.execSQL(sqlCreateSerie);
        db.execSQL("COMMIT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
