package com.example.gy.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gy.R;
import com.example.gy.model.Ejercicio;

import java.util.List;

public class AdaptadorEjercicios_RecyclerView extends RecyclerView.Adapter<AdaptadorEjercicios_RecyclerView.AdaptadorEjercicios_RecyclerViewVh> {

    private List<Ejercicio> EjercicioList;
    private Context context;

    public AdaptadorEjercicios_RecyclerView(List<Ejercicio> EjercicioList, Context context) {
        this.EjercicioList = EjercicioList;
    }

    @NonNull
    @Override
    public AdaptadorEjercicios_RecyclerView.AdaptadorEjercicios_RecyclerViewVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new AdaptadorEjercicios_RecyclerViewVh(LayoutInflater.from(context).inflate(R.layout.list_ejercicio,null));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEjercicios_RecyclerView.AdaptadorEjercicios_RecyclerViewVh holder, int position) {

        Ejercicio ejercicio = EjercicioList.get(position);

        String nombre = ejercicio.getNombre();

        holder.tvNombre.setText(nombre);
        holder.imIcon.setImageResource(ejercicio.getImagen());

    }

    @Override
    public int getItemCount() {
        return EjercicioList.size();
    }

    public class AdaptadorEjercicios_RecyclerViewVh extends RecyclerView.ViewHolder {

        TextView tvNombre;
        ImageView imIcon;
        public AdaptadorEjercicios_RecyclerViewVh(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.nomEjercicio);
            imIcon = itemView.findViewById(R.id.imgEjercicio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }
    }
}