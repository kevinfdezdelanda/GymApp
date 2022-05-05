package com.example.gy.adaptadores;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gy.R;
import com.example.gy.model.Rutina;

import java.util.ArrayList;

public class AdaptadorRutinas extends ArrayAdapter<Rutina> {

    private ArrayList<Rutina> rutinas;
    private Context context;

    public AdaptadorRutinas(@NonNull Context context, ArrayList<Rutina> rutinas) {
        super(context, R.layout.list_rutina_ejercicio_layout, rutinas);
        this.rutinas = rutinas;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_rutina_ejercicio_layout, null);
        TextView nombre = (TextView)item.findViewById(R.id.nombre);
        nombre.setText(rutinas.get(position).getNombre());
        TextView descripcion = (TextView)item.findViewById(R.id.descripcion);
        descripcion.setText(rutinas.get(position).getDescripcion());
        ImageView img = (ImageView)item.findViewById(R.id.imgSubidaEjercicio);

        Resources res = context.getResources();
        try {
            int resID = res.getIdentifier(rutinas.get(position).getImagen(), "drawable", context.getPackageName());
            img.setImageResource(resID);
        }catch (Exception e){

        }
        return (item);
    }
}
