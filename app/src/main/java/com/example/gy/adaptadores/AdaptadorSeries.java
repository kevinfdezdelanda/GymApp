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
import com.example.gy.model.Serie;

import java.util.List;

public class AdaptadorSeries extends ArrayAdapter<Serie> {

    private List<Serie> series;

    public AdaptadorSeries(@NonNull Context context, List<Serie> Series) {
        super(context, R.layout.list_serie, Series);
        this.series = Series;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_serie, null);

        return (item);
    }
}
