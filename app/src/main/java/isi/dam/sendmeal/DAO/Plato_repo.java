package isi.dam.sendmeal.DAO;

import android.util.Log;

import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import isi.dam.sendmeal.DAO.rest.PlatoRest;
import isi.dam.sendmeal.Domain.Plato;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Plato_repo {
    public static String _SERVER = "http://10.15.155.37:5000/";
    private static Plato_repo _INSTANCIA;
    private Retrofit rf;
    private PlatoRest platoRest;



    private List<Plato> listaPlatos;


    private Plato_repo(){ }

    public static Plato_repo getInstance(){
        if(_INSTANCIA == null){
            _INSTANCIA = new Plato_repo();
            _INSTANCIA.configurarRetroFit();
            _INSTANCIA.listaPlatos = new ArrayList<>();
        }
        return _INSTANCIA;
    }


    private void configurarRetroFit(){
        this.rf = new Retrofit.Builder()
                .baseUrl(_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("APP_JUANO","INSTANCIA CREADA");

        this.platoRest = this.rf.create(PlatoRest.class);
    }
}
