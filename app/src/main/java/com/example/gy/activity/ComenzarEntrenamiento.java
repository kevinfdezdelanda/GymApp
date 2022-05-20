package com.example.gy.activity;

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
import com.example.gy.dao.RutinaDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Entrenamiento;
import com.example.gy.model.Rutina;

public class ComenzarEntrenamiento extends AppCompatActivity {

    private Rutina rutina;
    private ListView listEjercicios;
    private Button gestionarEjercicios, editarRutina, borrarRutina, btnComenzarEntrenamiento;
    private  TextView nombre;
    private AdaptadorEjercicios adaptadorEjercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ocultar barra titulo
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
        adaptadorEjercicios =
                new AdaptadorEjercicios(this, ed.getEjerciciosRutina(rutina));
        listEjercicios.setAdapter(adaptadorEjercicios);

        //Abre la ventana gestionar Ejercicios
        gestionarEjercicios = (Button) findViewById(R.id.gestionarEjercicios);
        gestionarEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ComenzarEntrenamiento.this, GestionarEjerciciosRutina.class);
                i.putExtra("rutina", (Rutina) rutina);
                startActivityForResult(i, 1234);
            }
        });

        //Abre la ventana de editar la rutina
        editarRutina = (Button) findViewById(R.id.btnEditarRutina);
        editarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ComenzarEntrenamiento.this, CrearEditarRutina.class);
                i.putExtra("rutina", (Rutina) rutina);
                startActivityForResult(i,1234);
            }
        });

        //Abre un dialogo de confirmacion y borra la rutina
        borrarRutina = (Button) findViewById(R.id.btnBorrarRutina);
        borrarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ComenzarEntrenamiento.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Deseas borrar la rutina " + rutina.getNombre() + "?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        RutinaDao rd = new RutinaDao(ComenzarEntrenamiento.this);
                        if(rd.borrarRutina(rutina)){
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Rutina borrada", Toast.LENGTH_SHORT);
                            toast1.show();
                        }else{
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Error al borrar la rutina", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                        Intent i = new Intent(ComenzarEntrenamiento.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });

        //Abre un dialogo de confirmacion y abre la ventana de entrenamiento
        btnComenzarEntrenamiento = (Button) findViewById(R.id.btnComenzarEntreno);
        btnComenzarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ComenzarEntrenamiento.this);
                dialogo1.setTitle("Comenzar Entrenamiento");
                dialogo1.setMessage("¿Deseas comenzar el entrenamiento?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Intent i = new Intent(ComenzarEntrenamiento.this, EntrenamientoEmpezado.class);
                        i.putExtra("rutina", (Rutina) rutina);
                        i.putExtra("entrenamientos", (Entrenamiento) null);
                        startActivity(i);
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

    //Cuando vuelva de editar la rutina o gestionar los ejercicios y recarga los datos de la rutina y los ejercicios
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        RutinaDao rd = new RutinaDao(this);
        Rutina r = rd.getRutina(rutina);
        rutina = r;
        nombre.setText(rutina.getNombre());

        EjercicioDao ejercicioDao = new EjercicioDao(this);
        adaptadorEjercicios.clear();
        adaptadorEjercicios.addAll(ejercicioDao.getEjerciciosRutina(rutina));
    }

}