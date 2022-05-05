package com.example.gy.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios;
import com.example.gy.dao.EjercicioDao;
import com.example.gy.dao.RutinaDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;

public class GestionarEjerciciosRutina extends AppCompatActivity {

    private ListView listGestionarEjercicios;
    private Button btnCrearEjercicio, btnVolver;
    private AdaptadorEjercicios adaptadorEjercicios;
    private EjercicioDao ed;
    private Rutina rutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.gestionar_ejercicios_rutina);
        getSupportActionBar().hide();

        rutina = (Rutina) getIntent().getSerializableExtra("rutina");

        listGestionarEjercicios = findViewById(R.id.listEjercicios);
        btnCrearEjercicio = findViewById(R.id.btnCrearEjercicio);
        btnVolver = findViewById(R.id.btnVolver);

        ed = new EjercicioDao(this);
        adaptadorEjercicios =
                new AdaptadorEjercicios(this, ed.getEjerciciosRutina(rutina));
        listGestionarEjercicios.setAdapter(adaptadorEjercicios);

        btnCrearEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GestionarEjerciciosRutina.this, GestionarEjercicios.class);
                i.putExtra("rutina", rutina);
                startActivityForResult(i, 123456);
            }
        });

        listGestionarEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                System.out.println();
                Ejercicio ej = (Ejercicio) listGestionarEjercicios.getAdapter().getItem(position);
                RutinaDao rd = new RutinaDao(GestionarEjerciciosRutina.this);
                if(rd.quitarEjercicio(rutina, ej)){
                    adaptadorEjercicios.clear();
                    adaptadorEjercicios.addAll(ed.getEjerciciosRutina(rutina));
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        adaptadorEjercicios.clear();
        adaptadorEjercicios.addAll(ed.getEjerciciosRutina(rutina));
    }
}