package com.example.inin.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.inin.R;
import com.example.inin.ui.fragments.Pedidos;
import com.example.inin.ui.fragments.Inventario;
import com.example.inin.ui.fragments.Caducidad;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ActividadPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        BottomNavigationView bottomNavigationView = findViewById(R.id.barraNavegacion);

        String fragmento = getIntent().getStringExtra("fragment");
        if ("pedidos".equals(fragmento)) {
            bottomNavigationView.setSelectedItemId(R.id.pedidos); // Cambia el item seleccionado
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, new Pedidos())
                    .commit();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.inventario); // Carga el fragmento por defecto
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, new Inventario())
                    .commit();
        }
        bottomNavigationView.setOnItemSelectedListener(navListener);
    }

    private NavigationBarView.OnItemSelectedListener navListener = item -> {
        int itemId = item.getItemId();
        Fragment fragmentoSeleccionado = null;

        if (itemId == R.id.inventario) {
            fragmentoSeleccionado = new Inventario();
        } else if (itemId == R.id.pedidos) {
            fragmentoSeleccionado = new Pedidos();
        } else if (itemId == R.id.caducidades) {
            fragmentoSeleccionado = new Caducidad();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragmentoSeleccionado).commit();
        return true;
    };
}