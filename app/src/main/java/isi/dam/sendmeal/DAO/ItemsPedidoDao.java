package isi.dam.sendmeal.DAO;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;

public interface ItemsPedidoDao {


    @Query("SELECT * FROM itemspedido")
    List<ItemsPedido> getall();

    @Insert
    void insert(ItemsPedido itemspedido);

    @Insert
    void insertAll (ItemsPedido... itemspedido);

    @Delete
    void delete(ItemsPedido itemspedido);

    @Update
    void actualizar(ItemsPedido itemspedido);

}
