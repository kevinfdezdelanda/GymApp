package com.example.gy.activity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios;
import com.example.gy.adaptadores.AdaptadorSeries;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;
import com.example.gy.model.Serie;

import java.util.ArrayList;
import java.util.List;

public class EntrenamientoEjercicio extends AppCompatActivity {

    private Ejercicio ejercicio;
    private ListView listSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.v_entrenamiento_ejercicio);
        getSupportActionBar().hide();

        ejercicio = (Ejercicio) getIntent().getSerializableExtra("ejercicio");

        TextView nombreEj = (TextView) findViewById(R.id.txtNomEj);
        nombreEj.setText(ejercicio.getNombre());
        ImageView img = (ImageView)findViewById(R.id.imgEj);

        try{
            Resources res = getResources();
            int resID = res.getIdentifier(ejercicio.getImagen(), "drawable", getPackageName());
            img.setImageResource(resID);
        }catch (Exception e){

        }

        listSeries = (ListView) findViewById(R.id.listSeries);
        AdaptadorSeries adaptadorSeries =
                new AdaptadorSeries(this, getSeries());
        listSeries.setAdapter(adaptadorSeries);
    }

    public List<Serie> getSeries(){
        List<Serie> series = new ArrayList<>();

        series.add(new Serie());
        series.add(new Serie());
        series.add(new Serie());

        return series;
    }


}