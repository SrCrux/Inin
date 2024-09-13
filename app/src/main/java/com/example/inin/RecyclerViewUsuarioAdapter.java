package com.example.inin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewUsuarioAdapter extends RecyclerView.Adapter<RecyclerViewUsuarioAdapter.MyViewHolder> {

    Context context;
    ArrayList<Usuario> listaUsuarios;

    public RecyclerViewUsuarioAdapter(Context context, ArrayList<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public RecyclerViewUsuarioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_usuarios, parent, false);
        return new RecyclerViewUsuarioAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewUsuarioAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(listaUsuarios.get(position).getNombreUsuario());
        holder.imageView.setImageResource(listaUsuarios.get(position).getImagenUsuario());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewIcono);
            textView = itemView.findViewById(R.id.textViewListaUsuarios);

        }
    }
}
