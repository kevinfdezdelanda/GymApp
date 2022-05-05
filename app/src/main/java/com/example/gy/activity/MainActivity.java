package com.example.gy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.gy.R;
import com.example.gy.adaptadores.AdaptadorEjercicios_RecyclerView;
import com.example.gy.adaptadores.AdaptadorRutinas;
import com.example.gy.dao.RutinaDao;
import com.example.gy.model.Ejercicio;
import com.example.gy.model.Rutina;
import com.example.gy.util.GymSQliteHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listRutinas;
    private RecyclerView listEjerciciosProgreso;
    private AdaptadorRutinas adaptadorRutinas;
    private RutinaDao rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Asignamos al ViewPager el PageAdapter
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new PageAdaprter());
        // Se asigna al TabLayout el ViewPager y configura el modo de las pestañas
        TabLayout tabLayout= findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        //Añadimos iconos a las pestañas
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dumbbell_solid);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_calendar_alt_solid);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_user_solid);

        GymSQliteHelper usdbh = new GymSQliteHelper(this, getString(R.string.bbdd)+"", null, 1);

        rd = new RutinaDao(MainActivity.this);
    }

    public List<Ejercicio> getEjerciciosProgreso(){
        List<Ejercicio> ejerciciosList = new ArrayList<>();

        ejerciciosList.add( new Ejercicio("pectoral","Dia pecho 1","", 1));
        ejerciciosList.add(new Ejercicio("pectoral","Dia pecho 2","", 2));
        ejerciciosList.add(new Ejercicio("pectoral","Dia pecho 3", "",3));
        ejerciciosList.add(new Ejercicio("pectoral","Dia pecho 4","", 4));
        ejerciciosList.add(new Ejercicio("pectoral","Dia pecho 5", "",5));

        return ejerciciosList;
    }

    class PageAdaprter extends PagerAdapter {
        private LinearLayout ventanaInicio;
        private LinearLayout ventanaProgreso;
        private LinearLayout ventanaUsuario;

        private final int[] pestanas ={R.string.tab1, R.string.tab2,
                R.string.tab3 };
        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container,
                                      int position) {
            View page;
            switch (position){
                case 0:
                    if (ventanaInicio == null){
                        ventanaInicio = (LinearLayout)
                                LayoutInflater.from(MainActivity.this)
                                        .inflate(R.layout.v_inicio, container,false );
                    }
                    page= ventanaInicio;

                    listRutinas = (ListView) ventanaInicio.findViewById(R.id.listEjercicios);
                    adaptadorRutinas =
                            new AdaptadorRutinas(ventanaInicio.getContext(), rd.getRutinas());
                    listRutinas.setAdapter(adaptadorRutinas);

                    listRutinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            System.out.println();
                            Intent i = new Intent(ventanaInicio.getContext(), ComenzarEntrenamiento.class);
                            i.putExtra("rutina", (Rutina) listRutinas.getAdapter().getItem(position));
                            startActivity(i);
                        }
                    });

                    Button btnNuevaRutina = (Button) ventanaInicio.findViewById(R.id.btnNuevaRutina);
                    btnNuevaRutina.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(ventanaInicio.getContext(), CrearEditarRutina.class);
                            i.putExtra("rutina", (Rutina) null);
                            startActivityForResult(i,1234);
                        }
                    });


                    break;
                case 1:
                    if (ventanaProgreso == null) {
                        ventanaProgreso = (LinearLayout)
                                LayoutInflater.from(MainActivity.this)
                                        .inflate(R.layout.v_progreso,container,false);
                    }
                    page= ventanaProgreso;

                    listEjerciciosProgreso = (RecyclerView) ventanaProgreso.findViewById(R.id.listEjerciciosEvolucion);
                    listEjerciciosProgreso.setLayoutManager(new GridLayoutManager(ventanaProgreso.getContext(), 3));
                    AdaptadorEjercicios_RecyclerView adaptadorEjrecicios =
                            new AdaptadorEjercicios_RecyclerView(getEjerciciosProgreso(), ventanaProgreso.getContext());
                    listEjerciciosProgreso.setAdapter(adaptadorEjrecicios);

                    break;
                default:
                    if (ventanaUsuario == null) {
                        ventanaUsuario = (LinearLayout)
                                LayoutInflater.from(MainActivity.this)
                                        .inflate(R.layout.v_usuario,container,false);
                    }
                    page= ventanaUsuario;
                    break;
            }
            container.addView(page, 0);
            return page;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view,
                                        @NonNull Object object) {
            //return false;
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container,
                                int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        adaptadorRutinas.clear();
        adaptadorRutinas.addAll(rd.getRutinas());
    }
}