package isi.dam.sendmeal.DAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import isi.dam.sendmeal.Domain.ItemsPedido;
import isi.dam.sendmeal.Domain.Pedido;

@Database(entities = {Pedido.class, ItemsPedido.class}, version = 1)
public abstract class PedidoDB extends RoomDatabase {
public abstract PedidoDao pedidoDao();
public abstract ItemsPedidoDao itemsPedidoDao();

}
