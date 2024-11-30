package com.example.inin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.inin.R;
import com.example.inin.data.controller.EmpresaController;
import com.example.inin.data.dao.EmpresaDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Empresa;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroEmpresasActivity extends AppCompatActivity {

    private Button registroEmpresaButton;
    private TextInputEditText registrarNombreEmpresa;
    private TextInputEditText registrarNifEmpresa;
    private TextInputLayout textInputLayoutNombreEmpresa;
    private TextInputLayout textInputLayoutNifEmpresa;
    private EmpresaDao empresaDao;
    private EmpresaController empresaController;
    private String nombreEmpresa;
    private String nifEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_empresas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase bd = AppDatabase.getInstance(getApplicationContext());
        empresaDao = bd.empresaDao();
        empresaController = new EmpresaController(empresaDao);
        registroEmpresaButton = findViewById(R.id.registroEmpresa_btn);
        registrarNombreEmpresa = findViewById(R.id.textInputEditTextRegistrarNombreEmpresa);
        registrarNifEmpresa = findViewById(R.id.textInputEditTextRegistrarNifEmpresa);
        textInputLayoutNombreEmpresa = findViewById(R.id.textInputLayoutRegistrarNombreEmpresa);
        textInputLayoutNifEmpresa = findViewById(R.id.textInputLayoutRegistrarNifEmpresa);

        pulsarBotonRegistrarseEmpresa();
        cambiarColorPresionarBotonRegistrarseEmpresa();

    }

    public void pulsarBotonRegistrarseEmpresa() {

        registroEmpresaButton.setOnClickListener(v -> {
            nombreEmpresa = registrarNombreEmpresa.getText().toString().trim();
            nifEmpresa = registrarNifEmpresa.getText().toString().trim();
            String regexNif = "^(\\d{8}[A-Z])|([A-Z]\\d{7}[A-Z])$";

            if (nombreEmpresa.isEmpty()) {
                textInputLayoutNombreEmpresa.setError("El campo es obligatorio.");
            } else {
                textInputLayoutNombreEmpresa.setError(null);
                // Se observa el LiveData de buscarEmpresaPorNombre
                empresaController.buscarEmpresaPorNombre(nombreEmpresa).observe(this, empresa -> {
                    if (empresa != null) {
                        textInputLayoutNombreEmpresa.setError("El nombre de la empresa introducida ya existe.");
                    } else {
                        textInputLayoutNombreEmpresa.setError(null);
                        // Validación del NIF
                        if (nifEmpresa.length() != 9) {
                            textInputLayoutNifEmpresa.setError("El Nif debe tener 9 caracteres.");
                        } else {
                            textInputLayoutNifEmpresa.setError(null);
                            if (!nifEmpresa.matches(regexNif)) {
                                textInputLayoutNifEmpresa.setError("Nif Inválido Ej:(LNNNNNNNL / NNNNNNNNL).");
                            } else {
                                textInputLayoutNifEmpresa.setError(null);
                                // Se observa el LiveData de buscarEmpresaPorNif
                                empresaController.buscarEmpresaPorNif(nifEmpresa).observe(this, empresaNif -> {
                                    if (empresaNif != null) {
                                        textInputLayoutNifEmpresa.setError("Este NIF ya está en uso por otra empresa.");
                                    } else {
                                        textInputLayoutNifEmpresa.setError(null);
                                        // Si no hay conflictos, redirigimos a la siguiente actividad
                                        Intent i = new Intent(RegistroEmpresasActivity.this, SetPasswordAdministradorActivity.class);
                                        i.putExtra("nombreEmpresa", nombreEmpresa);
                                        i.putExtra("nifEmpresa", nifEmpresa);
                                        startActivity(i);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    public void cambiarColorPresionarBotonRegistrarseEmpresa() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        registroEmpresaButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    registroEmpresaButton.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    registroEmpresaButton.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }
}