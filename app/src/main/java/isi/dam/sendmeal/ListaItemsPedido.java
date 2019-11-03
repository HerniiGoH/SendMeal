package isi.dam.sendmeal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;
import isi.dam.sendmeal.Domain.Plato;

public class ListaItemsPedido extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        toolbar = findViewById(R.id.toolbar_crear_item);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });


        mRecyclerView = findViewById(R.id.rvItemsPedido);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Pedido pedido = new Pedido();


        ArrayList<ItemsPedido> listaItemsP = new ArrayList<>();
        for (Plato p : Plato_repo.getInstance().getListaPlatos()){
            ItemsPedido a = new ItemsPedido();
            a.setId(p.getId());
            a.setPedido();

        }


        mAdapter = new PlatoRecyclerAdapter(null, ListaItemsPedido.this, true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
