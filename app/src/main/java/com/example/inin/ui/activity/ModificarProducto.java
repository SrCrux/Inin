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
import com.example.inin.ui.adapter.RecyclerViewProductoAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ModificarProducto extends AppCompatActivity {

    private AppDatabase bd;
    private Spinner spinnerCategoria;
    private ProductoDao productoDao;
    private ProductoController controller;
    private Button modificarProducto;
    private Button volver;
    private TextInputEditText textNombreProductoModificar;
    private TextInputLayout textInputLayoutNombreProductoModificar;
    private TextInputEditText textPrecioProductoModificar;
    private TextInputLayout textInputLayoutPrecioProductoModificar;
    private TextInputEditText textStockProductoModificar;
    private TextInputLayout textInputLayoutStockProductoModificar;
    private TextInputLayout textInputLayoutCategoriaProductoModificar;
    private Producto productoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);

        productoSeleccionado = (Producto) getIntent().getSerializableExtra("producto") ;
        spinnerCategoria = findViewById(R.id.spinnerCategoriaProductoModificar);
        modificarProducto = findViewById(R.id.modificarProductoBoton);
        volver = findViewById(R.id.volverBotonModificar);
        textNombreProductoModificar = findViewById(R.id.EditTextNombreProductoModificar);
        textInputLayoutNombreProductoModificar = findViewById(R.id.textInputLayoutNombreProductoModificar);
        textPrecioProductoModificar = findViewById(R.id.EditTextPrecioProductoModificar);
        textInputLayoutPrecioProductoModificar = findViewById(R.id.textInputLayoutPrecioProductoModificar);
        textStockProductoModificar = findViewById(R.id.EditTextStockProductoModificar);
        textInputLayoutStockProductoModificar = findViewById(R.id.textInputLayoutStockProductoModificar);
        textInputLayoutCategoriaProductoModificar = findViewById(R.id.textInputLayoutCategoriaProductoModificar);

        bd = AppDatabase.getInstance(getApplicationContext());
        productoDao = bd.productoDao();
        controller = new ProductoController(productoDao);
        textNombreProductoModificar.setText(productoSeleccionado.getNombre());
        textPrecioProductoModificar.setText(String.valueOf(productoSeleccionado.getPrecio()));
        textStockProductoModificar.setText(String.valueOf(productoSeleccionado.getStock()));
        categoriasSpinner();
        cambiarColorPresionarBotonVolverModificar();
        cambiarColorPresionarBotonModificarProducto();
        pulsarBotonModificarProducto();
        pulsarVolverModificar();

    }


    private void categoriasSpinner() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<String> categorias = new ArrayList<>();
            categorias.add("Selecciona una categoría");
            for (ECategoria categoria : ECategoria.values()) {
                categorias.add(categoria.getNombreCategoria());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        R.layout.spinner, categorias);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoria.setAdapter(adapter);

                // Busca la posición de la categoría actual del producto y la selecciona
                String categoriaProducto = productoSeleccionado.getCategoria().getNombreCategoria();
                int posicion = categorias.indexOf(categoriaProducto);
                if (posicion != -1) {
                    spinnerCategoria.setSelection(posicion);
                } else {
                    spinnerCategoria.setSelection(0); // Valor predeterminado si no encuentra la categoría
                }
            });

        });
    }

    private void pulsarBotonModificarProducto() {
        modificarProducto.setOnClickListener(v -> {

            String nombreProducto = textNombreProductoModificar.getText().toString().trim();
            String precioProductoString = textPrecioProductoModificar.getText().toString().trim();
            String stockProductoString = textStockProductoModificar.getText().toString().trim();
            int categoria = spinnerCategoria.getSelectedItemPosition();
            String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString().trim();
            Producto productoModificado = productoSeleccionado;

            if (nombreProducto.isEmpty()) {
                textInputLayoutNombreProductoModificar.setError("Introduce un nombre");
            } else {
                textInputLayoutNombreProductoModificar.setError(null);
                productoModificado.setNombre(nombreProducto);
                if (precioProductoString.isEmpty()) {
                    textInputLayoutPrecioProductoModificar.setError("Introduce un precio");
                } else {
                    textInputLayoutPrecioProductoModificar.setError(null);
                    Double precioProducto = Double.parseDouble(precioProductoString);
                    productoModificado.setPrecio(precioProducto);
                    if (stockProductoString.isEmpty()) {
                        textInputLayoutStockProductoModificar.setError("Introduce un stock válido");
                    } else {
                        textInputLayoutStockProductoModificar.setError(null);
                        int stockProducto = Integer.parseInt(stockProductoString);
                        productoModificado.setStock(stockProducto);
                        if (categoria <= 0) {
                            textInputLayoutCategoriaProductoModificar.setError("Categoría invalida");
                        } else {
                            productoModificado.setCategoria(ECategoria.valueOf(categoriaSeleccionada.toUpperCase()));
                            ECategoria categoriaEnum = ECategoria.values()[categoria - 1];
                            productoModificado.setImagenProducto(categoriaEnum.getImagenCategoria());
                            controller.modificarProducto(productoModificado);
                            Toast.makeText(this, "Producto modificado con éxito", Toast.LENGTH_SHORT).show();
                            textNombreProductoModificar.setText("");
                            textPrecioProductoModificar.setText("");
                            textStockProductoModificar.setText("");
                            spinnerCategoria.setSelection(0);
                        }
                    }
                }
            }

        });

    }

    private void pulsarVolverModificar() {
        volver.setOnClickListener(v -> {
            Intent intent = new Intent(ModificarProducto.this, ActividadPrincipal.class);
            intent.putExtra("fragment", "inventario");
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonModificarProducto() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        modificarProducto.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    modificarProducto.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    modificarProducto.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonVolverModificar() {

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