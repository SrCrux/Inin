package com.example.inin.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.R;
import com.example.inin.data.controller.UsuarioController;
import com.example.inin.data.dao.EmpresaDao;
import com.example.inin.data.dao.UsuarioDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Usuario;
import com.example.inin.ui.adapter.RecyclerViewUsuarioAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsuariosActivity extends AppCompatActivity {

    private long botonRetrocederTiempo;
    private static final int INTERVALO_TIEMPO_SALIR = 2000;
    private UsuarioDao usuarioDao;
    private EmpresaDao empresaDao;
    private UsuarioController usuarioController;
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
        AppDatabase bd = AppDatabase.getInstance(getApplicationContext());
        empresaDao = bd.empresaDao();
        usuarioDao = bd.usuarioDao();
        usuarioController = new UsuarioController(usuarioDao);
        int idEmpresa = getIntent().getIntExtra("idEmpresa",-1);
        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usuarioController.listarUsuariosPorEmpresa(idEmpresa).observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                RecyclerViewUsuarioAdapter adapter = new RecyclerViewUsuarioAdapter(UsuariosActivity.this, usuarios);
                recyclerView.setAdapter(adapter);
            }
        });

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