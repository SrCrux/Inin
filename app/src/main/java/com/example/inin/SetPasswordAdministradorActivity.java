package com.example.inin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class SetPasswordAdministradorActivity extends AppCompatActivity {

    private Button establecerPassword;
    private TextInputEditText passwordText;
    private TextInputLayout passwordLayout;
    private EmpresaDao empresaDao;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_password_administrador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        establecerPassword = findViewById(R.id.establecerPassword_btn);
        passwordText = findViewById(R.id.textInputEditTextPasswordAdministrador);
        passwordLayout = findViewById(R.id.textInputLayoutPasswordAdministrador);
        empresaDao = new EmpresaDao(this);
        usuarioDao = new UsuarioDao(this);

        limpiarErrorPassword();
        pulsarBotonEstablecerPassword();
        cambiarColorPresionarBotonEstablecerPassword();


    }
public void pulsarBotonEstablecerPassword(){

        establecerPassword.setOnClickListener(v -> {
            String nombreEmpresa = RegistroEmpresasActivity.getNombreEmpresa();
            String nifEmpresa = RegistroEmpresasActivity.getNifEmpresa();
            String password = passwordText.getText().toString().trim();
            String nombreUsuario = "Administrador";
            int imagenDefault = R.mipmap.hamburguesa_foreground;
            boolean addEmpresa;
            boolean addUsuario;

            if (password.length()<8){
                passwordLayout.setError("La contraseña debe tener al menos 8 caracteres.");
            }else{
                passwordLayout.setError(null);
                addEmpresa = empresaDao.addEmpresa(nombreEmpresa, nifEmpresa);
                InicioSesionEmpresasActivity.empresaSesionActiva = empresaDao.getEmpresaPorNombre(nombreEmpresa);
                addUsuario = usuarioDao.addUsuario(nombreUsuario,password,null,true,imagenDefault,InicioSesionEmpresasActivity.empresaSesionActiva.getIdEmpresa());
                if (!addEmpresa || !addUsuario){
                    passwordLayout.setError("Error en el sistema al registrar empresa.");
                }else {
                    Toast.makeText(v.getContext(), "Registro realizado con exito", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SetPasswordAdministradorActivity.this, UsuariosActivity.class);
                    startActivity(i);
                }
            }
    });
}

public void limpiarErrorPassword(){

        passwordText.setOnClickListener(v -> {

            passwordLayout.setError(null);

    });

}

    @SuppressLint("ClickableViewAccessibility")
    public void cambiarColorPresionarBotonEstablecerPassword() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        establecerPassword.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    establecerPassword.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    establecerPassword.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }


}