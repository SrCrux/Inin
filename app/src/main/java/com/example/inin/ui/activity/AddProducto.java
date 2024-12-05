package com.example.inin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.inin.R;
import com.example.inin.data.controller.ProductoController;
import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.ECategoria;
import com.example.inin.data.model.Producto;
import com.example.inin.ui.fragments.Inventario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AddProducto extends AppCompatActivity {

    private AppDatabase bd;
    private Spinner spinnerCategoria;
    private ProductoDao productoDao;
    private ProductoController controller;
    private Button addProducto;
    private Button volver;
    private TextInputEditText textNombreProducto;
    private TextInputLayout textInputLayoutNombreProducto;
    private TextInputEditText textPrecioProducto;
    private TextInputLayout textInputLayoutPrecioProducto;
    private TextInputEditText textStockProducto;
    private TextInputLayout textInputLayoutStockProducto;
    private TextInputLayout textInputLayoutCategoriaProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);

        spinnerCategoria = findViewById(R.id.spinnerCategoriaProducto);
        addProducto = findViewById(R.id.addProductoBoton);
        volver = findViewById(R.id.volverBoton);
        textNombreProducto = findViewById(R.id.EditTextNombreProducto);
        textInputLayoutNombreProducto = findViewById(R.id.textInputLayoutNombreProducto);
        textPrecioProducto = findViewById(R.id.EditTextPrecioProducto);
        textInputLayoutPrecioProducto = findViewById(R.id.textInputLayoutPrecioProducto);
        textStockProducto = findViewById(R.id.EditTextStockProducto);
        textInputLayoutStockProducto = findViewById(R.id.textInputLayoutStockProducto);
        textInputLayoutCategoriaProducto = findViewById(R.id.textInputLayoutCategoriaProducto);

        bd = AppDatabase.getInstance(getApplicationContext());
        productoDao = bd.productoDao();
        controller = new ProductoController(productoDao);
        categoriasSpinner();

        cambiarColorPresionarBotonVolver();
        cambiarColorPresionarBotonAddProducto();
        pulsarBotonAddProducto();
        pulsarVolver();

    }


    private void categoriasSpinner() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<String> categorias = new ArrayList<>();
            categorias.add("Selecciona una categoría");
            for (ECategoria categoria : ECategoria.values()) {
                categorias.add(categoria.getNombreCategoria());
            }

            // Configurar el Spinner en el hilo principal
            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        R.layout.spinner, categorias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoria.setAdapter(adapter);
                spinnerCategoria.setSelection(0); // Esto selecciona "Selecciona una categoría" como valor predeterminado
            });
        });
    }

    private void pulsarBotonAddProducto() {
        addProducto.setOnClickListener(v -> {

            String nombreProducto = textNombreProducto.getText().toString().trim();
            String precioProductoString = textPrecioProducto.getText().toString().trim();
            String stockProductoString = textStockProducto.getText().toString().trim();
            int categoria = spinnerCategoria.getSelectedItemPosition();
            String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString().trim();
            Producto producto = new Producto();
            producto.setIdEmpresa(InicioSesionEmpresasActivity.empresaSesionActiva.getIdEmpresa());

            if(nombreProducto.isEmpty()){
                textInputLayoutNombreProducto.setError("Introduce un nombre");
            }else{
                textInputLayoutNombreProducto.setError(null);
                producto.setNombre(nombreProducto);
                if(precioProductoString.isEmpty()){
                    textInputLayoutPrecioProducto.setError("Introduce un precio");
                }else{
                    textInputLayoutPrecioProducto.setError(null);
                    Double precioProducto = Double.parseDouble(precioProductoString);
                    producto.setPrecio(precioProducto);
                    if(stockProductoString.isEmpty()){
                        textInputLayoutStockProducto.setError("Introduce un stock válido");
                    }else{
                        textInputLayoutStockProducto.setError(null);
                        int stockProducto = Integer.parseInt(stockProductoString);
                        producto.setStock(stockProducto);
                        if(categoria<=0){
                            textInputLayoutCategoriaProducto.setError("Categoría invalida");
                        }else{
                            producto.setCategoria(ECategoria.valueOf(categoriaSeleccionada.toUpperCase()));
                            ECategoria categoriaEnum = ECategoria.values()[categoria-1];
                            producto.setImagenProducto(categoriaEnum.getImagenCategoria());
                            controller.altaProducto(producto);
                            Toast.makeText(this, "Producto introducido con éxito", Toast.LENGTH_SHORT).show();
                            textNombreProducto.setText("");
                            textPrecioProducto.setText("");
                            textStockProducto.setText("");
                            spinnerCategoria.setSelection(0);
                        }
                    }
                }
            }

        });

    }

    private void pulsarVolver() {
        volver.setOnClickListener(v -> {
            Intent intent = new Intent(AddProducto.this, ActividadPrincipal.class);
            intent.putExtra("fragment", "inventario");
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonAddProducto() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        addProducto.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    addProducto.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    addProducto.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonVolver() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        volver.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    volver.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    volver.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }

}