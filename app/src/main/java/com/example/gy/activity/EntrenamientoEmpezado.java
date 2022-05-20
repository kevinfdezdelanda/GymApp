package com.example.gy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios;
import com.example.gy.dao.EjercicioDao;
import com.example.gy.dao.EntrenamientoDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Entrenamiento;
import com.example.gy.model.Rutina;
import com.example.gy.model.Serie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

public class EntrenamientoEmpezado extends AppCompatActivity {

    private Rutina rutina;
    private ListView listEjercicios;
    private TextView nombre;
    private Button btnFinalizar;
    private ArrayList<Entrenamiento> entrenamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Oculta la barra de titulo
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.entrenamiento_empezado);
        getSupportActionBar().hide();

        //Obtengo los datos de los ejercicios ya realizados y si no tengo datos del entrenamiento instancio el ArrayList
        rutina = (Rutina) getIntent().getSerializableExtra("rutina");
        entrenamientos = (ArrayList<Entrenamiento>) getIntent().getSerializableExtra("entrenamientos");
        if (entrenamientos == null) {
            entrenamientos = new ArrayList<>();
        }

        nombre = (TextView) findViewById(R.id.nomRutina);
        nombre.setText(rutina.getNombre());
        ImageView img = (ImageView) findViewById(R.id.imgRutina);

        Resources res = getResources();
        try {
            int resID = res.getIdentifier(rutina.getImagen(), "drawable", this.getPackageName());
            img.setImageResource(resID);
        } catch (Exception ex) {

        }

        listEjercicios = (ListView) findViewById(R.id.listEjercicios);

        // Obtengo los ejercicios de la rutina
        EjercicioDao ed = new EjercicioDao(this);
        AdaptadorEjercicios adaptadorEjercicios =
                new AdaptadorEjercicios(this, ed.getEjerciciosRutina(rutina));
        listEjercicios.setAdapter(adaptadorEjercicios);

        //Abre la ventana de entrenamiento del ejercicio
        listEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                System.out.println();
                Intent i = new Intent(EntrenamientoEmpezado.this, EntrenamientoEjercicio.class);
                i.putExtra("ejercicio", (Ejercicio) listEjercicios.getAdapter().getItem(position));
                i.putExtra("rutina", (Rutina) rutina);
                i.putExtra("entrenamientos", (ArrayList<Entrenamiento>) entrenamientos);
                startActivityForResult(i, 1234);
            }


        });

        //Guarda el entrenamiento en la BBDD
        btnFinalizar = (Button) findViewById(R.id.btnFinalizarEntreno);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EntrenamientoEmpezado.this);
                dialogo1.setTitle("Finalizar Entrenamiento");
                dialogo1.setMessage("¿Deseas finalizar el Entrenamiento?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        if (entrenamientos != null) {
                            EntrenamientoDao ed = new EntrenamientoDao(EntrenamientoEmpezado.this);
                            boolean correcto = ed.guardarEntrenamiento(entrenamientos);
                            if (correcto) {
                                Toast toast1 = Toast.makeText(getApplicationContext(),
                                        "Entrenamiento guardado", Toast.LENGTH_SHORT);
                                toast1.show();
                            } else {
                                Toast toast1 = Toast.makeText(getApplicationContext(),
                                        "Error al guardar el entrenamiento", Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                            Intent i = new Intent(EntrenamientoEmpezado.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();


            }
        });


    }
}