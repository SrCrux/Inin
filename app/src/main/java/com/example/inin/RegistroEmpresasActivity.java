package com.example.inin;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RegistroEmpresasActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_empresas);

        EditText editText = findViewById(R.id.nombreEmpresaEditText);

        // Obtén el drawable del cursor
        Drawable cursorDrawable = ContextCompat.getDrawable(this, R.drawable.custom_cursor);

        // Establece el drawable del cursor
        if (cursorDrawable != null) {
            editText.setTextCursorDrawable(cursorDrawable);
        }
    }
}
