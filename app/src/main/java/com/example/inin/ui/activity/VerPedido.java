package com.example.inin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.R;
import com.example.inin.data.controller.ProductoController;
import com.example.inin.data.controller.ProductoPedidoController;
import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.dao.ProductoPedidoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Pedido;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;
import com.example.inin.ui.adapter.RecyclerViewVerPedidoAdapter;

import java.util.List;

public class VerPedido extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewVerPedidoAdapter adapter;
    private List<ProductoPedido> listaProductoPedido;
    private AppDatabase bd;
    private ProductoPedidoDao productoPedidoDao;
    private ProductoPedidoController productoPedidoController;
    private Button volverBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedido);
        long idPedido = getIntent().getLongExtra("idPedidoInformacion", -1);
        recyclerView = findViewById(R.id.recyclerViewInformacion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        volverBoton = findViewById(R.id.volverBotonInformacion);
        bd = AppDatabase.getInstance(getApplicationContext());
        productoPedidoDao = bd.productoPedidoDao();
        productoPedidoController = new ProductoPedidoController(productoPedidoDao);
        Log.d("VerPedido", "ID Pedido recibido: " + idPedido);
        cambiarColorPresionarBotonVolver();
        pulsarBotonVolver();

        productoPedidoController.listarProductosPorPedido(idPedido).observe(this, new Observer<List<ProductoPedido>>() {
            @Override
            public void onChanged(List<ProductoPedido> productoPedidos) {
                if (productoPedidos != null && !productoPedidos.isEmpty()) {
                    listaProductoPedido = productoPedidos;
                    actualizarRecyclerView();
                } else {
                    // Log para verificar si la lista está vacía
                    Log.d("VerPedido", "Lista vacía");
                }
            }
        });
    }

    private void actualizarRecyclerView() {
        // Si aún no has inicializado el adaptador, lo haces aquí
        if (adapter == null) {
            adapter = new RecyclerViewVerPedidoAdapter(this, listaProductoPedido);
            recyclerView.setAdapter(adapter);
        } else {
            // Si ya existe el adaptador, actualiza la lista de productos
            adapter.setListaProductoPedido(listaProductoPedido);
            adapter.notifyDataSetChanged();
        }
    }

    private void pulsarBotonVolver() {
        volverBoton.setOnClickListener(v -> {
            Intent intent = new Intent(VerPedido.this, ActividadPrincipal.class);
            intent.putExtra("fragment", "pedidos");
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonVolver() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        volverBoton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    volverBoton.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    volverBoton.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }
}