package com.example.gy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios;
import com.example.gy.dao.EjercicioDao;
import com.example.gy.dao.RutinaDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;

public class GestionarEjercicios extends AppCompatActivity {

    private ListView listEjercicios;
    private Button btnEditarEjercicio, btnCrearEjercicio, btnBorrarEjercicio, btnAniadirEjercicio;
    private AdaptadorEjercicios adaptadorEjercicios;
    private EjercicioDao ed;
    private Ejercicio ejSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Ocultar barra de titulo
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
        btnAniadirEjercicio = findViewById(R.id.btnAniadirEjercicio);

        if(rutina==null){
            btnAniadirEjercicio.setVisibility(View.INVISIBLE);
        }

        //Abre la ventana para crear el ejercicio
        btnCrearEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GestionarEjercicios.this, CrearEditarEjercicio.class);
                startActivityForResult(i, 12345);
            }
        });

        //habilita los botones de editar, borrar y añadir ejercicios
        listEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ejSeleccionado = (Ejercicio) listEjercicios.getAdapter().getItem(i);
                habilitarBotones();
            }
        });

        //Añade el ejercicio a la rutina
        btnAniadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println();

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(GestionarEjercicios.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Deseas Añadir el ejercicio " + ejSeleccionado.getNombre() + " a la rutina?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        RutinaDao rd = new RutinaDao(GestionarEjercicios.this);
                        rd.aniadirEjercicio(rutina, ejSeleccionado);
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "Ejercicio añadido", Toast.LENGTH_SHORT);
                        toast1.show();
                        finish();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                    }
                });
                dialogo1.show();
            }
        });

        //Abre la ventana para editar el ejercicio
        btnEditarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GestionarEjercicios.this, CrearEditarEjercicio.class);
                i.putExtra("ejercicio", (Ejercicio) ejSeleccionado);
                startActivityForResult(i,1234);
            }
        });

        //Pide confirmacion con un dialogo y borra el ejercicio
        btnBorrarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(GestionarEjercicios.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Deseas borrar el ejercicio " + ejSeleccionado.getNombre() + "?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        EjercicioDao re = new EjercicioDao(GestionarEjercicios.this);
                        if(re.borrarEjercicio(ejSeleccionado)){
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Ejercicio borrado", Toast.LENGTH_SHORT);
                            toast1.show();
                        }else{
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Error al borrar el ejercicio", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                        finish();
                        startActivity(getIntent());
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

    //habilita botones
    public void habilitarBotones(){
        btnBorrarEjercicio.setEnabled(true);
        btnEditarEjercicio.setEnabled(true);
        btnAniadirEjercicio.setEnabled(true);
    }

    //Despues de editar o crear los ejercicios recarga la lista
    protected void onActivityResult(int requestCode, int resultCode,
                                     Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        adaptadorEjercicios.clear();
        adaptadorEjercicios.addAll(ed.getEjercicios());
    }

}