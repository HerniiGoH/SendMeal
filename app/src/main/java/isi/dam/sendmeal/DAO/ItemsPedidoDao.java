package isi.dam.sendmeal.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;
@Dao
public interface ItemsPedidoDao {


    @Query("SELECT * FROM itemspedido")
    List<ItemsPedido> getall();

    @Query("SELECT * FROM ItemsPedido WHERE idPedido_Child=:idPedido")
    List<ItemsPedido> getAllFromPedido(final int idPedido);

    @Insert
    void insert(ItemsPedido itemspedido);

    @Insert
    void insertAll (List<ItemsPedido> itemsPedidos);

    @Delete
    void delete(ItemsPedido itemspedido);

    @Update
    void actualizar(ItemsPedido itemspedido);

}
