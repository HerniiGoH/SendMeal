package isi.dam.sendmeal.DAO.rest;


import java.util.List;

import isi.dam.sendmeal.Domain.Plato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlatoRest {

    @GET("platos/")
    Call<List<Plato>> buscarTodos();

    @DELETE ("platos/{id}")
    Call<Void> borrar(@Path("id") Integer id);

    @PUT ("platos/{id}")
    Call<Plato> actualizar (@Path("id") Integer id, @Body Plato plato);

    @POST("platos/")
    Call<Plato> crear (@Body Plato plato);



}
