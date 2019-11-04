package isi.dam.sendmeal.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isi.dam.sendmeal.DAO.DBClient;
import isi.dam.sendmeal.DAO.PedidoDao;
import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.EstadoPedido;
import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;
import isi.dam.sendmeal.Domain.Plato;
import isi.dam.sendmeal.R;
import isi.dam.sendmeal.RecyclerAdapters.ItemsPedidoInfoRecyclerAdapter;
import isi.dam.sendmeal.RecyclerAdapters.ItemsPedidoRecyclerAdapter;

public class Info_Pedido extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pedido);
        toolbar = findViewById(R.id.toolbar_crear_item);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.rvInfoPedido);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Pedido pedidoSeleccionado = DBClient.getInstance(Info_Pedido.this).getPedidoDB().pedidoDao().getall().get(getIntent().getExtras().getInt("pos"));

        List<ItemsPedido> listaItems =DBClient.getInstance(Info_Pedido.this).getPedidoDB().itemsPedidoDao().getAllFromPedido(pedidoSeleccionado.getIdPedido());

        pedidoSeleccionado.setItems((ArrayList<ItemsPedido>) listaItems);

        Log.d("DEBUGGEANDO", ""+pedidoSeleccionado.getIdPedido()+" "+pedidoSeleccionado.getItems().size());

        mAdapter = new ItemsPedidoInfoRecyclerAdapter(pedidoSeleccionado.getItems(), Info_Pedido.this, total);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        total= findViewById(R.id.Precio_total_pedido);
        Double totalD = 0.0;
        for(ItemsPedido i : pedidoSeleccionado.getItems()){
            totalD+=i.getPrecioPlato();
        }
        total.setText(""+totalD);

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }



}
