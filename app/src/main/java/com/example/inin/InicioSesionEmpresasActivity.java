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

public class InicioSesionEmpresasActivity extends AppCompatActivity {

    Button botonIniciarSesionEmpresas;
    Button botonRegistrarEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciosesion_empresas);

        botonIniciarSesionEmpresas = findViewById(R.id.iniciarSesion_button);
        botonRegistrarEmpresas = findViewById(R.id.registrarEmpresa_button);

        cambiarColorPresionarBotonIniciarSesionEmpresas();
        cambiarColorPresionarBotonRegistrarEmpresas();
        pulsarBotonIniciarSesionEmpresas();
        pulsarBotonRegistrarEmpresas();


    }


    public void pulsarBotonIniciarSesionEmpresas() {

        botonIniciarSesionEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InicioSesionEmpresasActivity.this, RegistroUsuariosActivity.class);
                startActivity(i);
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

        int colorPresionado = ContextCompat.getColor(this,R.color.colorPrimaryWhite);
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

        int colorPresionado = ContextCompat.getColor(this,R.color.colorPrimaryWhite);
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



