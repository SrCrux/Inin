package com.example.inin.ui.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.data.controller.ProductoController;
import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.ui.activity.ModificarProducto;
import com.example.inin.R;
import com.example.inin.data.model.Producto;


import java.util.List;

public class RecyclerViewProductoAdapter extends RecyclerView.Adapter<RecyclerViewProductoAdapter.MyViewHolder> {

    private AppDatabase bd;
    private ProductoDao productoDao;
    private ProductoController productoController;
    private Context context;
    private List<Producto> listaProductos;

    public RecyclerViewProductoAdapter(Context context, List<Producto> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public RecyclerViewProductoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_productos, parent, false);
        return new RecyclerViewProductoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewProductoAdapter.MyViewHolder holder, int position) {
        bd = AppDatabase.getInstance(context.getApplicationContext());
        productoDao = bd.productoDao();
        productoController = new ProductoController(productoDao);
        Producto producto = listaProductos.get(position);
        holder.textViewNombre.setText(producto.getNombre());
        holder.textViewStock.setText(String.valueOf(producto.getStock()));
        holder.imageView.setImageResource(producto.getImagenProducto());

        holder.cardView.setOnClickListener(v -> {
            mostrarDialogoOpcionesProducto(producto);
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewNombre;
        TextView textViewStock;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewIconoProductos);
            textViewNombre = itemView.findViewById(R.id.textViewNombreProducto);
            textViewStock = itemView.findViewById(R.id.textViewStockProducto);
            cardView = itemView.findViewById(R.id.cardViewProductos);
        }
    }

    // Método para mostrar el diálogo y pasar el producto seleccionado
    private void mostrarDialogoOpcionesProducto(Producto productoSeleccionado) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton("Modificar", null)
                .setNeutralButton("Eliminar", null)
                .setNegativeButton("Cancelar", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                dialog.dismiss();
                Intent i = new Intent(context, ModificarProducto.class);
                i.putExtra("producto", productoSeleccionado);
                context.startActivity(i);
            });

            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
                dialog.dismiss();
                productoController.bajaProducto(productoSeleccionado.getIdProducto());
                listaProductos.remove(productoSeleccionado);
                notifyDataSetChanged();

            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
        });

        dialog.show();
    }
}
