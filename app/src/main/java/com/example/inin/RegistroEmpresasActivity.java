package com.example.inin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
    private daoEmpresa daoEmpresa;

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


        daoEmpresa = new daoEmpresa(this);

        pulsarBotonRegistrarseEmpresa();
        cambiarColorPresionarBotonRegistrarseEmpresa();

    }

    public void pulsarBotonRegistrarseEmpresa() {

        registroEmpresaButton.setOnClickListener(v -> {
            String nombreEmpresaString = registrarNombreEmpresa.getText().toString().trim();
            String nifEmpresaString = registrarNifEmpresa.getText().toString().trim();
            String regexNif = "^(\\d{8}[A-Z])|([A-Z]\\d{7}[A-Z])$";
            boolean addEmpresa;

            if (nombreEmpresaString.isEmpty()) {
                textInputLayoutNombreEmpresa.setError("El campo es obligatorio.");
            } else {
                textInputLayoutNombreEmpresa.setError(null);
                Empresa empresa = daoEmpresa.getEmpresaPorNombre(nombreEmpresaString);
                if (empresa != null) {
                    textInputLayoutNombreEmpresa.setError("La empresa introducida ya existe.");
                } else {
                    textInputLayoutNombreEmpresa.setError(null);
                    if (nifEmpresaString.length() != 9) {
                        textInputLayoutNifEmpresa.setError("El Nif debe tener 9 caracteres.");
                    } else {
                        textInputLayoutNifEmpresa.setError(null);
                        if (!nifEmpresaString.matches(regexNif)) {
                            textInputLayoutNifEmpresa.setError("Nif Inválido Ej:(LNNNNNNNL / NNNNNNNNL).");
                        } else {
                            textInputLayoutNifEmpresa.setError(null);
                            Empresa empresaNif = daoEmpresa.getEmpresaPorNif(nifEmpresaString);
                            if (empresaNif != null) {
                                textInputLayoutNifEmpresa.setError("Este NIF ya está en uso por otra empresa.");
                            } else {
                                textInputLayoutNifEmpresa.setError(null);
                                addEmpresa = daoEmpresa.addEmpresa(nombreEmpresaString, nifEmpresaString);
                                if (!addEmpresa) {
                                    textInputLayoutNifEmpresa.setError("Error del sistema al introducir la empresa.");
                                } else {
                                    textInputLayoutNifEmpresa.setError(null);
                                    Toast.makeText(v.getContext(), "Registro realizado con exito", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(RegistroEmpresasActivity.this, RegistroUsuariosActivity.class);
                                    startActivity(i);
                                }
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