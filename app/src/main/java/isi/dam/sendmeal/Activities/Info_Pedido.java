package isi.dam.sendmeal.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import isi.dam.sendmeal.DAO.DBClient;
import isi.dam.sendmeal.DAO.ItemsPedidoDao;
import isi.dam.sendmeal.DAO.PedidoDao;
import isi.dam.sendmeal.DAO.Pedido_repo;
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
    private Toolbar toolbar;
    private TextView total;
    protected Pedido pedidoSeleccionado;
    protected List<ItemsPedido> listaItems;
    private Button btnEnviar, btnEliminar;

    Handler miHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message m) {
            Log.d("APP_2", "VUELVE AL HANDLER" + m.arg1);
            switch (m.arg1) {
                case Pedido_repo._ALTA_PEDIDO:
                    finish();
                    break;
            }
        }
    };

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

        btnEnviar = findViewById(R.id.button_enviar_pedido);
        btnEliminar = findViewById(R.id.button_borrar_pedido);

        mRecyclerView = findViewById(R.id.rvInfoPedido);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        pedidoSeleccionado = DBClient.getInstance(Info_Pedido.this).getPedidoDB().pedidoDao().getall().get(getIntent().getExtras().getInt("pos"));

        listaItems = DBClient.getInstance(Info_Pedido.this).getPedidoDB().itemsPedidoDao().getAllFromPedido(pedidoSeleccionado.getIdPedido());

        List<Plato> platoList = Plato_repo.getInstance().getListaPlatos();

        for(ItemsPedido i : listaItems){
            for(Plato p : platoList){
                if(p.getId() == i.getPlato().getId()){
                    i.setPlato(p);
                }
            }
        }

        pedidoSeleccionado.setItems((ArrayList<ItemsPedido>) listaItems);

        Log.d("DEBUGGEANDO", "" + pedidoSeleccionado.getIdPedido() + " " + pedidoSeleccionado.getItems().size());

        mAdapter = new ItemsPedidoInfoRecyclerAdapter(pedidoSeleccionado.getItems(), Info_Pedido.this, total);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        final DecimalFormat formatoMonto = new DecimalFormat("$0.00");
        total = findViewById(R.id.Precio_total_pedido);
        Double totalD = 0.0;
        for (ItemsPedido i : pedidoSeleccionado.getItems()) {
            totalD += i.getPrecioPlato();
        }
        total.setText(formatoMonto.format(totalD));

        if (pedidoSeleccionado.getEstado() == EstadoPedido.ENVIADO) {
            btnEliminar.setEnabled(false);
            btnEnviar.setEnabled(false);
        }

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrarPedido borrarPedido = new BorrarPedido();
                borrarPedido.execute(pedidoSeleccionado);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidoSeleccionado.setEstado(EstadoPedido.ENVIADO);
                PedidoDao pedidoDao = DBClient.getInstance(Info_Pedido.this).getPedidoDB().pedidoDao();
                pedidoDao.actualizar(pedidoSeleccionado);
                Pedido_repo.getInstance().crearPedido(pedidoSeleccionado,miHandler);
            }
        });
    }

    class BorrarPedido extends AsyncTask<Pedido, Void, Void> {
        @Override
        protected Void doInBackground(Pedido... pedidos) {
            Integer id = pedidos[0].getIdPedido();
            PedidoDao pedidoDao = DBClient.getInstance(Info_Pedido.this).getPedidoDB().pedidoDao();
            pedidoDao.delete(pedidos[0]);
            ItemsPedidoDao itemsPedidoDao = DBClient.getInstance(Info_Pedido.this).getPedidoDB().itemsPedidoDao();
            Log.d("DEBUGGEANDO", "" + itemsPedidoDao.getAllFromPedido(id).size());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
