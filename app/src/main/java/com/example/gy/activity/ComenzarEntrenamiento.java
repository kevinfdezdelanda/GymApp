package com.example.gy.activity;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios;
import com.example.gy.dao.EjercicioDao;
import com.example.gy.dao.RutinaDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;

public class ComenzarEntrenamiento extends AppCompatActivity {

    private Rutina rutina;
    private ListView listEjercicios;
    private Button gestionarEjercicios, editarRutina, borrarRutina;
    private  TextView nombre;

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

        nombre = (TextView) findViewById(R.id.nomRutina);
        nombre.setText(rutina.getNombre());
        ImageView img = (ImageView) findViewById(R.id.imgRutina);

        Resources res = getResources();
        try {
            int resID = res.getIdentifier(rutina.getImagen(), "drawable", this.getPackageName());
            img.setImageResource(resID);
        }catch (Exception e){

        }

        listEjercicios = (ListView) findViewById(R.id.listEjercicios);

        EjercicioDao ed = new EjercicioDao(this);
        AdaptadorEjercicios adaptadorEjercicios =
                new AdaptadorEjercicios(this, ed.getEjerciciosRutina(rutina));
        listEjercicios.setAdapter(adaptadorEjercicios);


        listEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                System.out.println();
                Intent i = new Intent(ComenzarEntrenamiento.this, EntrenamientoEjercicio.class);
                i.putExtra("ejercicio", (Ejercicio) listEjercicios.getAdapter().getItem(position));
                startActivityForResult(i, 1234);
            }
        });

        gestionarEjercicios = (Button) findViewById(R.id.gestionarEjercicios);
        gestionarEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ComenzarEntrenamiento.this, GestionarEjerciciosRutina.class);
                i.putExtra("rutina", (Rutina) rutina);
                startActivityForResult(i, 1234);
            }
        });

        editarRutina = (Button) findViewById(R.id.btnEditarRutina);
        editarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ComenzarEntrenamiento.this, CrearEditarRutina.class);
                i.putExtra("rutina", (Rutina) rutina);
                startActivityForResult(i,1234);
            }
        });

        borrarRutina = (Button) findViewById(R.id.btnBorrarRutina);
        borrarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        RutinaDao rd = new RutinaDao(this);
        Rutina r = rd.getRutina(rutina);
        rutina = r;
        nombre.setText(rutina.getNombre());
    }

}