package isi.dam.sendmeal.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import isi.dam.sendmeal.DAO.Plato_repo;
import isi.dam.sendmeal.Domain.Plato;
import isi.dam.sendmeal.R;

public class BusquedaItem extends AppCompatActivity {

    Float precio_min, precio_max;
    String titulo;
    TextInputLayout ingresoTitulo, ingresoPrecioMin, ingresoPrecioMax;
    Button btnBuscar;
    ArrayList<Plato> platoResultados;
    Toolbar toolbar;

    final Handler miHandler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage (Message m){
            Log.d("APP_2", "VUELVE AL HANDLER"+ m.arg1);
            switch ( m.arg1){
                case Plato_repo._CONSULTA_PLATO:
                    platoResultados = (ArrayList<Plato>) Plato_repo.getInstance().getListaResultados();
                    if(platoResultados!= null){
                        Log.d("QUE ANDE", ""+platoResultados.size());
                        Intent intent = new Intent(BusquedaItem.this, ListaResultados.class);
                        startActivity(intent);
                    }
                    else{
                        Log.d("QUE ANDE", "No anduvo flaco");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_item);

        toolbar = findViewById(R.id.toolbar_01);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        ingresoTitulo = findViewById(R.id.IngresoBusqueda_nombre);
        ingresoPrecioMax = findViewById(R.id.IngresoBusqueda_Max);
        ingresoPrecioMin = findViewById(R.id.IngresoBusqueda_min);
        btnBuscar = findViewById(R.id.Busqueda_Boton);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titulo = ingresoTitulo.getEditText().getText().toString();
                String aux = ingresoPrecioMin.getEditText().getText().toString();
                if(aux.isEmpty()){
                    precio_min = Float.MIN_VALUE;
                }
                else{
                    precio_min = Float.valueOf(aux);
                }
                aux = ingresoPrecioMax.getEditText().getText().toString();
                if(aux.isEmpty()){
                    precio_max = Float.MAX_VALUE;
                }
                else{
                    precio_max = Float.valueOf(aux);
                }

                Plato_repo.getInstance().buscarPlatos(miHandler, precio_min, precio_max, titulo);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
