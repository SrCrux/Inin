package com.example.inin;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroEmpresasActivity extends AppCompatActivity {

    private Button registroEmpresaButton;
    private TextInputEditText registrarNombreEmpresa;
    private TextInputEditText registrarNifEmpresa;
    private TextInputLayout textInputLayoutNombreEmpresa;
    private TextInputLayout textInputLayoutNifEmpresa;
    private EmpresaDao empresaDao;
    private static String nombreEmpresa;
    private static String nifEmpresa;

    public static String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public static String getNifEmpresa() {
        return nifEmpresa;
    }


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

        registroEmpresaButton = findViewById(R.id.registroEmpresa_btn);
        registrarNombreEmpresa = findViewById(R.id.textInputEditTextRegistrarNombreEmpresa);
        registrarNifEmpresa = findViewById(R.id.textInputEditTextRegistrarNifEmpresa);
        textInputLayoutNombreEmpresa = findViewById(R.id.textInputLayoutRegistrarNombreEmpresa);
        textInputLayoutNifEmpresa = findViewById(R.id.textInputLayoutRegistrarNifEmpresa);
        empresaDao = new EmpresaDao(this);

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
                Empresa empresa = empresaDao.getEmpresaPorNombre(nombreEmpresa);
                if (empresa != null) {
                    textInputLayoutNombreEmpresa.setError("La empresa introducida ya existe.");
                } else {
                    textInputLayoutNombreEmpresa.setError(null);
                    if (nifEmpresa.length() != 9) {
                        textInputLayoutNifEmpresa.setError("El Nif debe tener 9 caracteres.");
                    } else {
                        textInputLayoutNifEmpresa.setError(null);
                        if (!nifEmpresa.matches(regexNif)) {
                            textInputLayoutNifEmpresa.setError("Nif Inválido Ej:(LNNNNNNNL / NNNNNNNNL).");
                        } else {
                            textInputLayoutNifEmpresa.setError(null);
                            Empresa empresaNif = empresaDao.getEmpresaPorNif(nifEmpresa);
                            if (empresaNif != null) {
                                textInputLayoutNifEmpresa.setError("Este NIF ya está en uso por otra empresa.");
                            } else {
                                textInputLayoutNifEmpresa.setError(null);
                                Intent i = new Intent(RegistroEmpresasActivity.this, SetPasswordAdministradorActivity.class);
                                startActivity(i);
                            }
                        }
                    }
                }
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