package isi.dam.sendmeal.DAO;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import isi.dam.sendmeal.Domain.Pedido;
import isi.dam.sendmeal.DAO.rest.PedidoRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pedido_repo {
    public static String _SERVER = "http://192.168.0.14:5000/";
    private static Pedido_repo _INSTANCIA;
    private Retrofit rf;
    private PedidoRest PedidoRest;

    public static final int _ALTA_PEDIDO = 1;
    public static final int _UPDATE_PEDIDO =2;
    public static final int _BORRADO_PEDIDO =3;
    public static final int _CONSULTA_PEDIDO =4;
    public static final int _ERROR_PEDIDO =9;

    private List<Pedido> listaPedidos;


    public Pedido_repo(){ }

    public static Pedido_repo getInstance(){
        if(_INSTANCIA == null){
            _INSTANCIA = new Pedido_repo();
            _INSTANCIA.configurarRetroFit();
            _INSTANCIA.listaPedidos = new ArrayList<>();
        }
        return _INSTANCIA;
    }

    private void configurarRetroFit(){
        this.rf = new Retrofit.Builder()
                .baseUrl(_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("APP_JUANO","INSTANCIA CREADA");

        this.PedidoRest = this.rf.create(PedidoRest.class);
    }

    public void crearPedido (Pedido a , final Handler h){
        Call<Pedido> llamada = this.PedidoRest.crear(a);
        llamada.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                Log.d("PedidoREPO","ANDA BIEN "+ response.isSuccessful());
                Log.d("PedidoREPO", "codigo" + response.code());

                if(response.isSuccessful()){
                    Log.d("PedidoREPO", "ejecuto bien");
                    listaPedidos.add(response.body());
                    Message m = new Message();
                    m.arg1 = _ALTA_PEDIDO;
                    h.sendMessage(m);
                }
            }
            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.d("PedidoREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PEDIDO;
                h.sendMessage(m);
            }
        });

    }

    public void actualizarPedido (final Pedido a ,final Handler h){
        Call<Pedido> llamada = this.PedidoRest.actualizar(a.getIdPedido(),a);
        llamada.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                Log.d("PedidoREPO","DESpues de ejecutar"+ response.isSuccessful());
                Log.d("PedidoREPO" ,"CODIGO" + response.code());

                if(response.isSuccessful()){
                    Log.d("PedidoREPO", "EJECUTO");
                    int pos = listaPedidos.indexOf(a);
                    listaPedidos.remove(a);
                    listaPedidos.add(pos, response.body());
                    Message m = new Message();
                    m.arg1= _UPDATE_PEDIDO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.d("PedidoREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PEDIDO;
                h.sendMessage(m);
            }
        });
    }

    public void borrarPedido(final Pedido a, final Handler h){
        Call<Void> llamada = this.PedidoRest.borrar(a.getIdPedido());
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("PedidoREPO", "Despues que ejecuta"+ response.isSuccessful());
                Log.d("PedidoREPO", "Codigo"+response.code());

                if(response.isSuccessful()){
                    Log.d("PedidoREPO", "Ejecuta");
                    for(Pedido a : listaPedidos){
                        Log.d("PedidoREPO", "Pedido"+ a.getIdPedido());
                    }
                    Log.d("PedidoREPO", "Borrar Pedido"+ a.getIdPedido());
                    listaPedidos.remove(a);
                    for(Pedido a : listaPedidos){
                        Log.d("PedidoREPO", "Pedido"+ a.getIdPedido());
                    }
                    Message m = new Message();
                    m.arg1= _BORRADO_PEDIDO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("PedidoREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PEDIDO;
                h.sendMessage(m);
            }
        });
    }

    public void listarPedido(final Handler h){
        Call<List<Pedido>> llamada = this.PedidoRest.buscarTodos();
        llamada.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.isSuccessful()){
                    Log.d("PedidoREPOS", "ENTRO AL IF"+response.body().size());
                    listaPedidos.clear();
                    listaPedidos.addAll(response.body());
                    Log.d("PedidoREPOS",""+listaPedidos.size());
                    Message m = new Message();
                    m.arg1 = _CONSULTA_PEDIDO;
                    h.sendMessage(m);
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.d("PedidoREPO","ERROR"+ t.getMessage());
                Message m = new Message();
                m.arg1= _ERROR_PEDIDO;
                h.sendMessage(m);
            }
        });
    }

    public List<Pedido> getListaPedidos(){
        return listaPedidos;
    }
}
