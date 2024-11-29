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
import com.example.inin.R;
import com.example.inin.data.model.Producto;
import com.example.inin.ui.activity.ActividadPrincipal;


import java.util.List;

public class RecyclerViewProductoAdapter extends RecyclerView.Adapter<RecyclerViewProductoAdapter.MyViewHolder> {

    Context context;
    List<Producto> listaProductos;

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
        Producto producto = listaProductos.get(position);
        holder.textViewNombre.setText(listaProductos.get(position).getNombre());
        holder.textViewStock.setText(String.valueOf(listaProductos.get(position).getStock()));
        holder.imageView.setImageResource(listaProductos.get(position).getImagenProducto());
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

    private void mostrarDialogoOpcionesProducto(){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton("Modificar", null)
                .setNeutralButton("Eliminar",null)
                .setNegativeButton("Cancelar", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                    dialog.dismiss();
                    Intent i = new Intent(context, ModificarProducto.class);
                    context.startActivity(i);

            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
        });

        dialog.show();
    }
}

