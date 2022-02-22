package com.example.gy.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios;
import com.example.gy.adaptadores.AdaptadorRutinas;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;

import java.util.ArrayList;
import java.util.List;

public class ComenzarEntrenamiento extends AppCompatActivity {

    private Rutina rutina;
    private ListView listEjercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.comenzar_entrenamiento);
        getSupportActionBar().hide();

        rutina = (Rutina) getIntent().getSerializableExtra("rutina");

        TextView nombre = (TextView) findViewById(R.id.nomRutina);
        nombre.setText(rutina.getNombre());
        ImageView img = (ImageView)findViewById(R.id.imgRutina);
        img.setImageResource(rutina.getImagen());

        listEjercicios = (ListView) findViewById(R.id.listRutina);
        AdaptadorEjercicios adaptadorEjercicios =
                new AdaptadorEjercicios(this, getEjercicios());
        listEjercicios.setAdapter(adaptadorEjercicios);


        listEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                System.out.println();
                Intent i = new Intent(ComenzarEntrenamiento.this, EntrenamientoEjercicio.class);
                i.putExtra("ejercicio", (Ejercicio) listEjercicios.getAdapter().getItem(position));
                startActivity(i);
            }
        });
    }

    public List<Ejercicio> getEjercicios(){
        List<Ejercicio> ejerciciosList = new ArrayList<>();

        ejerciciosList.add( new Ejercicio("pectoral","Press banca 1", "3x8", 1));
        ejerciciosList.add(new Ejercicio("pectoral","Press banca 2","3x8", 2));
        ejerciciosList.add(new Ejercicio("pectoral","Press banca 3","3x8", 3));
        ejerciciosList.add(new Ejercicio("pectoral","Press banca 4","3x8", 4));
        ejerciciosList.add(new Ejercicio("pectoral","Press banca 5","3x8", 5));

        return ejerciciosList;
    }


}