package com.example.inin.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.R;
import com.example.inin.data.controller.ProductoController;
import com.example.inin.data.dao.ProductoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Empresa;
import com.example.inin.data.model.Producto;
import com.example.inin.ui.activity.AddProducto;
import com.example.inin.ui.activity.InicioSesionEmpresasActivity;
import com.example.inin.ui.activity.UsuariosActivity;
import com.example.inin.ui.adapter.RecyclerViewProductoAdapter;
import com.example.inin.ui.adapter.RecyclerViewUsuarioAdapter;

import java.util.List;

public class Inventario extends Fragment {

    private AppDatabase bd;
    private ProductoDao productoDao;
    private ProductoController controller;
    private RecyclerView recyclerView;
    private Button altaBoton;
    private long empresaActivaId = InicioSesionEmpresasActivity.empresaSesionActiva.getIdEmpresa();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        bd = AppDatabase.getInstance(requireContext());
        productoDao = bd.productoDao();
        controller = new ProductoController(productoDao);
        recyclerView = view.findViewById(R.id.recyclerViewProductos);
        altaBoton = view.findViewById(R.id.altaProductoBoton);


        cambiarColorPresionarBotonAltaProducto();
        pulsarAltaBoton();
        recyclerView();

        return view;
    }

    private void recyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        controller.listarProductosPorEmpresa(empresaActivaId).observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                RecyclerViewProductoAdapter adapter = new RecyclerViewProductoAdapter(requireActivity(), productos);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void pulsarAltaBoton() {
        altaBoton.setOnClickListener(v -> {

            Intent i = new Intent(requireActivity(), AddProducto.class);
            startActivity(i);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonAltaProducto() {

        int colorPresionado = ContextCompat.getColor(requireContext(), R.color.colorPrimaryWhite);
        int colorNormal = Color.TRANSPARENT;

        altaBoton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el color cuando se presiona
                    altaBoton.setBackgroundColor(colorPresionado);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Cambiar el color de vuelta cuando se suelta
                    altaBoton.setBackgroundColor(colorNormal);
                    break;
            }
            return false;
        });
    }
}