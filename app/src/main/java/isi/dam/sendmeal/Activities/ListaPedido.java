package isi.dam.sendmeal.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import isi.dam.sendmeal.DAO.DBClient;
import isi.dam.sendmeal.DAO.PedidoDao;
import isi.dam.sendmeal.Domain.Pedido;
import isi.dam.sendmeal.R;
import isi.dam.sendmeal.RecyclerAdapters.PedidoRecyclerAdapter;

public class ListaPedido extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;
    List<Pedido> listaPedidos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        toolbar = findViewById(R.id.toolbar_lista_pedidos);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.rvPedido);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final  Runnable cargarPedidos = new Runnable() {
            @Override
            public void run() {
                PedidoDao pedidoDao = DBClient.getInstance(ListaPedido.this).getPedidoDB().pedidoDao();
                listaPedidos = pedidoDao.getall();
                mAdapter = new PedidoRecyclerAdapter(listaPedidos, ListaPedido.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        };
        Thread t1 = new Thread(cargarPedidos);
        t1.start();

        class BorrarPedidoAsyncTask extends AsyncTask<Pedido,Void,Void>{
            @Override
            protected Void doInBackground(Pedido... pedidos) {
                PedidoDao pedidoDao = DBClient.getInstance(ListaPedido.this).getPedidoDB().pedidoDao();
                pedidoDao.delete(pedidos[0]);
                listaPedidos.clear();
                listaPedidos.addAll(pedidoDao.getall());
                return null;
            }

            @Override
            protected void onPostExecute (Void aVoid){
                mAdapter.notifyDataSetChanged();
            }
        }

        FloatingActionButton fab = findViewById(R.id.agregar_flotante);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ListaPedido.this, ListaItemsPedido.class);
                startActivity(i2);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(mAdapter!=null){
            PedidoDao pedidoDao = DBClient.getInstance(ListaPedido.this).getPedidoDB().pedidoDao();
            listaPedidos.clear();
            listaPedidos.addAll(pedidoDao.getall());
        mAdapter.notifyDataSetChanged();}
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
