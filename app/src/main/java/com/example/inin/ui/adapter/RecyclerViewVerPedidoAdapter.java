package com.example.inin.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inin.R;
import com.example.inin.data.controller.ProductoController;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;
import java.util.List;

public class RecyclerViewVerPedidoAdapter extends RecyclerView.Adapter<RecyclerViewVerPedidoAdapter.MyViewHolder> {

    private Context context;
    private List<ProductoPedido> listaProductoPedido;
    private AppDatabase bd;
    private ProductoController productoController;

    public RecyclerViewVerPedidoAdapter(Context context, List<ProductoPedido> listaProductoPedido) {
        this.context = context;
        this.listaProductoPedido = listaProductoPedido;
    }

    @NonNull
    @Override
    public RecyclerViewVerPedidoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_ver_pedido, parent, false);
        return new RecyclerViewVerPedidoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewVerPedidoAdapter.MyViewHolder holder, int position) {
        ProductoPedido productoPedido = listaProductoPedido.get(position);

        // Inicializar la base de datos y controlador
        bd = AppDatabase.getInstance(context.getApplicationContext());
        productoController = new ProductoController(bd.productoDao());

        // Obtener el LiveData de producto
        productoController.buscarProducto(productoPedido.getIdProducto()).observeForever(new Observer<Producto>() {
            @Override
            public void onChanged(Producto producto) {
                if (producto != null) {
                    // Actualizamos la vista con los datos del producto
                    holder.textViewNombre.setText(producto.getNombre());
                    holder.imageView.setImageResource(producto.getImagenProducto());
                    holder.textViewCantidad.setText(String.valueOf(productoPedido.getCantidad()));
                    holder.textViewCaducidad.setText(String.valueOf(productoPedido.getFechaCaducidad()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductoPedido.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewNombre;
        TextView textViewCantidad;
        TextView textViewCaducidad;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewIconoProductos);
            textViewNombre = itemView.findViewById(R.id.textViewNombreProducto);
            textViewCantidad = itemView.findViewById(R.id.textViewCantidadProducto);
            textViewCaducidad = itemView.findViewById(R.id.textViewFechaCaducidadProducto);
            cardView = itemView.findViewById(R.id.cardViewProductos);
        }
    }

    public List<ProductoPedido> getListaProductoPedido() {
        return listaProductoPedido;
    }

    public void setListaProductoPedido(List<ProductoPedido> listaProductoPedido) {
        this.listaProductoPedido = listaProductoPedido;
    }
}
