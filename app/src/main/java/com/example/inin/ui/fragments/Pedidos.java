package com.example.inin.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.ui.activity.AddPedido;
import com.example.inin.R;
import com.example.inin.data.controller.PedidoController;
import com.example.inin.data.dao.PedidoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.Pedido;
import com.example.inin.ui.activity.InicioSesionEmpresasActivity;
import com.example.inin.ui.adapter.RecyclerViewPedidoAdapter;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Pedidos extends Fragment {

    private AppDatabase bd;
    private PedidoDao pedidoDao;
    private PedidoController controller;
    private RecyclerView recyclerView;
    private Button altaBoton;
    private Executor executor = Executors.newSingleThreadExecutor();
    private long empresaActivaId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
empresaActivaId = InicioSesionEmpresasActivity.empresaSesionActiva.getIdEmpresa();
        bd = AppDatabase.getInstance(requireContext());
        pedidoDao = bd.pedidoDao();
        controller = new PedidoController(pedidoDao);
        recyclerView = view.findViewById(R.id.recyclerViewPedidos);
        altaBoton = view.findViewById(R.id.altaPedidoBoton);


        cambiarColorPresionarBotonAltaPedido();
        pulsarAltaBoton();
        recyclerView();

        return view;
    }

    private void recyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        controller.listarPedidosPorEmpresa(empresaActivaId).observe(getViewLifecycleOwner(), new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                RecyclerViewPedidoAdapter adapter = new RecyclerViewPedidoAdapter(requireActivity(), pedidos);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pulsarAltaBoton() {
        altaBoton.setOnClickListener(v -> {
            Pedido pedido = new Pedido(empresaActivaId, LocalDate.now(), 0);
            executor.execute(() -> {
                long idPedido = controller.altaPedido(pedido);
                pedido.setIdPedido(idPedido);
                requireActivity().runOnUiThread(() -> {
                    Intent i = new Intent(requireActivity(), AddPedido.class);
                    i.putExtra("idPedido", pedido.getIdPedido());
                    startActivity(i);
                });
            });
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void cambiarColorPresionarBotonAltaPedido() {

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