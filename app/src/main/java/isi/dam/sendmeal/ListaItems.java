package isi.dam.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.Plato;

public class ListaItems extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_items);
        toolbar = findViewById(R.id.toolbar_crear_item);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
        Plato_repo.getInstance().listarPlato(miHandler);

        mRecyclerView = findViewById(R.id.rvPlatos);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.agregar_flotante);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(view.getContext(), CrearItem.class);
                startActivity(i2);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage (Message m){
            Log.d("APP_2", "VUELVE AL HANDLER"+ m.arg1);
            switch ( m.arg1){
                case Plato_repo._ALTA_PLATO:
                    break;
                case Plato_repo._CONSULTA_PLATO:
                    mAdapter = new PlatoRecyclerAdapter(Plato_repo.getInstance().getListaPlatos(), ListaItems.this);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
