package com.example.inin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity {

    private long botonRetrocederTiempo;
    private static final int INTERVALO_TIEMPO_SALIR = 2000;
    private ArrayList<Usuario> listaUsuarios;
    private UsuarioDao usuarioDao;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usuarioDao = new UsuarioDao(this);
        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        listaUsuarios = usuarioDao.getListaUsuarioPorNombreEmpresa(InicioSesionEmpresasActivity.empresaSesionActiva.getNombreEmpresa());
        RecyclerViewUsuarioAdapter adapter = new RecyclerViewUsuarioAdapter(this, listaUsuarios);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        // Si el tiempo desde la última pulsación es menor al intervalo, salir de la aplicación

        if (botonRetrocederTiempo + INTERVALO_TIEMPO_SALIR > System.currentTimeMillis()) {
            // Salir de la aplicación
            super.onBackPressed();
            ActivityCompat.finishAffinity(this);
        } else {
            // Si no, mostrar un mensaje al usuario
            Toast.makeText(this, "Presiona nuevamente para salir", Toast.LENGTH_SHORT).show();
        }

        botonRetrocederTiempo = System.currentTimeMillis();
    }

}