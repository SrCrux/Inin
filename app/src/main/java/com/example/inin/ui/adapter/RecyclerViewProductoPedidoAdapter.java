package com.example.inin.ui.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.R;
import com.example.inin.data.model.Producto;
import com.example.inin.data.model.ProductoPedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewProductoPedidoAdapter extends RecyclerView.Adapter<RecyclerViewProductoPedidoAdapter.MyViewHolder> {

    private Context context;
    private List<Producto> listaProductos;
    private List<ProductoPedido> listaProductoPedido = new ArrayList<>();

    public RecyclerViewProductoPedidoAdapter(Context context, List<Producto> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos;
        for (Producto producto : listaProductos) {
            ProductoPedido productoPedido = new ProductoPedido();
            productoPedido.setIdProducto(producto.getIdProducto()); // Inicializar con el producto correspondiente
            listaProductoPedido.add(productoPedido);
        }
    }

    @NonNull
    @Override
    public RecyclerViewProductoPedidoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_producto_pedido, parent, false);
        return new RecyclerViewProductoPedidoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewProductoPedidoAdapter.MyViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        ProductoPedido productoPedido = listaProductoPedido.get(position);
        holder.textViewNombre.setText(producto.getNombre());
        holder.imageView.setImageResource(producto.getImagenProducto());
        holder.editTextCantidad.setText("");
        holder.editTextCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Asignar 0 si el campo está vacío, o el valor ingresado si no lo está
                int cantidad = s.toString().isEmpty() ? 0 : Integer.parseInt(s.toString());
                productoPedido.setCantidad(cantidad);
                productoPedido.setPrecio(cantidad * producto.getPrecio());
            }
        });
        holder.fechaCaducidad.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, ((view, year, month, dayOfMonth) -> {
                String fecha = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                holder.fechaCaducidad.setText(fecha);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    productoPedido.setFechaCaducidad(LocalDate.parse(fecha));
                }
            }), 2024, 11, 1);
            datePickerDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewNombre;
        EditText editTextCantidad;
        Button fechaCaducidad;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewIconoProductos);
            textViewNombre = itemView.findViewById(R.id.textViewNombreProducto);
            editTextCantidad = itemView.findViewById(R.id.editTextCantidadProducto);
            fechaCaducidad = itemView.findViewById(R.id.buttonFechaCaducidadProducto);
            cardView = itemView.findViewById(R.id.cardViewProductos);
        }
    }

    public List<ProductoPedido> getListaProductoPedido(){
        return listaProductoPedido;
    }
}
