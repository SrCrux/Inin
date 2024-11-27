package com.example.inin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.inin.R;
import com.example.inin.data.controller.EmpresaController;
import com.example.inin.data.dao.EmpresaDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Empresa;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class InicioSesionEmpresasActivity extends AppCompatActivity {

    private Button botonIniciarSesionEmpresas;
    private Button botonRegistrarEmpresas;
    private TextInputEditText textNombreEmpresa;
    private TextInputEditText textNifEmpresa;
    private TextInputLayout textInputLayoutNombreEmpresa;
    private TextInputLayout textInputLayoutNifEmpresa;
    private EmpresaDao empresaDao;
    private EmpresaController controller;
    private static final int INTERVALO_TIEMPO_SALIR = 2000;
    private long botonRetrocederTiempo;
    public static Empresa empresaSesionActiva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciosesion_empresas);

        AppDatabase bd = AppDatabase.getInstance(getApplicationContext());
        empresaDao = bd.empresaDao();
        controller = new EmpresaController(empresaDao);
        botonIniciarSesionEmpresas = findViewById(R.id.iniciarSesion_button);
        botonRegistrarEmpresas = findViewById(R.id.registrarEmpresa_button);
        textNombreEmpresa = findViewById(R.id.nombreEmpresaEditText);
        textNifEmpresa = findViewById(R.id.nifEmpresaEditText);
        textInputLayoutNombreEmpresa = findViewById(R.id.textInputLayoutNombreEmpresa);
        textInputLayoutNifEmpresa = findViewById(R.id.textInputLayoutNifEmpresa);

        cambiarColorPresionarBotonIniciarSesionEmpresas();
        cambiarColorPresionarBotonRegistrarEmpresas();
        pulsarBotonIniciarSesionEmpresas();
        pulsarBotonRegistrarEmpresas();


    }

    @Override
    public void onBackPressed() {
        // Si el tiempo desde la última pulsación es menor al intervalo, salir de la aplicación
        if (botonRetrocederTiempo + INTERVALO_TIEMPO_SALIR > System.currentTimeMillis()) {
            // Salir de la aplicación
            super.onBackPressed();
            return;
        } else {
            // Si no, mostrar un mensaje al usuario
            Toast.makeText(this, "Presiona nuevamente para salir", Toast.LENGTH_SHORT).show();
        }

        botonRetrocederTiempo = System.currentTimeMillis();
    }

    public void pulsarBotonIniciarSesionEmpresas() {

        botonIniciarSesionEmpresas.setOnClickListener(v -> {
            String nombreEmpresa = textNombreEmpresa.getText().toString().trim();
            String nifEmpresa = textNifEmpresa.getText().toString().trim();
            String regexNif = "^(\\d{8}[A-Z])|([A-Z]\\d{7}[A-Z])$";

            if (nombreEmpresa.isEmpty()) {
                textInputLayoutNombreEmpresa.setError("El campo es obligatorio.");
            } else {
                textInputLayoutNombreEmpresa.setError(null);
                controller.buscarEmpresaPorNombre(nombreEmpresa).observe(this, empresa -> {
                    if (empresa == null) {
                        textInputLayoutNombreEmpresa.setError("La empresa introducida no existe.");
                    } else {
                        if (!nifEmpresa.matches(regexNif)) {
                            textInputLayoutNifEmpresa.setError("Nif Inválido Ej:(LNNNNNNNL / NNNNNNNNL).");
                        } else {
                            textInputLayoutNifEmpresa.setError(null);
                            if (!empresa.getNif().equals(nifEmpresa)) {
                                textInputLayoutNifEmpresa.setError("El Nif introducido no es válido.");
                            } else {
                                textInputLayoutNombreEmpresa.setError(null);
                                textInputLayoutNifEmpresa.setError(null);
                                empresaSesionActiva = empresa;
                                Intent i = new Intent(InicioSesionEmpresasActivity.this, UsuariosActivity.class);
                                i.putExtra("idEmpresa",empresa.getIdEmpresa());
                                startActivity(i);
                                textNombreEmpresa.setText("");
                                textNifEmpresa.setText("");
                            }
                        }
                    }
                });
            }
        });
    }


    public void pulsarBotonRegistrarEmpresas() {

        botonRegistrarEmpresas.setOnClickListener(v -> {
            Intent i = new Intent(InicioSesionEmpresasActivity.this, RegistroEmpresasActivity.class);
            startActivity(i);
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    public void cambiarColorPresionarBotonIniciarSesionEmpresas() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        botonIniciarSesionEmpresas.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    botonIniciarSesionEmpresas.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    botonIniciarSesionEmpresas.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void cambiarColorPresionarBotonRegistrarEmpresas() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        botonRegistrarEmpresas.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    botonRegistrarEmpresas.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    botonRegistrarEmpresas.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }

}



