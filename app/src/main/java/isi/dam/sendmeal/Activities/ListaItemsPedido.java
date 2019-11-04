package isi.dam.sendmeal.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isi.dam.sendmeal.DAO.DBClient;
import isi.dam.sendmeal.DAO.ItemsPedidoDao;
import isi.dam.sendmeal.DAO.PedidoDao;
import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.EstadoPedido;
import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;
import isi.dam.sendmeal.Domain.Plato;
import isi.dam.sendmeal.R;
import isi.dam.sendmeal.RecyclerAdapters.ItemsPedidoRecyclerAdapter;
import isi.dam.sendmeal.RecyclerAdapters.PlatoRecyclerAdapter;

public class ListaItemsPedido extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;
    TextView total;
    Button crear;
    ArrayList<ItemsPedido> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        crear = findViewById(R.id.boton_crear_pedido);
        toolbar = findViewById(R.id.toolbar_crear_item);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        total = findViewById(R.id.Precio_total_pedido);
        total.setText("0");

        mRecyclerView = findViewById(R.id.rvItemsPedido);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setFecha_creacion(null);
        pedido.setLat(0.0);
        pedido.setLng(0.0);

         lista = new ArrayList<>();

        for (Plato p : Plato_repo.getInstance().getListaPlatos()) {
            ItemsPedido item = new ItemsPedido();
            item.setCantidad(0);
            item.setPlato(p);
            item.setPrecioPlato(0.0);
            lista.add(item);
        }

        mAdapter = new ItemsPedidoRecyclerAdapter(lista, ListaItemsPedido.this, total);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pedido.setFecha_creacion(new Date());
                GuardarPedido guardarPedido = new GuardarPedido();
                guardarPedido.execute(pedido);
            }
        });

    }

    class GuardarItemsPedido extends AsyncTask<List<ItemsPedido>, Void, Void>{

        @Override
        protected Void doInBackground(List<ItemsPedido>... lists) {
            ItemsPedidoDao itemsPedidoDao = DBClient.getInstance(ListaItemsPedido.this).getPedidoDB().itemsPedidoDao();
            PedidoDao pedidoDao = DBClient.getInstance(ListaItemsPedido.this).getPedidoDB().pedidoDao();
            List<Pedido> pedidos = pedidoDao.getall();
            Integer id = pedidos.get(pedidos.size()-1).getIdPedido();
            for(ItemsPedido i : lists[0]){
                if(i.getCantidad()==0){
                    lists[0].remove(i);
                }
                else{
                    i.setIdPedido_Child(id);
                }
            }
            itemsPedidoDao.insertAll(lists[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            finish();
        }
    }

    class GuardarPedido extends AsyncTask<Pedido, Void, Void> {

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            PedidoDao pedidoDao = DBClient.getInstance(ListaItemsPedido.this).getPedidoDB().pedidoDao();
            if (pedidos[0].getIdPedido() != null) {
                pedidoDao.actualizar(pedidos[0]);
            } else {
                pedidoDao.insert(pedidos[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GuardarItemsPedido guardarItemsPedido = new GuardarItemsPedido();
            guardarItemsPedido.execute(lista);
        /*Intent i = new Intent(ListaItemsPedido.this, ListaPedido.class);
        startActivity(i);*/
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
