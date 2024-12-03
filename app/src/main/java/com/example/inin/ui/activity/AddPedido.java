package com.example.inin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Transaction;

import com.example.inin.R;
import com.example.inin.data.controller.PedidoController;
import com.example.inin.data.controller.ProductoController;
import com.example.inin.data.controller.ProductoPedidoController;
import com.example.inin.data.dao.PedidoDao;
import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.dao.ProductoPedidoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Pedido;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;
import com.example.inin.ui.adapter.RecyclerViewProductoPedidoAdapter;
import com.example.inin.ui.fragments.Pedidos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AddPedido extends AppCompatActivity {

    private AppDatabase bd;
    private ProductoDao productoDao;
    private ProductoPedidoDao productoPedidoDao;
    private ProductoPedidoController productoPedidoController;
    private ProductoController productoController;
    private PedidoDao pedidoDao;
    private PedidoController pedidoController;
    private RecyclerView recyclerView;
    private Button confirmar;
    private long empresaActivaId = InicioSesionEmpresasActivity.empresaSesionActiva.getIdEmpresa();
    private RecyclerViewProductoPedidoAdapter adapter;
    private long idPedido;
    private List<ProductoPedido> listaProductoPedido;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pedido);
        idPedido = getIntent().getLongExtra("idPedido", -1);
        // Inicialización de la base de datos
        bd = AppDatabase.getInstance(getApplicationContext());
        productoDao = bd.productoDao();
        productoController = new ProductoController(productoDao);
        productoPedidoDao = bd.productoPedidoDao();
        productoPedidoController = new ProductoPedidoController(productoPedidoDao);
        pedidoDao = bd.pedidoDao();
        pedidoController = new PedidoController(pedidoDao);
        confirmar = findViewById(R.id.altaPedidoProductoBoton);
        recyclerView = findViewById(R.id.recyclerViewProductoPedido);
        recyclerView();
        cambiarColorPresionarBotonConfirmar();
        pulsarBotonConfirmar();


    }

    private void recyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productoController.listarProductosPorEmpresa(empresaActivaId).observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                adapter = new RecyclerViewProductoPedidoAdapter(AddPedido.this, productos);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pulsarBotonConfirmar() {
        confirmar.setOnClickListener(v -> {
            listaProductoPedido = adapter.getListaProductoPedido();

            // Validar fechas
            boolean fechaValida = true;

            for (ProductoPedido productoPedido : listaProductoPedido) {
                productoPedido.setIdPedido(idPedido);
                if (productoPedido.getCantidad() > 0) {
                    if (productoPedido.getFechaCaducidad() == null || !esFechaValida(productoPedido.getFechaCaducidad())) {
                        Toast.makeText(this, "Fecha incorrecta", Toast.LENGTH_SHORT).show();
                        fechaValida = false;
                        break;
                    } else {
                        productoPedidoController.altaProductoPedido(productoPedido);
                    }
                }
            }

            if (!fechaValida) {
                return;
            }
            // Ejecutar actualizaciones en segundo plano
            new ActualizarPedidoTask().execute(listaProductoPedido);

            Toast.makeText(this, "Pedido introducido con éxito", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddPedido.this, ActividadPrincipal.class);
            intent.putExtra("fragment", "pedidos");
            startActivity(intent);
            finish();
        });
    }

    // Método para validar la fecha usando LocalDate
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean esFechaValida(LocalDate fechaCaducidad) {
        LocalDate fechaActual = LocalDate.now();
        return !fechaCaducidad.isBefore(fechaActual);
    }


    // AsyncTask para procesar las actualizaciones
    private class ActualizarPedidoTask extends AsyncTask<List<ProductoPedido>, Void, Void> {
        @Override
        protected Void doInBackground(List<ProductoPedido>... params) {
            List<ProductoPedido> productosPedido = params[0];
            procesarPedidoConActualizaciones(productosPedido, idPedido);
            return null;
        }
    }

    @Transaction
    public void procesarPedidoConActualizaciones(List<ProductoPedido> productosPedido, long idPedido) {
        // Obtener productos y pedido
        List<Integer> idsProductos = productosPedido.stream().map(ProductoPedido::getIdProducto).collect(Collectors.toList());
        List<Producto> productos = productoDao.obtenerProductosPorIds(idsProductos);
        Pedido pedido = pedidoDao.buscarPedido(idPedido);

        double nuevoPrecioTotal = pedido.getPrecioTotal();

        for (ProductoPedido productoPedido : productosPedido) {
            Producto producto = buscarProducto(productos, productoPedido.getIdProducto());

            if (producto != null) {
                // Actualizar stock
                producto.setStock(producto.getStock() + productoPedido.getCantidad());
            }

            // Actualizar precio total del pedido
            nuevoPrecioTotal += producto.getPrecio() * productoPedido.getCantidad();
        }

        // Guardar cambios
        pedido.setPrecioTotal(nuevoPrecioTotal);
        productoDao.actualizarProductos(productos);
        pedidoDao.modificarPedido(pedido);
    }

    private Producto buscarProducto(List<Producto> productos, long idProducto) {
        for (Producto producto : productos) {
            if (producto.getIdProducto() == idProducto) {
                return producto;
            }
        }
        return null;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonConfirmar() {

        int colorPresionado = ContextCompat.getColor(this, R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        confirmar.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    confirmar.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    confirmar.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }
}
