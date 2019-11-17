package isi.dam.sendmeal.DAO;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import isi.dam.sendmeal.DAO.rest.PlatoRest;
import isi.dam.sendmeal.Domain.Plato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Plato_repo {
    public static String _SERVER = "http://192.168.0.14:5000/";
    private static Plato_repo _INSTANCIA;
    private Retrofit rf;
    private PlatoRest platoRest;

    public static final int _ALTA_PLATO = 1;
    public static final int _UPDATE_PLATO =2;
    public static final int _BORRADO_PLATO =3;
    public static final int _CONSULTA_PLATO =4;
    public static final int _ERROR_PLATO =9;


    private List<Plato> listaResultados;

    private List<Plato> listaPlatos;


    private Plato_repo(){ }

    public static Plato_repo getInstance(){
        if(_INSTANCIA == null){
            _INSTANCIA = new Plato_repo();
            _INSTANCIA.configurarRetroFit();
            _INSTANCIA.listaPlatos = new ArrayList<>();
            _INSTANCIA.listaResultados = new ArrayList<>();
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

    public void crearPlato (Plato a , final Handler h){
        Call<Plato> llamada = this.platoRest.crear(a);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                Log.d("PLATOREPO","ANDA BIEN "+ response.isSuccessful());
                Log.d("PLATOREPO", "codigo" + response.code());

                if(response.isSuccessful()){
                Log.d("PLATOREPO", "ejecuto bien");
                listaPlatos.add(response.body());
                Message m = new Message();
                m.arg1 = _ALTA_PLATO;
                h.sendMessage(m);
                }
            }
            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
            Log.d("PLATOREPO","ERROR"+ t.getMessage());
            Message m = new Message();
            m.arg1= _ERROR_PLATO;
            h.sendMessage(m);
            }
        });

    }

    public void actualizarPlato (final Plato a ,final Handler h){
        Call<Plato> llamada = this.platoRest.actualizar(a.getId(),a);
        llamada.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                Log.d("PLATOREPO","DESpues de ejecutar"+ response.isSuccessful());
                Log.d("PLATOREPO" ,"CODIGO" + response.code());

                if(response.isSuccessful()){
                    Log.d("PLATOREPO", "EJECUTO");
                    int pos = listaPlatos.indexOf(a);
                    listaPlatos.remove(a);
                    listaPlatos.add(pos, response.body());
                    if(listaResultados.contains(a)){
                        pos = listaResultados.indexOf(a);
                        listaResultados.remove(a);
                        listaResultados.add(pos, response.body());
                    }
                    Message m = new Message();
                    m.arg1= _UPDATE_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("PLATOREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void borrarPlato(final Plato a, final Handler h){
        Call<Void> llamada = this.platoRest.borrar(a.getId());
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("PLATOREPO", "Despues que ejecuta"+ response.isSuccessful());
                Log.d("PLATOREPO", "Codigo"+response.code());

                if(response.isSuccessful()){
                    Log.d("PLATOREPO", "Ejecuta");
                    for(Plato a : listaPlatos){
                        Log.d("PLATOREPO", "Plato"+ a.getId());
                    }
                    Log.d("PLATOREPO", "Borrar Plato"+ a.getId());
                    listaPlatos.remove(a);
                    if(listaResultados.contains(a)){
                        listaResultados.remove(a);
                    }
                    for(Plato a : listaPlatos){
                        Log.d("PLATOREPO", "Plato"+ a.getId());
                    }
                    Message m = new Message();
                    m.arg1= _BORRADO_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("PLATOREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void listarPlato(final Handler h){
        Call<List<Plato>> llamada = this.platoRest.buscarTodos();
        llamada.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    Log.d("PLATOREPOS", "ENTRO AL IF"+response.body().size());
                    listaPlatos.clear();
                    listaPlatos.addAll(response.body());
                    Log.d("PLATOREPOS",""+listaPlatos.size());
                    Message m = new Message();
                    m.arg1 = _CONSULTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Log.d("PLATOREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public void buscarPlatos(final Handler h, Float precio_min, Float precio_max, String titulo){
        Call<List<Plato>> llamada = this.platoRest.listaPlatos(precio_max, precio_min, titulo);
        llamada.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                if(response.isSuccessful()){
                    Log.d("PLATOREPOS", "ENTRO AL IF");
                    listaResultados.clear();
                    listaResultados.addAll(response.body());
                    Message m = new Message();
                    m.arg1 = _CONSULTA_PLATO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Log.d("PLATOREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PLATO;
                h.sendMessage(m);
            }
        });
    }

    public List<Plato> getListaPlatos(){
        return listaPlatos;
    }
    public List<Plato> getListaResultados(){
        return listaResultados;
    }
}
