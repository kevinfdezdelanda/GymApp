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

public class GestionarEjercicios extends AppCompatActivity {

    private ListView listEjercicios;
    private Button btnEditarEjercicio, btnCrearEjercicio, btnBorrarEjercicio;
    private AdaptadorEjercicios adaptadorEjercicios;
    private EjercicioDao ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.gestionar_ejercicios);
        getSupportActionBar().hide();

        listEjercicios = findViewById(R.id.listEjercicios);

        Rutina rutina = (Rutina) getIntent().getSerializableExtra("rutina");

        ed = new EjercicioDao(this);
        adaptadorEjercicios =
                new AdaptadorEjercicios(this, ed.getEjercicios());
        listEjercicios.setAdapter(adaptadorEjercicios);

        btnEditarEjercicio = findViewById(R.id.editarEjercicio);
        btnCrearEjercicio = findViewById(R.id.crearEjecicio);
        btnBorrarEjercicio = findViewById(R.id.borrarEjercicio);

        btnCrearEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GestionarEjercicios.this, CrearEditarEjercicio.class);
                startActivityForResult(i, 12345);
            }
        });

        listEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                System.out.println();
                Ejercicio ej = (Ejercicio) listEjercicios.getAdapter().getItem(position);
                RutinaDao rd = new RutinaDao(GestionarEjercicios.this);
                rd.aniadirEjercicio(rutina, ej);
                finish();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                     Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        adaptadorEjercicios.clear();
        adaptadorEjercicios.addAll(ed.getEjercicios());
    }

}