package com.example.gy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.dao.EntrenamientoDao;
import com.example.gy.dao.SerieDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Entrenamiento;
import com.example.gy.model.Rutina;
import com.example.gy.model.Serie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntrenamientoEjercicio extends AppCompatActivity {

    private Ejercicio ejercicio;
    private ListView listSeries;
    private TextView txtRm1, txtRm2, txtRm3;
    private Entrenamiento entrenamiento;
    private EditText txtNota, txtPeso1, txtPeso2, txtPeso3, txtRepeticiones1, txtRepeticiones2, txtRepeticiones3;
    private ArrayList<EditText> txtPesos, txtRepeticiones;
    private ArrayList<TextView> txtRms;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Oculta la barra del titulo
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.entrenamiento_ejercicio);
        getSupportActionBar().hide();

        ejercicio = (Ejercicio) getIntent().getSerializableExtra("ejercicio");

        txtNota = (EditText) findViewById(R.id.txtNota);
        TextView nombreEj = (TextView) findViewById(R.id.txtNomEj);
        nombreEj.setText(ejercicio.getNombre());
        ImageView img = (ImageView)findViewById(R.id.imgEj);

        try{
            Resources res = getResources();
            int resID = res.getIdentifier(ejercicio.getImagen(), "drawable", getPackageName());
            img.setImageResource(resID);
        }catch (Exception e){

        }

        //Obtengo los datos del entreno anterior
        EntrenamientoDao ed = new EntrenamientoDao(EntrenamientoEjercicio.this);
        entrenamiento = ed.getUltimoEntrenamiento(ejercicio);
        if(entrenamiento!=null||entrenamiento.getNota().equals("")){
            txtNota.setHint(entrenamiento.getNota());
        }

        txtPeso1 = (EditText) findViewById(R.id.txtPeso1);
        txtPeso2 = (EditText) findViewById(R.id.txtPeso2);
        txtPeso3 = (EditText) findViewById(R.id.txtPeso3);

        txtRepeticiones1 = (EditText) findViewById(R.id.txtRepeticiones1);
        txtRepeticiones2 = (EditText) findViewById(R.id.txtRepeticiones2);
        txtRepeticiones3 = (EditText) findViewById(R.id.txtRepeticiones3);

        txtRm1 = (TextView) findViewById(R.id.txtRm1);
        txtRm2 = (TextView) findViewById(R.id.txtRm2);
        txtRm3 = (TextView) findViewById(R.id.txtRm3);

        //Obtengo los datos de las series del entreno anterior
        SerieDao sd = new SerieDao(EntrenamientoEjercicio.this);
        List<Serie> series = sd.getSeriesEntrenamiento(entrenamiento);

        // Cargo los datos por defecto en las series
        if(series.size()==0){
            series.add(new Serie(12,1,0,20));
            series.add(new Serie(12,2,0,20));
            series.add(new Serie(12,3,0,20));
        }

        txtPesos = new ArrayList<>();
        txtRepeticiones = new ArrayList<>();
        txtRms = new ArrayList<>();

        txtPesos.add(txtPeso1);
        txtPesos.add(txtPeso2);
        txtPesos.add(txtPeso3);

        txtRepeticiones.add(txtRepeticiones1);
        txtRepeticiones.add(txtRepeticiones2);
        txtRepeticiones.add(txtRepeticiones3);

        txtRms.add(txtRm1);
        txtRms.add(txtRm2);
        txtRms.add(txtRm3);

        // Cargo los datos del las series y añado los evento para calcular el 1RM
        for (int i = 0; i < series.size(); i++) {
            txtPesos.get(i).setHint(series.get(i).getPeso()+"");
            txtRepeticiones.get(i).setHint(series.get(i).getReps()+"");
            txtRms.get(i).setText(series.get(i).getRm()+"");

            int finalI = i;
            txtPesos.get(i).addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    calcRm(finalI);
               }

               @Override
               public void afterTextChanged(Editable editable) {

               }
            });

            txtRepeticiones.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    calcRm(finalI);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        //Obtengo los datos y guardo el entreno del ejercicio en un arrayList que se manda a la ventana del entreno
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(EntrenamientoEjercicio.this);
                dialogo1.setTitle("Finalizar Ejercicio");
                dialogo1.setMessage("¿Deseas guardar el Ejercicio?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Serie s1 = new Serie();
                        Serie s2 = new Serie();
                        Serie s3 = new Serie();

                        ArrayList<Serie> series = new ArrayList<>();
                        series.add(s1);
                        series.add(s2);
                        series.add(s3);

                        for (int i = 0; i < txtPesos.size(); i++) {
                            String strPeso = String.valueOf(txtPesos.get(i).getText());
                            String strReps = String.valueOf(txtRepeticiones.get(i).getText());

                            Double peso = 0.0;
                            if(strPeso.equals("")){
                                peso = Double.parseDouble(String.valueOf(txtPesos.get(i).getHint()));
                            }else{
                                peso = Double.parseDouble(strPeso);
                            }

                            int repeticiones = 0;
                            if(strReps.equals("")){
                                repeticiones = Integer.parseInt(String.valueOf(txtRepeticiones.get(i).getHint()));
                            }else{
                                repeticiones = Integer.parseInt(strReps);
                            }

                            series.get(i).setPeso(peso);
                            series.get(i).setReps(repeticiones);
                            series.get(i).calcular1rm();
                            series.get(i).setNumSerie((i+1));
                        }

                        Entrenamiento e = new Entrenamiento();
                        e.setNota(String.valueOf(txtNota.getText()));

                        long miliseconds = System.currentTimeMillis();
                        Date date = new Date(miliseconds);
                        e.setFecha(date);

                        e.setSeries(series);

                        e.setEjercicio(ejercicio);

                        ArrayList<Entrenamiento> entrenamientos = (ArrayList<Entrenamiento>) getIntent().getSerializableExtra("entrenamientos");
                        entrenamientos.add(e);

                        Intent i = new Intent(EntrenamientoEjercicio.this, EntrenamientoEmpezado.class);
                        i.putExtra("entrenamientos", (ArrayList<Entrenamiento>) entrenamientos);
                        i.putExtra("rutina", (Rutina) getIntent().getSerializableExtra("rutina"));
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

    //Metodo para calcular la 1RM a tiempo real
    public void calcRm(int i){
        Serie s = new Serie();

        String strPeso = String.valueOf(txtPesos.get(i).getText());
        String strReps = String.valueOf(txtRepeticiones.get(i).getText());

        Double peso = 0.0;
        if(strPeso.equals("")){
            peso = Double.parseDouble(String.valueOf(txtPesos.get(i).getHint()));
        }else{
            peso = Double.parseDouble(strPeso);
        }

        int repeticiones = 0;
        if(strReps.equals("")){
            repeticiones = Integer.parseInt(String.valueOf(txtRepeticiones.get(i).getHint()));
        }else{
            repeticiones = Integer.parseInt(strReps);
        }

        s.setPeso(peso);
        s.setReps(repeticiones);

        s.calcular1rm();

        txtRms.get(i).setText(s.getRm()+"");
    }


}