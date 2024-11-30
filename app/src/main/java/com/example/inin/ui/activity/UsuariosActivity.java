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
import com.example.inin.data.dao.UsuarioDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Usuario;
import com.example.inin.ui.adapter.RecyclerViewUsuarioAdapter;

import java.util.List;

public class UsuariosActivity extends AppCompatActivity {

    private long botonRetrocederTiempo;
    private static final int INTERVALO_TIEMPO_SALIR = 2000;
    private UsuarioDao usuarioDao;
    private UsuarioController usuarioController;
    private RecyclerView recyclerView;
    private RecyclerViewUsuarioAdapter adapter; // Mantenemos el adaptador

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

        // Inicialización de la base de datos y el controlador
        AppDatabase bd = AppDatabase.getInstance(getApplicationContext());
        usuarioDao = bd.usuarioDao();
        usuarioController = new UsuarioController(usuarioDao);

        // Obtener el id de la empresa desde el Intent
        long idEmpresa = getIntent().getLongExtra("idEmpresa", -1);

        // Configuración del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configuramos el adaptador una sola vez
        adapter = new RecyclerViewUsuarioAdapter(UsuariosActivity.this, null);
        recyclerView.setAdapter(adapter);

        // Observador para actualizar el RecyclerView cuando los datos cambian
        usuarioController.listarUsuariosPorEmpresa((int) idEmpresa).observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                if (usuarios != null && !usuarios.isEmpty()) {
                    Log.d("UsuariosActivity", "Usuarios actualizados: " + usuarios.size());
                    adapter.updateUsuarios(usuarios);
                } else {
                    Log.d("UsuariosActivity", "No se encontraron usuarios.");
                }
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
