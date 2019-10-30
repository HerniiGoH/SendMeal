package isi.dam.sendmeal.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import isi.dam.sendmeal.Domain.Pedido;
@Dao
public interface PedidoDao {

    @Query("SELECT * FROM pedido")
    List<Pedido> getall();

    @Insert
    void insert(Pedido pedido);

    @Insert
    void insertAll (Pedido... pedidos);

    @Delete
    void delete(Pedido pedido);

    @Update
    void actualizar(Pedido pedido);



}
