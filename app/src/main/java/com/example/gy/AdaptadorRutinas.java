package com.example.gy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class AdaptadorRutinas extends ArrayAdapter<Rutina> {

    private Rutina[] rutinas;

    public AdaptadorRutinas(@NonNull Context context, Rutina[] rutinas) {
        super(context, R.layout.list_rutina_layout, rutinas);
        this.rutinas = rutinas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_rutina_layout, null);
        TextView nombre = (TextView)item.findViewById(R.id.nombre);
        nombre.setText(rutinas[position].getNombre());
        TextView descripcion = (TextView)item.findViewById(R.id.descripcion);
        descripcion.setText(rutinas[position].getDescripcion());
        ImageView img = (ImageView)item.findViewById(R.id.img);
        img.setImageDrawable(rutinas[position].getImagen().getDrawable());
        return (item);
    }
}
