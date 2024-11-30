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
import com.example.inin.data.model.Usuario;
import com.example.inin.ui.activity.ActividadPrincipal;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class RecyclerViewUsuarioAdapter extends RecyclerView.Adapter<RecyclerViewUsuarioAdapter.MyViewHolder> {

    private Context context;
    private List<Usuario> listaUsuarios;
    private TextInputEditText editTextPassword;
    private TextInputLayout textInputLayout;

    public RecyclerViewUsuarioAdapter(Context context, List<Usuario> listaUsuarios) {
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
        Usuario usuario = listaUsuarios.get(position);
        holder.textView.setText(usuario.getNombreUsuario());
        holder.imageView.setImageResource(usuario.getImagenUsuario());
        holder.cardView.setOnClickListener(v -> {
            if (usuario.isAdministrador()) {
                mostrarDialogoContraseña(usuario);
            } else {
                mostrarDialogoPin(usuario);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuarios != null ? listaUsuarios.size() : 0;
    }

    public void updateUsuarios(List<Usuario> nuevosUsuarios) {
        // Actualizamos la lista y notificamos al adaptador que los datos cambiaron
        this.listaUsuarios = nuevosUsuarios;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewIcono);
            textView = itemView.findViewById(R.id.textViewListaUsuarios);
            cardView = itemView.findViewById(R.id.cardViewRecyclerUsuariosActivity);
        }
    }

    private void mostrarDialogoContraseña(Usuario usuario) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_input_password, null);

        textInputLayout = dialogView.findViewById(R.id.textInputLayoutPassword);
        editTextPassword = dialogView.findViewById(R.id.editTextPassword);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("Aceptar", null)
                .setNegativeButton("Cancelar", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String contraseñaIngresada = editTextPassword.getText().toString().trim();
                if (!usuario.getPasswordUsuario().equals(contraseñaIngresada)) {
                    textInputLayout.setError("Contraseña incorrecta");
                } else {
                    textInputLayout.setError(null);
                    dialog.dismiss();
                    Intent i = new Intent(context, ActividadPrincipal.class);
                    context.startActivity(i);
                }
            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
        });

        dialog.show();
    }

    private void mostrarDialogoPin(Usuario usuario) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_input_pin, null);

        TextInputEditText pinEditText = dialogView.findViewById(R.id.pinEditText);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("Aceptar", null)
                .setNegativeButton("Cancelar", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String pinIngresado = pinEditText.getText().toString().trim();
                if (!usuario.getPinUsuario().equals(pinIngresado)) {
                    pinEditText.setError("PIN incorrecto");
                } else {
                    pinEditText.setError(null);
                    dialog.dismiss();
                    Intent i = new Intent(context, ActividadPrincipal.class);
                    context.startActivity(i);
                }
            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorTerciary));
        });

        dialog.show();
    }
}
