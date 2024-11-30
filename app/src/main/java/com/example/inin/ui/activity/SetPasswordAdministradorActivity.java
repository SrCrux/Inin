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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.inin.R;
import com.example.inin.data.controller.EmpresaController;
import com.example.inin.data.controller.UsuarioController;
import com.example.inin.data.dao.EmpresaDao;
import com.example.inin.data.dao.UsuarioDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Empresa;
import com.example.inin.data.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SetPasswordAdministradorActivity extends AppCompatActivity {

    private Button establecerPassword;
    private TextInputEditText passwordText;
    private TextInputLayout passwordLayout;
    private EmpresaDao empresaDao;
    private UsuarioDao usuarioDao;
    private AppDatabase bd;
    private EmpresaController empresaController;
    private UsuarioController usuarioController;

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

        bd = AppDatabase.getInstance(getApplicationContext());
        empresaDao = bd.empresaDao();
        usuarioDao = bd.usuarioDao();
        empresaController = new EmpresaController(empresaDao);
        usuarioController = new UsuarioController(usuarioDao);
        establecerPassword = findViewById(R.id.establecerPassword_btn);
        passwordText = findViewById(R.id.textInputEditTextPasswordAdministrador);
        passwordLayout = findViewById(R.id.textInputLayoutPasswordAdministrador);

        limpiarErrorPassword();
        pulsarBotonEstablecerPassword();
        cambiarColorPresionarBotonEstablecerPassword();
    }

    public void pulsarBotonEstablecerPassword() {
        establecerPassword.setOnClickListener(v -> {
            String nombreEmpresa = getIntent().getStringExtra("nombreEmpresa");
            String nifEmpresa = getIntent().getStringExtra("nifEmpresa");
            String password = passwordText.getText().toString().trim();
            String nombreUsuario = "Administrador";
            int imagenDefault = R.mipmap.hamburguesa_foreground;
            Empresa empresa = new Empresa(nombreEmpresa, nifEmpresa);

            if (password.length() < 8) {
                passwordLayout.setError("La contraseña debe tener al menos 8 caracteres.");
            } else {
                passwordLayout.setError(null);

                // Usar un Executor para realizar la inserción de la empresa en un hilo secundario
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    long idEmpresa = empresaController.altaEmpresa(empresa);
                    InicioSesionEmpresasActivity.empresaSesionActiva = empresa;

                    if (idEmpresa > 0) {
                        // Después de insertar la empresa, verificamos si el usuario administrador ya existe
                        runOnUiThread(() -> verificarYCrearUsuario(idEmpresa, nombreUsuario, password, imagenDefault));
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(v.getContext(), "Error al registrar la empresa", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });
    }

    // Método para verificar si el usuario administrador ya existe
    private void verificarYCrearUsuario(long idEmpresa, String nombreUsuario, String password, int imagenDefault) {
        usuarioController.buscarUsuarioPorNombre(nombreUsuario, (int) idEmpresa).observe(this, usuarioExistente -> {
            if (usuarioExistente == null) {
                Usuario administrador = new Usuario((int) idEmpresa, true, nombreUsuario, password, imagenDefault);
                Log.d("UsuariosActivity", "Insertando usuario: " + administrador.getNombreUsuario());
                usuarioController.altaUsuario(administrador);
                Log.d("UsuariosActivity", "Usuarios insertados: " + usuarioController.listarUsuariosPorEmpresa((int) idEmpresa).getValue());

                // Mostrar el mensaje de éxito
                Toast.makeText(this, "Registro realizado con éxito", Toast.LENGTH_SHORT).show();

                // Redirigir a la siguiente actividad
                Intent i = new Intent(SetPasswordAdministradorActivity.this, UsuariosActivity.class);
                i.putExtra("idEmpresa", idEmpresa);  // Usamos el id de la empresa insertada
                startActivity(i);
            }
            usuarioController.buscarUsuarioPorNombre(nombreUsuario, (int) idEmpresa).removeObservers(this);
        });
    }

    public void limpiarErrorPassword() {
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
