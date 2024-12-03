package com.example.inin.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.R;
import com.example.inin.data.controller.ProductoController;
import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;

import java.util.List;

public class RecyclerViewCaducidadesAdapter extends RecyclerView.Adapter<RecyclerViewCaducidadesAdapter.MyViewHolder> {

    private Context context;
    private List<ProductoPedido> listaProductoPedido;
    private AppDatabase bd;
    private ProductoDao productoDao;
    private ProductoController productoController;

    public RecyclerViewCaducidadesAdapter(Context context, List<ProductoPedido> listaProductoPedido) {
        this.context = context;
        this.listaProductoPedido = listaProductoPedido;
    }

    @NonNull
    @Override
    public RecyclerViewCaducidadesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_caducidades, parent, false);
        return new RecyclerViewCaducidadesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCaducidadesAdapter.MyViewHolder holder, int position) {
        ProductoPedido productoPedido = listaProductoPedido.get(position);

        bd = AppDatabase.getInstance(context.getApplicationContext());
        productoDao = bd.productoDao();
        productoController = new ProductoController(productoDao);

        productoController.buscarProducto(productoPedido.getIdProducto()).observeForever(new Observer<Producto>() {
            @Override
            public void onChanged(Producto producto) {
                if (producto != null) {
                    holder.imageView.setImageResource(producto.getImagenProducto());
                    holder.textViewNombrePedido.setText(producto.getNombre());
                    holder.textViewFechaCaducidad.setText(String.valueOf(productoPedido.getFechaCaducidad()));
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
        TextView textViewNombrePedido;
        TextView textViewFechaCaducidad;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewIconoProductos);
            textViewNombrePedido = itemView.findViewById(R.id.textViewCaducidadesNombreProducto);
            textViewFechaCaducidad = itemView.findViewById(R.id.textViewCaducidadesFechaCaducidad);
        }
    }

    public List<ProductoPedido> getListaProductoPedido() {
        return listaProductoPedido;
    }

    public void setListaProductoPedido(List<ProductoPedido> listaProductoPedido) {
        this.listaProductoPedido = listaProductoPedido;
    }
}


