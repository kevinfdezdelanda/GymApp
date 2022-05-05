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

import androidx.appcompat.app.AppCompatActivity;

import com.example.gy.R;
import com.example.gy.dao.RutinaDao;
import com.example.gy.model.Rutina;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CrearEditarRutina extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    private Button btnSubirFoto, btnGuardarRutina;
    private EditText txtNombreRutina, txtDescripcionRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.crear_editar_rutina);
        getSupportActionBar().hide();

        Rutina rutina = (Rutina) getIntent().getSerializableExtra("rutina");

        btnSubirFoto = (Button) this.findViewById(R.id.btnSubirFoto);
        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria(view);
            }
        });

        txtNombreRutina = (EditText) this.findViewById(R.id.txtNombreEjercicio);
        txtDescripcionRutina = (EditText) this.findViewById(R.id.txtDescripcionEjercicio);

        btnGuardarRutina = (Button) this.findViewById(R.id.btnGuardarEjercicio);
        btnGuardarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RutinaDao rd = new RutinaDao(CrearEditarRutina.this);

                String nombre = txtNombreRutina.getText().toString();
                String descripcion = txtDescripcionRutina.getText().toString();

                if(nombre.equals("")||descripcion.equals("")){

                }else{
                    Rutina r = new Rutina();
                    r.setNombre(nombre);
                    r.setDescripcion(descripcion);

                    if(rutina == null){
                        rd.crearRutina(r);
                    }else{
                        r.setId(rutina.getId());
                        rd.editarRutina(r);
                    }

                    finish();
                }


            }
        });

        if(rutina!=null){
            txtNombreRutina.setText(rutina.getNombre());
            txtDescripcionRutina.setText(rutina.getDescripcion());
        }
    }

    public void abrirGaleria(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }

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