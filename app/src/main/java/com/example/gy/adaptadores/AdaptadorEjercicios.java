package com.example.gy.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gy.R;
import com.example.gy.model.Ejercicio;

import java.util.List;

public class AdaptadorEjercicios extends ArrayAdapter<Ejercicio> {

    private List<Ejercicio> ejercicios;

    public AdaptadorEjercicios(@NonNull Context context, List<Ejercicio> Ejercicios) {
        super(context, R.layout.list_rutina_ejercicio_layout, Ejercicios);
        this.ejercicios = Ejercicios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_rutina_ejercicio_layout, null);
        TextView nombre = (TextView)item.findViewById(R.id.nombre);
        nombre.setText(ejercicios.get(position).getNombre());
        TextView descripcion = (TextView)item.findViewById(R.id.descripcion);
        descripcion.setText(ejercicios.get(position).getDescripcion());
        ImageView img = (ImageView)item.findViewById(R.id.img);
        img.setImageResource(ejercicios.get(position).getImagen());
        return (item);
    }
}
