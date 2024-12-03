package com.example.inin.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inin.R;
import com.example.inin.data.controller.ProductoPedidoController;
import com.example.inin.data.dao.ProductoPedidoDao;
import com.example.inin.data.database.AppDatabase;
import com.example.inin.data.model.ProductoPedido;
import com.example.inin.ui.adapter.RecyclerViewCaducidadesAdapter;

import java.time.LocalDate;
import java.util.List;

public class Caducidad extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewCaducidadesAdapter recyclerViewCaducidadesAdapter;
    private AppDatabase bd;
    private ProductoPedidoDao productoPedidoDao;
    private ProductoPedidoController productoPedidoController;
    private List<ProductoPedido> listaProductoPedido;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_caducidad, container, false);

        bd = AppDatabase.getInstance(getContext());
        productoPedidoDao = bd.productoPedidoDao();
        productoPedidoController = new ProductoPedidoController(productoPedidoDao);

        recyclerView = view.findViewById(R.id.recyclerViewCaducidades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productoPedidoController.obtenerProductosPorFechaCaducidadMasProxima()
                .observe(getViewLifecycleOwner(), new Observer<List<ProductoPedido>>() {
                    @Override
                    public void onChanged(List<ProductoPedido> productoPedidos) {
                        if (productoPedidos != null && !productoPedidos.isEmpty()) {
                            listaProductoPedido = productoPedidos;
                            actualizarRecyclerView();
                        }
                    }
                });

        return view;
    }

    private void actualizarRecyclerView() {
        if (recyclerViewCaducidadesAdapter == null) {
            recyclerViewCaducidadesAdapter = new RecyclerViewCaducidadesAdapter(getContext(), listaProductoPedido);
            recyclerView.setAdapter(recyclerViewCaducidadesAdapter);
        } else {
            recyclerViewCaducidadesAdapter.setListaProductoPedido(listaProductoPedido);
            recyclerViewCaducidadesAdapter.notifyDataSetChanged();
        }
    }
}