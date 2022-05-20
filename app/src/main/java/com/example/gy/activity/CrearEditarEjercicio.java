package com.example.gy.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.dao.EjercicioDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CrearEditarEjercicio extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    private Button btnSubirFoto, btnGuardarEjercicio;
    private EditText txtNombreEjercicio, txtDescripcionEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Oculta la barra del titulo
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.crear_editar_ejercicio);
        getSupportActionBar().hide();

        Ejercicio ejercicio = (Ejercicio) getIntent().getSerializableExtra("ejercicio");

        // Si se esta editando el ejercicio cambia el titulo de la ventana
        TextView txtTitulo = (TextView) this.findViewById(R.id.txtTitulo);
        if(ejercicio!=null){
            txtTitulo.setText("Editar Ejercicio");
        }

        //Abre la galeria para seleccionar una foto
        btnSubirFoto = (Button) this.findViewById(R.id.btnSubirFoto);
        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria(view);
            }
        });

        txtNombreEjercicio = (EditText) this.findViewById(R.id.txtNombreEjercicio);
        txtDescripcionEjercicio = (EditText) this.findViewById(R.id.txtDescripcionEjercicio);

        //Comprueba los datos y guarda o edita el ejercicio
        btnGuardarEjercicio = (Button) this.findViewById(R.id.btnGuardarEjercicio);
        btnGuardarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EjercicioDao rd = new EjercicioDao(com.example.gy.activity.CrearEditarEjercicio.this);

                String nombre = txtNombreEjercicio.getText().toString();
                String descripcion = txtDescripcionEjercicio.getText().toString();

                if(nombre.equals("")||descripcion.equals("")){
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            "Los campos nombre y descripcion no pueden estar vacios", Toast.LENGTH_SHORT);
                    toast1.show();
                }else{
                    Ejercicio r = new Ejercicio();
                    r.setNombre(nombre);
                    r.setDescripcion(descripcion);

                    if(ejercicio == null){
                        if(rd.crearEjercicio(r)){
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Ejercicio creado", Toast.LENGTH_SHORT);
                            toast1.show();
                        }else{
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Error al crear el ejercicio", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                    }else{
                        r.setId(ejercicio.getId());
                        if(rd.editarEjecicio(r)){
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Ejercicio editado", Toast.LENGTH_SHORT);
                            toast1.show();
                        }else{
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Error al editar el ejercicio", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                    }

                    finish();
                }


            }
        });

        // Si se esta editando el ejercicio se escriben sus datos en los campos
        if(ejercicio!=null){
            txtNombreEjercicio.setText(ejercicio.getNombre());
            txtDescripcionEjercicio.setText(ejercicio.getDescripcion());
        }
    }

    //Abre la galeria
    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }

    //Carga la imagen seleccionada
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath=selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            ImageView mImg = (ImageView) findViewById(R.id.imgSubidaEjercicio);
                            mImg.setImageBitmap(bmp);

                        }
                    }
                }
                break;
        }
    }

}