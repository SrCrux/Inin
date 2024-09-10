package com.example.inin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class InicioSesionEmpresasActivity extends AppCompatActivity {

    Button botonIniciarSesionEmpresas;
    Button botonRegistrarEmpresas;
    TextInputEditText textNombreEmpresa;
    TextInputEditText textNifEmpresa;
    TextInputLayout textInputLayoutNombreEmpresa;
    TextInputLayout textInputLayoutNifEmpresa;
    daoEmpresa daoEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciosesion_empresas);

        botonIniciarSesionEmpresas = findViewById(R.id.iniciarSesion_button);
        botonRegistrarEmpresas = findViewById(R.id.registrarEmpresa_button);
        textNombreEmpresa = findViewById(R.id.nombreEmpresaEditText);
        textNifEmpresa = findViewById(R.id.nifEmpresaEditText);
        textInputLayoutNombreEmpresa = findViewById(R.id.textInputLayoutNombreEmpresa);
        textInputLayoutNifEmpresa = findViewById(R.id.textInputLayoutNifEmpresa);

        daoEmpresa = new daoEmpresa(this);

        cambiarColorPresionarBotonIniciarSesionEmpresas();
        cambiarColorPresionarBotonRegistrarEmpresas();
        pulsarBotonIniciarSesionEmpresas();
        pulsarBotonRegistrarEmpresas();


    }


    public void pulsarBotonIniciarSesionEmpresas() {

        botonIniciarSesionEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreEmpresaString = String.valueOf(textNombreEmpresa.getText());
                String nifEmpresaString = String.valueOf(textNifEmpresa.getText());
                Empresa empresa = daoEmpresa.getEmpresaPorNombre(nombreEmpresaString);

                if (empresa == null) {
                    textInputLayoutNombreEmpresa.setError("La empresa introducida no es válida.");
                } else {
                    textInputLayoutNombreEmpresa.setError(null);
                    if (!empresa.getNifEmpresa().equals(nifEmpresaString)) {
                        textInputLayoutNifEmpresa.setError("Nif inválido");
                    } else {
                        textInputLayoutNombreEmpresa.setError(null);
                        Intent i = new Intent(InicioSesionEmpresasActivity.this, RegistroUsuariosActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
    }

    public void pulsarBotonRegistrarEmpresas() {

        botonRegistrarEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InicioSesionEmpresasActivity.this, RegistroEmpresasActivity.class);
                startActivity(i);
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    public void cambiarColorPresionarBotonIniciarSesionEmpresas() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        botonIniciarSesionEmpresas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
            }

        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void cambiarColorPresionarBotonRegistrarEmpresas() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        botonRegistrarEmpresas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
            }

        });
    }
}



