package isi.dam.sendmeal.DAO.rest;

import java.util.List;

import isi.dam.sendmeal.Domain.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoRest {

    @GET("Pedidos/")
    Call<List<Pedido>> buscarTodos();

    @DELETE("Pedidos/{id}")
    Call<Void> borrar(@Path("id") Integer id);

    @PUT("Pedidos/{id}")
    Call<Pedido> actualizar (@Path("id") Integer id, @Body Pedido Pedido);

    @POST("Pedidos/")
    Call<Pedido> crear (@Body Pedido Pedido);

}
