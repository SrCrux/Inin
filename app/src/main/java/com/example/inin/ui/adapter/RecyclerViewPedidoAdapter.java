package com.example.inin.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.R;
import com.example.inin.ui.activity.VerPedido;
import com.example.inin.data.controller.PedidoController;
import com.example.inin.data.dao.PedidoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Pedido;


import java.util.List;

public class RecyclerViewPedidoAdapter extends RecyclerView.Adapter<RecyclerViewPedidoAdapter.MyViewHolder> {

    private AppDatabase bd;
    private PedidoDao pedidoDao;
    private PedidoController pedidoController;
    private Context context;
    private List<Pedido> listaPedidos;

    public RecyclerViewPedidoAdapter(Context context, List<Pedido> listaPedidos) {
        this.context = context;
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public RecyclerViewPedidoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_pedidos, parent, false);
        return new RecyclerViewPedidoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPedidoAdapter.MyViewHolder holder, int position) {
        bd = AppDatabase.getInstance(context.getApplicationContext());
        pedidoDao = bd.pedidoDao();
        pedidoController = new PedidoController(pedidoDao);
        Pedido pedido = listaPedidos.get(position);
        holder.textViewIdPedido.setText(String.valueOf(pedido.getIdPedido()));
        holder.textViewFechaPedido.setText(String.valueOf(pedido.getFecha()));
        holder.textViewPrecioPedido.setText(String.format("%.2f",pedido.getPrecioTotal()));

        holder.cardView.setOnClickListener(v -> {
            mostrarDialogoOpcionesPedido(pedido);  //
        });
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewIdPedido;
        TextView textViewFechaPedido;
        TextView textViewPrecioPedido;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIdPedido = itemView.findViewById(R.id.textViewIdPedido);
            textViewFechaPedido = itemView.findViewById(R.id.textViewFechaPedido);
            textViewPrecioPedido = itemView.findViewById(R.id.textViewPrecioPedido);
            cardView = itemView.findViewById(R.id.cardViewPedidos);
        }
    }

    private void mostrarDialogoOpcionesPedido(Pedido pedidoSeleccionado) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton("Ver Pedido", null)
                .setNeutralButton("Eliminar", null)
                .setNegativeButton("Cancelar", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                dialog.dismiss();
                // Aquí se pasa el producto al iniciar la actividad
                Intent i = new Intent(context, VerPedido.class);
                i.putExtra("idPedidoInformacion", pedidoSeleccionado.getIdPedido());
                context.startActivity(i);
            });

            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
                dialog.dismiss();
                pedidoController.bajaPedido(pedidoSeleccionado.getIdPedido());
                listaPedidos.remove(pedidoSeleccionado);
                notifyDataSetChanged();

            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
        });

        dialog.show();
    }
}


