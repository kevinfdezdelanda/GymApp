package com.example.gy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios;
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
        setContentView(R.layout.comenzar_entrenamiento);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        rutina = (Rutina) getIntent().getSerializableExtra("rutina");

        TextView nombre = (TextView) findViewById(R.id.nomRutina);
        nombre.setText(rutina.getNombre());
        ImageView img = (ImageView)findViewById(R.id.imgRutina);
        img.setImageResource(rutina.getImagen());

        ListView listEjercicios = (ListView) findViewById(R.id.listEjercicios);
        AdaptadorEjercicios adaptadorEjercicios =
                new AdaptadorEjercicios(this, getEjercicios());
        listEjercicios.setAdapter(adaptadorEjercicios);
    }

    public List<Ejercicio> getEjercicios(){
        List<Ejercicio> ejerciciosList = new ArrayList<>();

        ejerciciosList.add( new Ejercicio(R.drawable.pectoral,"Press banca 1", "3x8", 1));
        ejerciciosList.add(new Ejercicio(R.drawable.pectoral,"Press banca 2","3x8", 2));
        ejerciciosList.add(new Ejercicio(R.drawable.pectoral,"Press banca 3","3x8", 3));
        ejerciciosList.add(new Ejercicio(R.drawable.pectoral,"Press banca 4","3x8", 4));
        ejerciciosList.add(new Ejercicio(R.drawable.pectoral,"Press banca 5","3x8", 5));

        return ejerciciosList;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}